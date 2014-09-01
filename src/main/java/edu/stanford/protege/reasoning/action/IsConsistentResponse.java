package edu.stanford.protege.reasoning.action;

import com.google.common.base.Objects;
import com.google.common.base.Optional;
import edu.stanford.protege.reasoning.*;

/**
 * @author Matthew Horridge, Stanford University, Bio-Medical Informatics Research Group, Date: 15/04/2014
 */
public class IsConsistentResponse extends KbQueryResponse {

    private final Optional<Consistency> consistency;

    public IsConsistentResponse(KbId kbId, KbDigest kbDigest, Optional<Consistency> consistency) {
        super(kbId, kbDigest);
        this.consistency = consistency;
    }

    public Optional<Consistency> getConsistency() {
        return consistency;
    }

    @Override
    public String toString() {
        return Objects.toStringHelper("IsConsistentResponse")
                .addValue(getKbId())
                .addValue(getKbDigest())
                .addValue(consistency.isPresent() ? consistency.get() : "ABSENT").toString();
    }

    @Override
    public boolean equals(Object o) {
        if(o == this) {
            return true;
        }
        if(!(o instanceof IsConsistentResponse)) {
            return false;
        }
        IsConsistentResponse other = (IsConsistentResponse) o;
        return this.getKbId().equals(other.getKbId())
                && this.getKbDigest().equals(other.getKbDigest())
                && this.getConsistency().equals(other.getConsistency());
    }

    @Override
    public int hashCode() {
        return "IsConsistentResponse".hashCode()
                + getKbId().hashCode()
                + getKbDigest().hashCode()
                + getConsistency().hashCode();
    }
}
