package edu.stanford.protege.reasoning.action;

import com.google.common.base.Objects;

import static com.google.common.base.Preconditions.*;

/**
 * @author Matthew Horridge, Stanford University, Bio-Medical Informatics Research Group, Date: 06/09/2014
 */
public class ProcessingState {

    private final String reasonerName;

    private final String currentTaskDescription;

    private final int percentageProcessed;

    public ProcessingState(String reasonerName, String currentTaskDescription, int percentageProcessed) {
        this.reasonerName = checkNotNull(reasonerName);
        this.currentTaskDescription = checkNotNull(currentTaskDescription);
        checkArgument(0 <= percentageProcessed && percentageProcessed <= 100);
        this.percentageProcessed = percentageProcessed;
    }

    public String getReasonerName() {
        return reasonerName;
    }

    public String getCurrentTaskDescription() {
        return currentTaskDescription;
    }

    public int getPercentageProcessed() {
        return percentageProcessed;
    }

    @Override
    public String toString() {
        return Objects.toStringHelper("ProcessingState")
                      .add("reasonerName", reasonerName)
                      .add("task", currentTaskDescription)
                      .add("percentage", percentageProcessed).toString();
    }

    @Override
    public int hashCode() {
        return "ProcessingState".hashCode()
                + reasonerName.hashCode() + currentTaskDescription.hashCode()
                + percentageProcessed;
    }

    @Override
    public boolean equals(Object o) {
        if(o == this) {
            return true;
        }
        if(!(o instanceof ProcessingState)) {
            return false;
        }
        ProcessingState other = (ProcessingState) o;
        return this.reasonerName.equals(other.reasonerName)
                && this.currentTaskDescription.equals(other.currentTaskDescription)
                && this.percentageProcessed == other.percentageProcessed;
    }
}
