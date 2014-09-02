package edu.stanford.protege.reasoning.impl;

import com.google.common.base.Optional;
import com.google.common.util.concurrent.*;
import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;
import edu.stanford.protege.reasoning.*;
import edu.stanford.protege.reasoning.action.*;
import org.semanticweb.owlapi.change.AxiomChangeData;
import org.semanticweb.owlapi.reasoner.*;

import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @author Matthew Horridge, Stanford University, Bio-Medical Informatics Research Group, Date: 15/04/2014
 */
public class KbReasonerImpl implements KbReasoner {

    private final ReadWriteLock readWriteLock = new ReentrantReadWriteLock();

    private final Lock writeLock = readWriteLock.writeLock();

    private final Lock readLock = readWriteLock.readLock();

    private final OWLReasonerFactorySelector reasonerFactorySelector;

    private final KbId kbId;

    // We don't modify the axioms in a reasoner.  We always create a fresh reasoner.
    private final AtomicReference<Reasoner> reasoner;

    private final KbAxiomSetManager kbAxiomSetManager;

    private final HandlerRegistry handlerRegistry;

    private final ListeningExecutorService applyChangesExecutorService;

    private final AtomicInteger clock = new AtomicInteger();


    @Inject
    public KbReasonerImpl(@Assisted KbId kbId, HandlerRegistry handlerRegistry, KbAxiomSetManager axiomSetManager, OWLReasonerFactorySelector reasonerFactorySelector) {
        this.kbId = kbId;
        this.handlerRegistry = handlerRegistry;
        this.kbAxiomSetManager = axiomSetManager;
        applyChangesExecutorService = MoreExecutors.listeningDecorator(Executors.newSingleThreadExecutor());
        this.reasonerFactorySelector = reasonerFactorySelector;
        this.reasoner = new AtomicReference<Reasoner>(new NullReasoner(KbDigest.emptyDigest()));

        // Seems bad... doing work in constructor
        handlerRegistry.registerHandler(ApplyChangesAction.TYPE, new ApplyChangesActionHandlerImpl());
        handlerRegistry.registerHandler(IsConsistentAction.TYPE, new IsConsistentActionHandlerImpl());
        handlerRegistry.registerHandler(IsEntailedAction.TYPE, new IsEntailedActionHandlerImpl());
        handlerRegistry.registerHandler(GetSubClassesAction.TYPE, new GetSubClassesActionHandlerImpl());
        handlerRegistry.registerHandler(GetInstancesAction.TYPE, new GetInstancesActionHandlerImpl());
        handlerRegistry.registerHandler(GetKbDigestAction.TYPE, new GetKbDigestActionHandlerImpl());
        handlerRegistry.registerHandler(ReplaceAxiomsAction.TYPE, new ReplaceAxiomsHandlerImpl());
        handlerRegistry.registerHandler(GetSuperClassesAction.TYPE, new GetSuperClassesActionHandlerImpl());

    }

    @Override
    public <A extends KbAction<R, H>, R extends Response, H extends ActionHandler> ListenableFuture<R> execute(final A action) {

        try {
            return handlerRegistry.handleAction(action);
        }
        catch (TimeOutException e) {
            return Futures.immediateFailedFuture(e);
        }
        catch (RuntimeException e) {
            return Futures.immediateFailedFuture(new InternalReasonerException(e));
        }
    }

    @Override
    public void shutDown() {
        applyChangesExecutorService.shutdownNow();
    }


    private Reasoner getReasoner() {
        return reasoner.get();
    }

    private class ApplyChangesActionHandlerImpl implements ApplyChangesActionHandler {
        @Override
        public ListenableFuture<ApplyChangesResponse> handleAction(ApplyChangesAction action) {
            return applyChanges(action.getChangeData());
        }
    }


    private class IsConsistentActionHandlerImpl implements IsConsistentHandler {
        @Override
        public ListenableFuture<IsConsistentResponse> handleAction(IsConsistentAction action) {
            Reasoner r = getReasoner();
            return Futures.immediateFuture(new IsConsistentResponse(kbId, r.getKbDigest(), r.getConsistency()));
        }
    }

    private class IsEntailedActionHandlerImpl implements IsEntailedActionHandler {
        @Override
        public ListenableFuture<IsEntailedResponse> handleAction(IsEntailedAction action) {
            Reasoner r = getReasoner();
            return Futures.immediateFuture(new IsEntailedResponse(kbId, r.getKbDigest(), action.getAxiom(), r.isEntailed(action.getAxiom())));

        }
    }

    private class GetSubClassesActionHandlerImpl implements GetSubClassesActionHandler {
        @Override
        public ListenableFuture<GetSubClassesResponse> handleAction(GetSubClassesAction action) {
            Reasoner r = getReasoner();
            return Futures.immediateFuture(new GetSubClassesResponse(kbId, r.getKbDigest(), action.getClassExpression(), r.getSubClasses(action.getClassExpression(), true)));
        }
    }

    private class GetInstancesActionHandlerImpl implements GetInstancesActionHandler {
        @Override
        public ListenableFuture<GetInstancesResponse> handleAction(final GetInstancesAction action) {

            Reasoner r = getReasoner();
            return Futures.immediateFuture(new GetInstancesResponse(kbId,
                    r.getKbDigest(),
                    action.getClassExpression(),
                    r.getInstances(action.getClassExpression(), action.getHierarchyQueryType().isDirect())));
        }
    }

    private class GetSuperClassesActionHandlerImpl implements GetSuperClassesActionHandler {
        @Override
        public ListenableFuture<GetSuperClassesResponse> handleAction(GetSuperClassesAction action) {
            Reasoner r = getReasoner();
            return Futures.immediateFuture(
                    new GetSuperClassesResponse(
                            kbId,
                            r.getKbDigest(),
                            action.getClassExpression(),
                            r.getSuperClasses(
                                    action.getClassExpression(),
                                    action.getHierarchyQueryType().isDirect()
                            )
                    )
            );
        }
    }

    private class GetKbDigestActionHandlerImpl implements GetKbDigestActionHandler {
        @Override
        public ListenableFuture<GetKbDigestResponse> handleAction(GetKbDigestAction action) {
            try {
                readLock.lock();
                return Futures.immediateFuture(new GetKbDigestResponse(kbId, kbAxiomSetManager.getKbDigest()));
            } finally {
                readLock.unlock();
            }
        }
    }

    private class ReplaceAxiomsHandlerImpl implements ReplaceAxiomsActionHandler {
        @Override
        public ListenableFuture<ReplaceAxiomsResponse> handleAction(final ReplaceAxiomsAction action) {
            return updateKb(new KbUpdateOperation<ReplaceAxiomsResponse>() {
                @Override
                public Optional<VersionedOntology> performUpdate(KbAxiomSetManager axiomSetManager) {
                    return axiomSetManager.replaceAxioms(action.getAxioms().asList());
                }

                @Override
                public ReplaceAxiomsResponse createResponse(KbId kbId, KbDigest digest) {
                    return new ReplaceAxiomsResponse(kbId, digest);
                }
            });
        }
    }

    private ListenableFuture<ApplyChangesResponse> applyChanges(final List<AxiomChangeData> changeData) {
        return updateKb(new KbUpdateOperation<ApplyChangesResponse>() {
            @Override
            public Optional<VersionedOntology> performUpdate(KbAxiomSetManager axiomSetManager) {
                return axiomSetManager.applyChanges(changeData);
            }

            @Override
            public ApplyChangesResponse createResponse(KbId kbId, KbDigest kbDigest) {
                return new ApplyChangesResponse(kbId, kbDigest);
            }
        });
    }



    private <R extends Response> ListenableFuture<R> updateKb(KbUpdateOperation<R> updateOperation) {
        try {
            writeLock.lock();
            Optional<VersionedOntology> ontology = updateOperation.performUpdate(kbAxiomSetManager);
            if (ontology.isPresent()) {
                VersionedOntology versionedOntology = ontology.get();
                OWLReasonerFactory reasonerFactory = reasonerFactorySelector.getReasonerFactory(versionedOntology.getOntology());
                return applyChangesExecutorService.submit(new ReasonerUpdater<>(kbId, clock, reasonerFactory, versionedOntology, new ReasonerUpdater.ReasonerUpdaterCallback() {
                    @Override
                    public void reasonerReady(Reasoner r) {
                        reasoner.set(r);
                    }
                }, updateOperation));
            }
            else {
                return Futures.immediateFuture(updateOperation.createResponse(kbId, kbAxiomSetManager.getKbDigest()));
            }
        } finally {
            writeLock.unlock();
        }
    }


    private static interface KbUpdateOperation<R> {
        Optional<VersionedOntology> performUpdate(KbAxiomSetManager axiomSetManager);
        R createResponse(KbId kbId, KbDigest digest);
    }

    /**
     * Updates the reasoner by creating a fresh reasoner.
     */
    private static class ReasonerUpdater<R extends Response> implements Callable<R> {

        private KbId kbId;

        private OWLReasonerFactory reasonerFactory;

        private VersionedOntology versionedOntology;

        private ReasonerUpdaterCallback callback;

        private int clockValue;

        private AtomicInteger clock;

        private KbUpdateOperation<R> updateOperation;


        private ReasonerUpdater(KbId kbId, AtomicInteger clock, OWLReasonerFactory reasonerFactory, VersionedOntology versionedOntology, ReasonerUpdaterCallback callback, KbUpdateOperation<R> updateOperation) {
            this.kbId = kbId;
            this.clockValue = clock.incrementAndGet();
            this.clock = clock;
            this.reasonerFactory = reasonerFactory;
            this.versionedOntology = versionedOntology;
            this.callback = callback;
            this.updateOperation = updateOperation;
        }

        @Override
        public R call() throws Exception {
            if (clock.get() != clockValue) {
                new ApplyChangesResponse(kbId, versionedOntology.getKbDigest());
            }

            // Dynamically select best reasoner factory?
            SimpleConfiguration configuration = new SimpleConfiguration(new ConsoleProgressMonitor());
            OWLReasoner owlReasoner = reasonerFactory.createNonBufferingReasoner(versionedOntology.getOntology(), configuration);
            boolean consistent = owlReasoner.isConsistent();
            if (consistent) {
                owlReasoner.precomputeInferences(InferenceType.CLASS_HIERARCHY, InferenceType.CLASS_ASSERTIONS);
            }
            Reasoner reasoner = new ReasonerImpl(versionedOntology.getKbDigest(), owlReasoner);
            callback.reasonerReady(reasoner);
            return updateOperation.createResponse(kbId, versionedOntology.getKbDigest());
        }

        public static interface ReasonerUpdaterCallback {
            void reasonerReady(Reasoner reasoner);
        }
    }
}
