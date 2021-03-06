package edu.stanford.protege.reasoning.impl;

import com.google.common.base.Optional;
import com.google.common.base.Stopwatch;
import com.google.common.util.concurrent.*;
import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;
import com.google.inject.name.Named;
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

    public static final String TIMEOUT_VARIABLE_NAME = "timeOut";

    private final ReadWriteLock readWriteLock = new ReentrantReadWriteLock();

    private final Lock writeLock = readWriteLock.writeLock();

    private final Lock readLock = readWriteLock.readLock();

    private final OWLReasonerFactorySelector reasonerFactorySelector;

    private final KbId kbId;

    // We don't modify the axioms in a reasoner.  We always create a fresh reasoner.
    private final AtomicReference<Reasoner> reasoner;

    private final AtomicReference<ReasoningStats> stats;

    private final AtomicReference<ReasonerState> processingState;

    private final KbAxiomSetManager kbAxiomSetManager;

    private final HandlerRegistry handlerRegistry;

    private final ListeningExecutorService applyChangesExecutorService;

    private final ListeningExecutorService queryExecutorService;

    private final AtomicInteger clock = new AtomicInteger();

    private final long timeOut;


    @Inject
    public KbReasonerImpl(
            @Assisted KbId kbId,
            HandlerRegistry handlerRegistry,
            KbAxiomSetManager axiomSetManager,
            OWLReasonerFactorySelector reasonerFactorySelector,
            @Named(TIMEOUT_VARIABLE_NAME) long timeOut) {
        this.kbId = kbId;
        this.handlerRegistry = handlerRegistry;
        this.kbAxiomSetManager = axiomSetManager;
        this.applyChangesExecutorService = MoreExecutors.listeningDecorator(Executors.newSingleThreadExecutor());
        this.queryExecutorService = MoreExecutors.listeningDecorator(reasonerFactorySelector.getQueryExecutorService());
        this.reasonerFactorySelector = reasonerFactorySelector;
        this.reasoner = new AtomicReference<Reasoner>(new NullReasoner(KbDigest.emptyDigest()));
        this.stats = new AtomicReference<>(new ReasoningStats());
        this.processingState = new AtomicReference<>(
                new ReasonerState(
                        "None", KbDigest.emptyDigest(), "Idle", Optional.<Progress>absent()
                )
        );
        this.timeOut = timeOut;

        // Seems bad... doing work in constructor
        handlerRegistry.registerHandler(ApplyChangesAction.TYPE, new ApplyChangesActionHandlerImpl());
        handlerRegistry.registerHandler(IsConsistentAction.TYPE, new IsConsistentActionHandlerImpl());
        handlerRegistry.registerHandler(IsEntailedAction.TYPE, new IsEntailedActionHandlerImpl());
        handlerRegistry.registerHandler(GetSubClassesAction.TYPE, new GetSubClassesActionHandlerImpl());
        handlerRegistry.registerHandler(GetInstancesAction.TYPE, new GetInstancesActionHandlerImpl());
        handlerRegistry.registerHandler(GetKbDigestAction.TYPE, new GetKbDigestActionHandlerImpl());
        handlerRegistry.registerHandler(ReplaceAxiomsAction.TYPE, new ReplaceAxiomsHandlerImpl());
        handlerRegistry.registerHandler(GetSuperClassesAction.TYPE, new GetSuperClassesActionHandlerImpl());
        handlerRegistry.registerHandler(GetReasonerStateAction.TYPE, new GetReasonerStateActionHandlerImpl());
        handlerRegistry.registerHandler(GetEquivalentClassesAction.TYPE, new GetEquivalentClassesActionHandlerImpl());

    }

    @Override
    public <A extends KbAction<R, H>, R extends Response, H extends ActionHandler> ListenableFuture<R> execute(
            final A action) {
            return handlerRegistry.handleAction(action);
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
            return queryExecutorService.submit(
                    new Callable<IsConsistentResponse>() {
                        @Override
                        public IsConsistentResponse call() throws Exception {
                            Reasoner r = getReasoner();
                            return new IsConsistentResponse(kbId, r.getKbDigest(), r.getConsistency());
                        }
                    }
            );
        }
    }

    private class IsEntailedActionHandlerImpl implements IsEntailedActionHandler {
        @Override
        public ListenableFuture<IsEntailedResponse> handleAction(final IsEntailedAction action) {
            return queryExecutorService.submit(
                    new Callable<IsEntailedResponse>() {
                        @Override
                        public IsEntailedResponse call() throws Exception {
                            Reasoner r = getReasoner();
                            return new IsEntailedResponse(
                                    kbId, r.getKbDigest(), action.getAxiom(), r.isEntailed(action.getAxiom())
                            );
                        }
                    }
            );
        }
    }

    private class GetSubClassesActionHandlerImpl implements GetSubClassesActionHandler {
        @Override
        public ListenableFuture<GetSubClassesResponse> handleAction(final GetSubClassesAction action) {
            return queryExecutorService.submit(
                    new Callable<GetSubClassesResponse>() {
                        @Override
                        public GetSubClassesResponse call() throws Exception {
                            Reasoner r = getReasoner();
                            return new GetSubClassesResponse(
                                    kbId,
                                    r.getKbDigest(),
                                    action.getClassExpression(),
                                    r.getSubClasses(action.getClassExpression(), true)
                            );
                        }
                    }
            );
        }
    }

    private class GetInstancesActionHandlerImpl implements GetInstancesActionHandler {
        @Override
        public ListenableFuture<GetInstancesResponse> handleAction(final GetInstancesAction action) {
            return queryExecutorService.submit(
                    new Callable<GetInstancesResponse>() {
                        @Override
                        public GetInstancesResponse call() throws Exception {
                            Reasoner r = getReasoner();
                            return new GetInstancesResponse(
                                    kbId, r.getKbDigest(), action.getClassExpression(), r.getInstances(
                                    action.getClassExpression(), action.getHierarchyQueryType().isDirect()
                            )
                            );
                        }
                    }
            );
        }
    }

    private class GetSuperClassesActionHandlerImpl implements GetSuperClassesActionHandler {
        @Override
        public ListenableFuture<GetSuperClassesResponse> handleAction(final GetSuperClassesAction action) {
            return queryExecutorService.submit(
                    new Callable<GetSuperClassesResponse>() {
                        @Override
                        public GetSuperClassesResponse call() throws Exception {
                            Reasoner r = getReasoner();
                            return new GetSuperClassesResponse(
                                    kbId, r.getKbDigest(), action.getClassExpression(), r.getSuperClasses(
                                    action.getClassExpression(), action.getHierarchyQueryType().isDirect()
                            )

                            );
                        }
                    }
            );
        }
    }

    private class GetEquivalentClassesActionHandlerImpl implements GetEquivalentClassesActionHandler {
        @Override
        public ListenableFuture<GetEquivalentClassesResponse> handleAction(final GetEquivalentClassesAction action) {
            return queryExecutorService.submit(
                    new Callable<GetEquivalentClassesResponse>() {
                        @Override
                        public GetEquivalentClassesResponse call() throws Exception {
                            Reasoner r = getReasoner();
                            return new GetEquivalentClassesResponse(kbId, r.getKbDigest(), action.getClassExpression(), r.getEquivalentClasses(action.getClassExpression()));
                        }
                    }
            );
        }
    }

    private class GetKbDigestActionHandlerImpl implements GetKbDigestActionHandler {
        @Override
        public ListenableFuture<GetKbDigestResponse> handleAction(GetKbDigestAction action) {
            return queryExecutorService.submit(
                    new Callable<GetKbDigestResponse>() {
                        @Override
                        public GetKbDigestResponse call() throws Exception {
                            try {
                                readLock.lock();
                                return new GetKbDigestResponse(kbId, getReasoner().getKbDigest());
                            } finally {
                                readLock.unlock();
                            }
                        }
                    }
            );

        }
    }

    private class ReplaceAxiomsHandlerImpl implements ReplaceAxiomsActionHandler {
        @Override
        public ListenableFuture<ReplaceAxiomsResponse> handleAction(final ReplaceAxiomsAction action) {
            return updateKb(
                    new KbUpdateOperation<ReplaceAxiomsResponse>() {
                        @Override
                        public Optional<VersionedOntology> performUpdate(KbAxiomSetManager axiomSetManager) {
                            return axiomSetManager.replaceAxioms(action.getAxioms());
                        }

                        @Override
                        public ReplaceAxiomsResponse createResponse(KbId kbId, KbDigest digest) {
                            return new ReplaceAxiomsResponse(kbId, digest);
                        }
                    }
            );
        }
    }

    private ListenableFuture<ApplyChangesResponse> applyChanges(final List<AxiomChangeData> changeData) {
        return updateKb(
                new KbUpdateOperation<ApplyChangesResponse>() {
                    @Override
                    public Optional<VersionedOntology> performUpdate(KbAxiomSetManager axiomSetManager) {
                        return axiomSetManager.applyChanges(changeData);
                    }

                    @Override
                    public ApplyChangesResponse createResponse(KbId kbId, KbDigest kbDigest) {
                        return new ApplyChangesResponse(kbId, kbDigest);
                    }
                }
        );
    }

    private class GetReasonerStateActionHandlerImpl implements GetReasonerStateActionHandler {
        @Override
        public ListenableFuture<GetReasonerStateResponse> handleAction(GetReasonerStateAction action) {
            return Futures.immediateFuture(new GetReasonerStateResponse(kbId, processingState.get()));
        }
    }


    private <R extends Response> ListenableFuture<R> updateKb(KbUpdateOperation<R> updateOperation) {
        try {
            // This isn't correct.  We want to update the reasoner and only set it as the new reasoner if there
            // weren't any errors.  I think.
            writeLock.lock();
            Optional<VersionedOntology> ontology = updateOperation.performUpdate(kbAxiomSetManager);
            if (ontology.isPresent()) {
                VersionedOntology versionedOntology = ontology.get();
                OWLReasonerFactory reasonerFactory = reasonerFactorySelector.getReasonerFactory(versionedOntology.getOntology());
                return applyChangesExecutorService.submit(new ReasonerUpdater<>(kbId,
                                                                                clock,
                                                                                reasonerFactory,
                                                                                versionedOntology,
                                                                                new ReasonerUpdater
                                                                                        .ReasonerUpdaterCallback() {
                                                                                    @Override
                                                                                    public void reasonerReady(
                                                                                            Reasoner r,
                                                                                            ReasoningStats
                                                                                                    reasoningStats) {
                                                                                        reasoner.set(r);
                                                                                        stats.set(reasoningStats);
                                                                                    }

                                                                                    @Override
                                                                                    public void processing(
                                                                                            ReasonerState state) {
                                                                                        processingState.set(state);
                                                                                    }
                                                                                },
                                                                                updateOperation,
                                                                                timeOut));

            }
            else {
                return Futures.immediateFuture(updateOperation.createResponse(kbId, getReasoner().getKbDigest()));
            }
        }  finally {
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

        private final KbId kbId;

        private final OWLReasonerFactory reasonerFactory;

        private final VersionedOntology versionedOntology;

        private final ReasonerUpdaterCallback callback;

        private final int clockValue;

        private final AtomicInteger clock;

        private final KbUpdateOperation<R> updateOperation;

        private long timeOut;


        private ReasonerUpdater(
                KbId kbId,
                AtomicInteger clock,
                OWLReasonerFactory reasonerFactory,
                VersionedOntology versionedOntology,
                ReasonerUpdaterCallback callback,
                KbUpdateOperation<R> updateOperation,
                long timeOut) {
            this.kbId = kbId;
            this.clockValue = clock.incrementAndGet();
            this.clock = clock;
            this.reasonerFactory = reasonerFactory;
            this.versionedOntology = versionedOntology;
            this.callback = callback;
            this.updateOperation = updateOperation;
            this.timeOut = timeOut;
        }

        @Override
        public R call() throws Exception {
            try {
                if (clock.get() != clockValue) {
                    return updateOperation.createResponse(kbId, versionedOntology.getKbDigest());
                }
                callback.processing(
                        new ReasonerState(
                                reasonerFactory.getReasonerName(),
                                versionedOntology.getKbDigest(),
                                "Loading reasoner",
                                Optional.of(Progress.indeterminate())
                        )
                );
                // Dynamically select best reasoner factory?
                Stopwatch stopwatch = Stopwatch.createStarted();
                SimpleConfiguration configuration = new SimpleConfiguration(
                        new KbReasonerProgressMonitor(reasonerFactory.getReasonerName(), versionedOntology.getKbDigest()) {
                            @Override
                            public void stateChanged(ReasonerState processingState) {
                                callback.processing(processingState);
                            }
                        }
                , FreshEntityPolicy.ALLOW, timeOut, IndividualNodeSetPolicy.BY_NAME);
                OWLReasoner owlReasoner = reasonerFactory.createNonBufferingReasoner(
                        versionedOntology.getOntology(), configuration
                );
                boolean consistent = owlReasoner.isConsistent();
                if (consistent) {
                    owlReasoner.precomputeInferences(InferenceType.CLASS_HIERARCHY, InferenceType.CLASS_ASSERTIONS);
                }
                stopwatch.stop();
                Reasoner reasoner = new ReasonerImpl(versionedOntology.getKbDigest(), owlReasoner);
                callback.reasonerReady(
                        reasoner, new ReasoningStats(
                        reasonerFactory.getReasonerName(), stopwatch.elapsed(TimeUnit.MILLISECONDS)
                )
                );
                callback.processing(
                        new ReasonerState(
                                reasonerFactory.getReasonerName(),
                                reasoner.getKbDigest(),
                                "Ready",
                                Optional.<Progress>absent()
                        )
                );
                return updateOperation.createResponse(kbId, versionedOntology.getKbDigest());
            } catch (TimeOutException e) {
                throw new ReasonerTimeOutException();
            } catch (ReasonerInternalErrorException e) {
                throw e;
            } catch (Throwable t) {
                throw new ReasonerInternalErrorException(t);
            }
        }

        public static interface ReasonerUpdaterCallback {

            /**
             * Called when the processing state of the reasoner has changed.  This could be due to a reasoning stage
             * change or an increment of progress during a specific stage.
             * @param reasonerState The state.  Not {@code null}.
             */
            void processing(ReasonerState reasonerState);

            void reasonerReady(Reasoner reasoner, ReasoningStats reasoningStats);
        }
    }
}
