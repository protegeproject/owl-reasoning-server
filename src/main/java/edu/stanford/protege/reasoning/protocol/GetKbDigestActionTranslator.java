package edu.stanford.protege.reasoning.protocol;

import edu.stanford.protege.reasoning.action.GetKbDigestAction;

import javax.inject.Inject;
import java.io.IOException;

/**
 * @author Matthew Horridge, Stanford University, Bio-Medical Informatics Research Group, Date: 28/08/2014
 */
public class GetKbDigestActionTranslator implements Translator<GetKbDigestAction, Messages.GetKbDigestActionMessage> {

    private KbIdTranslator kbIdTranslator;

    @Inject
    public GetKbDigestActionTranslator(KbIdTranslator kbIdTranslator) {
        this.kbIdTranslator = kbIdTranslator;
    }

    @Override
    public GetKbDigestAction decode(Messages.GetKbDigestActionMessage message) {
        return new GetKbDigestAction(kbIdTranslator.decode(message.getKbId()));
    }

    @Override
    public Messages.GetKbDigestActionMessage encode(GetKbDigestAction object) {
        return Messages.GetKbDigestActionMessage.newBuilder()
                .setKbId(kbIdTranslator.encode(object.getKbId()))
                .build();
    }

    @Override
    public GetKbDigestAction decode(byte[] bytes) throws IOException {
        return decode(Messages.GetKbDigestActionMessage.parseFrom(bytes));
    }
}
