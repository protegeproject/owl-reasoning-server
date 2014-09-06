package edu.stanford.protege.reasoning.impl;

import com.google.common.base.Optional;
import edu.stanford.protege.reasoning.action.ReasonerState;
import org.semanticweb.owlapi.reasoner.ReasonerProgressMonitor;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * @author Matthew Horridge, Stanford University, Bio-Medical Informatics Research Group, Date: 06/09/2014
 */
public abstract class KbReasonerProgressMonitor implements ReasonerProgressMonitor {

    private String reasonerName;

    private Optional<String> taskName = Optional.absent();

    private Optional<Integer> progress = Optional.absent();

    public KbReasonerProgressMonitor(String reasonerName) {
        this.reasonerName = checkNotNull(reasonerName);
    }

    public ReasonerState getProcessingState() {
        return new ReasonerState(reasonerName, taskName.or("Idle"), progress.or(0));
    }

    public abstract void stateChanged(ReasonerState state);

    @Override
    public void reasonerTaskStarted(String task) {
        taskName = Optional.of(task);
        stateChanged(getProcessingState());
    }

    @Override
    public void reasonerTaskStopped() {
        taskName = Optional.absent();
        stateChanged(getProcessingState());
    }

    @Override
    public void reasonerTaskProgressChanged(int value, int max) {
        System.out.println(value + " of " + max);
        int percent = (int) Math.round(100.0 * value / max);
        progress = Optional.of(percent);
        stateChanged(getProcessingState());
    }

    @Override
    public void reasonerTaskBusy() {
        stateChanged(getProcessingState());
    }
}
