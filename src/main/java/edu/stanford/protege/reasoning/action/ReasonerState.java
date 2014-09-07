package edu.stanford.protege.reasoning.action;

import com.google.common.base.Objects;
import com.google.common.base.Optional;
import edu.stanford.protege.reasoning.KbDigest;

import static com.google.common.base.Preconditions.*;

/**
 * @author Matthew Horridge, Stanford University, Bio-Medical Informatics Research Group, Date: 06/09/2014
 */
public class ReasonerState {

    private final String reasonerName;

    private final String stateDescription;

    private final KbDigest reasonerKbDigest;

    private final Optional<Progress> progress;

    public ReasonerState(String reasonerName, KbDigest kbDigest, String stateDescription, Optional<Progress> progress) {
        this.reasonerName = checkNotNull(reasonerName);
        this.reasonerKbDigest = checkNotNull(kbDigest);
        this.stateDescription = checkNotNull(stateDescription);
        this.progress = checkNotNull(progress);
    }

    public String getReasonerName() {
        return reasonerName;
    }

    public KbDigest getReasonerKbDigest() {
        return reasonerKbDigest;
    }

    public String getStateDescription() {
        return stateDescription;
    }

    public Optional<Progress> getProgress() {
        return progress;
    }

    @Override
    public String toString() {
        return Objects.toStringHelper("ReasonerState")
                      .add("reasonerName", reasonerName)
                      .addValue(getReasonerKbDigest())
                      .add("task", stateDescription)
                      .add("progress", progress).toString();
    }

    @Override
    public int hashCode() {
        return "ReasonerState".hashCode()
                + reasonerName.hashCode() + getReasonerKbDigest().hashCode() + stateDescription.hashCode()
                + progress.hashCode();
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
                && this.getReasonerKbDigest().equals(other.getReasonerKbDigest())
                && this.stateDescription.equals(other.stateDescription)
                && this.progress.equals(other.progress);
    }
}
