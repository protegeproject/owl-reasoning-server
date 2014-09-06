package edu.stanford.protege.reasoning.protocol;

import com.google.common.base.Optional;
import edu.stanford.protege.reasoning.action.Progress;

import java.io.IOException;

/**
 * @author Matthew Horridge, Stanford University, Bio-Medical Informatics Research Group, Date: 06/09/2014
 */
public class ReasonerStateTranslator implements Translator<edu.stanford.protege.reasoning.action.ReasonerState, Messages.ReasonerState> {

    private Translator<Progress, Messages.Progress> progressTranslator;

    @Override
    public edu.stanford.protege.reasoning.action.ReasonerState decode(Messages.ReasonerState message) {
        return new edu.stanford.protege.reasoning.action.ReasonerState(
                message.getReasonerName(),
                message.getStateDescription(),
                Optional.<Progress>of(progressTranslator.decode(message.getProgress()))
        );
    }

    @Override
    public edu.stanford.protege.reasoning.action.ReasonerState decode(byte[] bytes) throws IOException {
        return decode(Messages.ReasonerState.parseFrom(bytes));
    }

    @Override
    public Messages.ReasonerState encode(edu.stanford.protege.reasoning.action.ReasonerState object) {
        Messages.ReasonerState.Builder builder = Messages.ReasonerState.newBuilder();
        if(object.getProgress().isPresent()) {
            builder.setProgress(progressTranslator.encode(object.getProgress().get()));
        }
        return builder
                .setReasonerName(object.getReasonerName())
                .setStateDescription(object.getStateDescription())
                .build();
    }
}
