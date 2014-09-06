package edu.stanford.protege.reasoning.action;

import com.google.common.base.Objects;
import com.google.common.util.concurrent.ListenableFuture;
import edu.stanford.protege.reasoning.KbId;

/**
 * @author Matthew Horridge, Stanford University, Bio-Medical Informatics Research Group, Date: 06/09/2014
 */
public class GetReasonerStateAction extends KbAction<GetReasonerStateResponse, GetReasonerStateActionHandler> {

    public static final ActionType<GetReasonerStateActionHandler> TYPE = ActionType.create();

    public GetReasonerStateAction(KbId kbId) {
        super(kbId);
    }


    @Override
    public ActionType<GetReasonerStateActionHandler> getType() {
        return TYPE;
    }

    @Override
    public ListenableFuture<GetReasonerStateResponse> dispatch(GetReasonerStateActionHandler handler) {
        return handler.handleAction(this);
    }

    @Override
    public String toString() {
        return Objects.toStringHelper("GetReasonerStateAction")
                      .addValue(getKbId())
                      .toString();
    }

    @Override
    public int hashCode() {
        return "GetReasonerStateAction".hashCode() + getKbId().hashCode();
    }

    @Override
    public boolean equals(Object o) {
        if(o == this) {
            return true;
        }
        if(!(o instanceof GetReasonerStateAction)) {
            return false;
        }
        GetReasonerStateAction other = (GetReasonerStateAction) o;
        return this.getKbId().equals(other.getKbId());
    }
}
