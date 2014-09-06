package edu.stanford.protege.reasoning;

import com.google.common.base.Objects;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

/**
 * @author Matthew Horridge, Stanford University, Bio-Medical Informatics Research Group, Date: 06/09/2014
 */
public class ReasoningStats {

    private final String reasonerName;

    private final long processingTime;

    public ReasoningStats() {
        reasonerName = "None";
        processingTime = 0;
    }

    public ReasoningStats(String reasonerName, long processingTime) {
        this.reasonerName = checkNotNull(reasonerName);
        checkArgument(0 <= processingTime);
        this.processingTime = processingTime;
    }

    public String getReasonerName() {
        return reasonerName;
    }

    public long getProcessingTime() {
        return processingTime;
    }

    @Override
    public String toString() {
        return Objects.toStringHelper("ReasoningStats")
                      .add("reasoner-name", reasonerName)
                      .add("processing-time", processingTime)
                      .toString();
    }

    @Override
    public int hashCode() {
        return "ReasoningStats".hashCode() + reasonerName.hashCode() + (int) processingTime;
    }

    @Override
    public boolean equals(Object o) {
        if(o == this) {
            return true;
        }
        if(!(o instanceof ReasoningStats)) {
            return false;
        }
        ReasoningStats other = (ReasoningStats) o;
        return this.reasonerName.equals(other.reasonerName)
                && this.processingTime == other.processingTime;
    }
}
