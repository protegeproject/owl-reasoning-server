package edu.stanford.protege.reasoning.impl;


import com.google.common.cache.*;
import com.google.common.eventbus.EventBus;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.inject.Inject;
import edu.stanford.protege.reasoning.*;
import edu.stanford.protege.reasoning.action.*;

import java.util.concurrent.ExecutionException;

/**
 * @author Matthew Horridge, Stanford University, Bio-Medical Informatics Research Group, Date: 15/04/2014
 */
public class ReasoningServiceImpl implements ReasoningService {


    private final HandlerRegistry handlerRegistry;

    private final LoadingCache<KbId, KbReasoner> reasonerCache;


    @Inject
    public ReasoningServiceImpl(KbReasonerFactory kbReasonerFactory, EventBus eventBus, HandlerRegistry serverActionHandlerRegistry) {
        this.reasonerCache = CacheBuilder.newBuilder()
                .removalListener(new ReasonerCacheRemovalListener())
                .build(new ReasonerCacheLoader(kbReasonerFactory));
        this.handlerRegistry = serverActionHandlerRegistry;
    }

    @Override
    @SuppressWarnings("unchecked")
    public <A extends Action<R, H>, R extends Response, H extends ActionHandler> ListenableFuture<R> execute(A action) {
        if(action instanceof KbAction) {
            return executeKnowledgeBaseAction((KbAction) action);
        }
        else {
            return executeServerAction(action);
        }
    }

    private <A extends Action<R, H>, R extends Response, H extends ActionHandler> ListenableFuture<R> executeServerAction(A action) {
        return handlerRegistry.handleAction(action);
    }

    private <A extends KbAction<R, H>, R extends Response, H extends ActionHandler> ListenableFuture<R> executeKnowledgeBaseAction(A action) {
        KbId kbId = action.getKbId();
        KbReasoner kbReasoner = getKbReasoner(kbId);
        return kbReasoner.execute(action);

    }

    private KbReasoner getKbReasoner(final KbId kbId) {
        try {
            return reasonerCache.get(kbId);
        } catch (ExecutionException e) {
            throw new RuntimeException("There was an error creating the reasoner");
        }
    }

    @Override
    public void shutDown() {
        reasonerCache.invalidateAll();
    }


    private static class ReasonerCacheLoader extends CacheLoader<KbId, KbReasoner> {

        private final KbReasonerFactory kbReasonerFactory;

        private ReasonerCacheLoader(KbReasonerFactory kbReasonerFactory) {
            this.kbReasonerFactory = kbReasonerFactory;
        }

        @Override
        public KbReasoner load(KbId key) throws Exception {
            return createKb(key);
        }

        private KbReasoner createKb(KbId kbId) {
            return kbReasonerFactory.createReasoner(kbId);
        }
    }

    private static class ReasonerCacheRemovalListener implements RemovalListener<KbId, KbReasoner> {
        @Override
        public void onRemoval(RemovalNotification<KbId, KbReasoner> notification) {
            KbReasoner reasoner = notification.getValue();
            if(reasoner != null) {
                reasoner.shutDown();
            }
        }
    }
}
