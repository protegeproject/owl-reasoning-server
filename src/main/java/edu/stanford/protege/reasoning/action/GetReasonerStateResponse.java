package edu.stanford.protege.reasoning.action;

import com.google.common.base.Objects;
import edu.stanford.protege.reasoning.KbId;
import edu.stanford.protege.reasoning.Response;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * @author Matthew Horridge, Stanford University, Bio-Medical Informatics Research Group, Date: 06/09/2014
 */
public class GetReasonerStateResponse implements Response {

    private KbId kbId;

    private ReasonerState reasonerState;

    public GetReasonerStateResponse(KbId kbId, ReasonerState reasonerState) {
        this.kbId = checkNotNull(kbId);
        this.reasonerState = checkNotNull(reasonerState);
    }

    public KbId getKbId() {
        return kbId;
    }

    public ReasonerState getReasonerState() {
        return reasonerState;
    }

    @Override
    public String toString() {
        return Objects.toStringHelper("GetReasonerStateResponse")
                      .addValue(kbId)
                      .addValue(reasonerState)
                      .toString();
    }

    @Override
    public int hashCode() {
        return "GetReasonerStateResponse".hashCode() + kbId.hashCode() + reasonerState.hashCode();
    }

    @Override
    public boolean equals(Object o) {
        if(o == this) {
            return true;
        }
        if(!(o instanceof GetReasonerStateResponse)) {
            return false;
        }
        GetReasonerStateResponse other = (GetReasonerStateResponse) o;
        return this.kbId.equals(other.kbId) && this.reasonerState.equals(other.reasonerState);
    }
}
