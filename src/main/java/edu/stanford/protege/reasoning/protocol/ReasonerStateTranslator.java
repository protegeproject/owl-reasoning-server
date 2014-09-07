package edu.stanford.protege.reasoning.protocol;

import com.google.common.base.Optional;
import edu.stanford.protege.reasoning.KbDigest;
import edu.stanford.protege.reasoning.action.Progress;

import javax.inject.Inject;
import java.io.IOException;

/**
 * @author Matthew Horridge, Stanford University, Bio-Medical Informatics Research Group, Date: 06/09/2014
 */
public class ReasonerStateTranslator implements Translator<edu.stanford.protege.reasoning.action.ReasonerState, Messages.ReasonerState> {

    private Translator<KbDigest, Messages.KbDigest> kbDigestTranslator;

    private Translator<Progress, Messages.Progress> progressTranslator;

    @Inject
    public ReasonerStateTranslator(Translator<KbDigest, Messages.KbDigest> kbDigestTranslator,
                                   Translator<Progress, Messages.Progress> progressTranslator) {
        this.kbDigestTranslator = kbDigestTranslator;
        this.progressTranslator = progressTranslator;
    }

    @Override
    public edu.stanford.protege.reasoning.action.ReasonerState decode(Messages.ReasonerState message) {
        Optional<Progress> progress;
        if(message.hasProgress()) {
            progress = Optional.<Progress>of(progressTranslator.decode(message.getProgress()));
        }
        else {
            progress = Optional.absent();
        }
        return new edu.stanford.protege.reasoning.action.ReasonerState(
                message.getReasonerName(),
                kbDigestTranslator.decode(message.getReasonerKbDigest()),
                message.getStateDescription(), progress
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
        builder.setReasonerKbDigest(kbDigestTranslator.encode(object.getReasonerKbDigest()));
        return builder
                .setReasonerName(object.getReasonerName())
                .setStateDescription(object.getStateDescription())
                .build();
    }
}
