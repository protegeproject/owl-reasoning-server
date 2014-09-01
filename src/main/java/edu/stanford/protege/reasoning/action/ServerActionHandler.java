package edu.stanford.protege.reasoning.action;

import edu.stanford.protege.reasoning.Response;
import edu.stanford.protege.reasoning.Action;

/**
 * @author Matthew Horridge, Stanford University, Bio-Medical Informatics Research Group, Date: 20/07/2014
 */
public interface ServerActionHandler<A extends Action, R extends Response> extends ActionHandler<A, R> {
}
