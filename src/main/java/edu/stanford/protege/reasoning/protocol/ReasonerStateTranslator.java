package edu.stanford.protege.reasoning.protocol;

import java.io.IOException;

/**
 * @author Matthew Horridge, Stanford University, Bio-Medical Informatics Research Group, Date: 06/09/2014
 */
public class ReasonerStateTranslator implements Translator<edu.stanford.protege.reasoning.action.ReasonerState, Messages.ReasonerState> {
    @Override
    public edu.stanford.protege.reasoning.action.ReasonerState decode(Messages.ReasonerState message) {
        return new edu.stanford.protege.reasoning.action.ReasonerState(
                message.getReasonerName(),
                message.getStateDescription(),
                message.getPercentProcessed()
        );
    }

    @Override
    public edu.stanford.protege.reasoning.action.ReasonerState decode(byte[] bytes) throws IOException {
        return decode(Messages.ReasonerState.parseFrom(bytes));
    }

    @Override
    public Messages.ReasonerState encode(edu.stanford.protege.reasoning.action.ReasonerState object) {
        return Messages.ReasonerState.newBuilder()
                .setReasonerName(object.getReasonerName())
                .setStateDescription(object.getStateDescription())
                .setPercentProcessed(object.getPercentageProcessed())
                .build();
    }
}
