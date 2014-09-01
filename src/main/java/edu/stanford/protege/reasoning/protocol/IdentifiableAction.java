package edu.stanford.protege.reasoning.protocol;

import edu.stanford.protege.reasoning.Action;

/**
 * @author Matthew Horridge, Stanford University, Bio-Medical Informatics Research Group, Date: 29/08/2014
 */
public class IdentifiableAction {

    private final Integer id;

    private final Action action;

    public IdentifiableAction(Integer id, Action action) {
        this.id = id;
        this.action = action;
    }

    public Integer getId() {
        return id;
    }

    public Action getAction() {
        return action;
    }
}
