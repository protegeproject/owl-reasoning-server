package edu.stanford.protege.reasoning.protocol;

import edu.stanford.protege.reasoning.action.ProcessingState;

import java.io.IOException;

/**
 * @author Matthew Horridge, Stanford University, Bio-Medical Informatics Research Group, Date: 06/09/2014
 */
public class ProcessingStateTranslator implements Translator<ProcessingState, Messages.ProcessingState> {
    @Override
    public ProcessingState decode(Messages.ProcessingState message) {
        return new ProcessingState(
                message.getReasonerName(),
                message.getCurrentTask(),
                message.getPercentProcessed()
        );
    }

    @Override
    public ProcessingState decode(byte[] bytes) throws IOException {
        return decode(Messages.ProcessingState.parseFrom(bytes));
    }

    @Override
    public Messages.ProcessingState encode(ProcessingState object) {
        return Messages.ProcessingState.newBuilder()
                .setReasonerName(object.getReasonerName())
                .setCurrentTask(object.getCurrentTaskDescription())
                .setPercentProcessed(object.getPercentageProcessed())
                .build();
    }
}
