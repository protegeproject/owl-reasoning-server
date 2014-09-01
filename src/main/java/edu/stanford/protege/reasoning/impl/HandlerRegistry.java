package edu.stanford.protege.reasoning.impl;

import com.google.common.util.concurrent.ListenableFuture;
import com.google.inject.Inject;
import edu.stanford.protege.reasoning.Response;
import edu.stanford.protege.reasoning.Action;
import edu.stanford.protege.reasoning.action.ActionHandler;
import edu.stanford.protege.reasoning.action.ActionType;

/**
 * @author Matthew Horridge, Stanford University, Bio-Medical Informatics Research Group, Date: 15/04/2014
 */
public class HandlerRegistry {

    private final ActionHandlerMap actionHandlerMap;

    @Inject
    public HandlerRegistry(ActionHandlerMap actionHandlerMap) {
        this.actionHandlerMap = actionHandlerMap;
    }

    public <A extends Action<R, H>, R extends Response, H extends ActionHandler> ListenableFuture<R> handleAction(A action) {
        ActionType<H> type = action.getType();
        H actionHandler = getActionHandler(type);
        return action.dispatch(actionHandler);
    }

    public <H extends ActionHandler> void registerHandler(ActionType<H> type, H handler) {
        actionHandlerMap.put(type, handler);
    }

    @SuppressWarnings("unchecked")
    private <H extends ActionHandler> H getActionHandler(ActionType<H> type) {
        H handler = actionHandlerMap.get(type);
        if(handler == null) {
            throw new RuntimeException("Handler not registered for type: " + type);
        }
        return handler;
    }


}
