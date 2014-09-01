package edu.stanford.protege.reasoning;

import com.google.common.util.concurrent.ListenableFuture;
import edu.stanford.protege.reasoning.Response;
import edu.stanford.protege.reasoning.action.ActionHandler;
import edu.stanford.protege.reasoning.action.ActionType;

/**
 * @author Matthew Horridge, Stanford University, Bio-Medical Informatics Research Group, Date: 15/04/2014
 */
public interface Action<R extends Response, H extends ActionHandler> {

    /**
     * Gets the type of action.
     * @return The type of action.  Not {@code null}.  Any two calls of this method will return equal action types.
     */
    ActionType<H> getType();

    /**
     * Dispatches this action to the specified handler.
     * @param handler The handler that the action should be dispatched to.  Not {@code null}.
     * @return The result of dispatching the action to the specified handler.  Not {@code null}.
     */
    ListenableFuture<R> dispatch(H handler);

}
