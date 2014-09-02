package edu.stanford.protege.reasoning.impl;

import com.google.common.util.concurrent.ListenableFuture;
import edu.stanford.protege.reasoning.HasShutDown;
import edu.stanford.protege.reasoning.Response;
import edu.stanford.protege.reasoning.action.ActionHandler;
import edu.stanford.protege.reasoning.action.KbAction;
import org.semanticweb.owlapi.reasoner.TimeOutException;

import java.util.concurrent.Future;

/**
 * @author Matthew Horridge, Stanford University, Bio-Medical Informatics Research Group, Date: 15/04/2014
 */
public interface KbReasoner extends HasShutDown {

    /**
     * Applies an action to the reasoner.  Actions may be applied concurrently.  The order in which actions are
     * submitted does not provide a guaranteed execution order.  For example, submitting an action to update the
     * reasoner followed by an action to query the reasoner does not guarantee that the action to query the reasoner
     * will be executed after the action that updated the reasoner.
     * @param action
     * @param <A>
     * @param <R>
     * @param <H>
     * @return
     */
    <A extends KbAction<R, H>, R extends Response, H extends ActionHandler> ListenableFuture<R> execute(A action) throws
                                                                                                                  TimeOutException;
}
