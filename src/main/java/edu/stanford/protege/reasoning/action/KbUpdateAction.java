package edu.stanford.protege.reasoning.action;

import edu.stanford.protege.reasoning.KbId;

/**
 * @author Matthew Horridge, Stanford University, Bio-Medical Informatics Research Group, Date: 16/04/2014
 */
public abstract class KbUpdateAction<R extends KbUpdateResponse, H extends ActionHandler> extends KbAction<R, H> {

    protected KbUpdateAction(KbId kbId) {
        super(kbId);
    }
}
