package edu.stanford.protege.reasoning.action;

import com.google.common.base.Objects;
import com.google.common.util.concurrent.ListenableFuture;
import edu.stanford.protege.reasoning.KbId;

/**
 * @author Matthew Horridge, Stanford University, Bio-Medical Informatics Research Group, Date: 28/08/2014
 */
public class GetKbDigestAction extends KbAction<GetKbDigestResponse, GetKbDigestHandler> {

    public static final ActionType<GetKbDigestHandler> TYPE = ActionType.create();

    public GetKbDigestAction(KbId kbId) {
        super(kbId);
    }

    @Override
    public ActionType<GetKbDigestHandler> getType() {
        return TYPE;
    }

    @Override
    public ListenableFuture<GetKbDigestResponse> dispatch(GetKbDigestHandler handler) {
        return handler.handleAction(this);
    }

    @Override
    public int hashCode() {
        return "GetKbDigestAction".hashCode() + getKbId().hashCode();
    }

    @Override
    public boolean equals(Object o) {
        if(o == this) {
            return true;
        }
        if(!(o instanceof GetKbDigestAction)) {
            return false;
        }
        GetKbDigestAction other = (GetKbDigestAction) o;
        return this.getKbId().equals(other.getKbId());
    }

    @Override
    public String toString() {
        return Objects.toStringHelper("GetKbDigestAction")
                .addValue(getKbId())
                .toString();
    }
}

