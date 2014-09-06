package edu.stanford.protege.reasoning.action;

import com.google.common.base.Objects;

import static com.google.common.base.Preconditions.*;

/**
 * @author Matthew Horridge, Stanford University, Bio-Medical Informatics Research Group, Date: 06/09/2014
 */
public class ReasonerState {

    private final String reasonerName;

    private final String stateDescription;

    private final int percentageProcessed;

    public ReasonerState(String reasonerName, String stateDescription, int percentageProcessed) {
        this.reasonerName = checkNotNull(reasonerName);
        this.stateDescription = checkNotNull(stateDescription);
        checkArgument(0 <= percentageProcessed && percentageProcessed <= 100);
        this.percentageProcessed = percentageProcessed;
    }

    public String getReasonerName() {
        return reasonerName;
    }

    public String getStateDescription() {
        return stateDescription;
    }

    public int getPercentageProcessed() {
        return percentageProcessed;
    }

    @Override
    public String toString() {
        return Objects.toStringHelper("ReasonerState")
                      .add("reasonerName", reasonerName)
                      .add("task", stateDescription)
                      .add("percentage", percentageProcessed).toString();
    }

    @Override
    public int hashCode() {
        return "ReasonerState".hashCode()
                + reasonerName.hashCode() + stateDescription.hashCode()
                + percentageProcessed;
    }

    @Override
    public boolean equals(Object o) {
        if(o == this) {
            return true;
        }
        if(!(o instanceof ReasonerState)) {
            return false;
        }
        ReasonerState other = (ReasonerState) o;
        return this.reasonerName.equals(other.reasonerName)
                && this.stateDescription.equals(other.stateDescription)
                && this.percentageProcessed == other.percentageProcessed;
    }
}
