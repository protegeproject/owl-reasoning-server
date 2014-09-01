package edu.stanford.protege.reasoning.action;

import edu.stanford.protege.reasoning.Action;
import edu.stanford.protege.reasoning.KbId;
import edu.stanford.protege.reasoning.Response;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * @author Matthew Horridge, Stanford University, Bio-Medical Informatics Research Group, Date: 15/04/2014
 */
public abstract class KbAction<R extends Response, H extends ActionHandler> implements Action<R, H> {

    private final KbId kbId;

    protected KbAction(KbId kbId) {
        this.kbId = checkNotNull(kbId);
    }

    public KbId getKbId() {
        return kbId;
    }

}
