package edu.stanford.protege.reasoning.protocol;

import com.google.inject.Inject;
import edu.stanford.protege.reasoning.KbId;
import edu.stanford.protege.reasoning.action.GetProcessingStateAction;

import java.io.IOException;

/**
 * @author Matthew Horridge, Stanford University, Bio-Medical Informatics Research Group, Date: 06/09/2014
 */
public class GetProcessingStateActionTranslator implements Translator<GetProcessingStateAction, Messages.GetProcessingStateActionMessage> {

    private Translator<KbId, Messages.KbId> kbIdTranslator;

    @Inject
    public GetProcessingStateActionTranslator(Translator<KbId, Messages.KbId> kbIdTranslator) {
        this.kbIdTranslator = kbIdTranslator;
    }

    @Override
    public GetProcessingStateAction decode(Messages.GetProcessingStateActionMessage message) {
        return new GetProcessingStateAction(
            kbIdTranslator.decode(message.getKbId())
        );
    }

    @Override
    public GetProcessingStateAction decode(byte[] bytes) throws IOException {
        return decode(Messages.GetProcessingStateActionMessage.parseFrom(bytes));
    }

    @Override
    public Messages.GetProcessingStateActionMessage encode(GetProcessingStateAction object) {
        return Messages.GetProcessingStateActionMessage.newBuilder()
                .setKbId(kbIdTranslator.encode(object.getKbId()))
                .build();
    }
}
