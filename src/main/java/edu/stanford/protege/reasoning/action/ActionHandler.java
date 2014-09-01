package edu.stanford.protege.reasoning.action;

import com.google.common.util.concurrent.ListenableFuture;
import edu.stanford.protege.reasoning.Action;
import edu.stanford.protege.reasoning.Response;

/**
 * @author Matthew Horridge, Stanford University, Bio-Medical Informatics Research Group, Date: 15/04/2014
 */
public interface ActionHandler<A extends Action, R extends Response> {

    /**
     * Handles the specified action.
     * @param action The action to be handled.  Not {@code null}.
     * @return The result of handling the action.  The result is provided via a future.  Not {@code null}.
     */
    ListenableFuture<R> handleAction(A action);
}
