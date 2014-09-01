package edu.stanford.protege.reasoning;

import com.google.common.util.concurrent.ListenableFuture;
import edu.stanford.protege.reasoning.action.ActionHandler;

/**
 * @author Matthew Horridge, Stanford University, Bio-Medical Informatics Research Group, Date: 15/04/2014
 */
public interface ReasoningService extends HasShutDown {

    /**
     * Asks for an action to be executed.  The action will be executed at some time in the future.  The order of
     * action submission does not guarantee the order of action execution.
     * @param action The action to be executed.  Not {@code null}.
     * @param <A> The action type.
     * @param <R> The result type.
     * @param <H> The handler type.
     * @return The future result as a {@link ListenableFuture}.  Not {@code null}.  The actual result will be available
     * after the action has been executed.
     */
    <A extends Action<R, H>, R extends Response, H extends ActionHandler> ListenableFuture<R> execute(A action);



}
