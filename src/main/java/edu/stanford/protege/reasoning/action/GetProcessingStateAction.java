package edu.stanford.protege.reasoning.action;

import com.google.common.base.Objects;
import com.google.common.util.concurrent.ListenableFuture;
import edu.stanford.protege.reasoning.KbId;

/**
 * @author Matthew Horridge, Stanford University, Bio-Medical Informatics Research Group, Date: 06/09/2014
 */
public class GetProcessingStateAction extends KbAction<GetProcessingStateResponse, GetProcessingStateActionHandler> {

    public static final ActionType<GetProcessingStateActionHandler> TYPE = ActionType.create();

    public GetProcessingStateAction(KbId kbId) {
        super(kbId);
    }


    @Override
    public ActionType<GetProcessingStateActionHandler> getType() {
        return TYPE;
    }

    @Override
    public ListenableFuture<GetProcessingStateResponse> dispatch(GetProcessingStateActionHandler handler) {
        return handler.handleAction(this);
    }

    @Override
    public String toString() {
        return Objects.toStringHelper("GetProcessingStateAction")
                      .addValue(getKbId())
                      .toString();
    }

    @Override
    public int hashCode() {
        return "GetProcessingStateAction".hashCode() + getKbId().hashCode();
    }

    @Override
    public boolean equals(Object o) {
        if(o == this) {
            return true;
        }
        if(!(o instanceof GetProcessingStateAction)) {
            return false;
        }
        GetProcessingStateAction other = (GetProcessingStateAction) o;
        return this.getKbId().equals(other.getKbId());
    }
}
