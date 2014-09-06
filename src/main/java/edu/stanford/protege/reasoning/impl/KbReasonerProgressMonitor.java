package edu.stanford.protege.reasoning.impl;

import com.google.common.base.Optional;
import edu.stanford.protege.reasoning.action.Progress;
import edu.stanford.protege.reasoning.action.ReasonerState;
import org.semanticweb.owlapi.reasoner.ReasonerProgressMonitor;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * @author Matthew Horridge, Stanford University, Bio-Medical Informatics Research Group, Date: 06/09/2014
 */
public abstract class KbReasonerProgressMonitor implements ReasonerProgressMonitor {

    private String reasonerName;

    private Optional<String> taskName = Optional.absent();

    private Optional<Progress> progress = Optional.absent();

    public KbReasonerProgressMonitor(String reasonerName) {
        this.reasonerName = checkNotNull(reasonerName);
    }

    public ReasonerState getProcessingState() {
        return new ReasonerState(reasonerName, taskName.or("Idle"), progress);
    }

    public abstract void stateChanged(ReasonerState state);

    @Override
    public void reasonerTaskStarted(String task) {
        taskName = Optional.of(task);
        stateChanged(getProcessingState());
    }

    @Override
    public void reasonerTaskStopped() {
        stateChanged(getProcessingState());
        taskName = Optional.absent();
    }

    @Override
    public void reasonerTaskProgressChanged(int value, int max) {
        progress = Optional.of(Progress.from(0).to(max).withValue(value));
        stateChanged(getProcessingState());
    }

    @Override
    public void reasonerTaskBusy() {
        progress = Optional.of(Progress.indeterminate());
        stateChanged(getProcessingState());
    }
}
