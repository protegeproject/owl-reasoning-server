package edu.stanford.protege.reasoning.action;

import com.google.common.base.Objects;
import edu.stanford.protege.reasoning.KbId;
import edu.stanford.protege.reasoning.Response;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * @author Matthew Horridge, Stanford University, Bio-Medical Informatics Research Group, Date: 06/09/2014
 */
public class GetProcessingStateResponse implements Response {

    private KbId kbId;

    private ProcessingState processingState;

    public GetProcessingStateResponse(KbId kbId, ProcessingState processingState) {
        this.kbId = checkNotNull(kbId);
        this.processingState = checkNotNull(processingState);
    }

    public KbId getKbId() {
        return kbId;
    }

    public ProcessingState getProcessingState() {
        return processingState;
    }

    @Override
    public String toString() {
        return Objects.toStringHelper("GetProcessingStateResponse")
                      .addValue(kbId)
                      .addValue(processingState)
                      .toString();
    }

    @Override
    public int hashCode() {
        return "GetProcessingStateResponse".hashCode() + kbId.hashCode() + processingState.hashCode();
    }

    @Override
    public boolean equals(Object o) {
        if(o == this) {
            return true;
        }
        if(!(o instanceof GetProcessingStateResponse)) {
            return false;
        }
        GetProcessingStateResponse other = (GetProcessingStateResponse) o;
        return this.kbId.equals(other.kbId) && this.processingState.equals(other.processingState);
    }
}
