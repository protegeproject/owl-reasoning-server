package edu.stanford.protege.reasoning.action;

import com.google.common.base.Objects;
import edu.stanford.protege.reasoning.KbDigest;
import edu.stanford.protege.reasoning.KbId;

/**
 * @author Matthew Horridge, Stanford University, Bio-Medical Informatics Research Group, Date: 15/04/2014
 */
public class ApplyChangesResponse extends KbUpdateResponse {

    public ApplyChangesResponse(KbId kbId, KbDigest kbDigest) {
        super(kbId, kbDigest);
    }

    @Override
    public String toString() {
        return Objects.toStringHelper("ApplyChangesResponse")
                .addValue(getKbId())
                .addValue(getKbDigest())
                .toString();
    }

    @Override
    public int hashCode() {
        return "ApplyChangesResponse".hashCode() + getKbId().hashCode() + getKbDigest().hashCode();
    }

    @Override
    public boolean equals(Object o) {
        if(o == this) {
            return true;
        }
        if(!(o instanceof ApplyChangesResponse)) {
            return false;
        }
        ApplyChangesResponse other = (ApplyChangesResponse) o;
        return this.getKbId().equals(other.getKbId()) && this.getKbDigest().equals(other.getKbDigest());
    }
}
