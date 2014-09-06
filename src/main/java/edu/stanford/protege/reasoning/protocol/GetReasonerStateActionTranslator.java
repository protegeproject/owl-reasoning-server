package edu.stanford.protege.reasoning.protocol;

import com.google.inject.Inject;
import edu.stanford.protege.reasoning.KbId;
import edu.stanford.protege.reasoning.action.GetReasonerStateAction;

import java.io.IOException;

/**
 * @author Matthew Horridge, Stanford University, Bio-Medical Informatics Research Group, Date: 06/09/2014
 */
public class GetReasonerStateActionTranslator implements Translator<GetReasonerStateAction, Messages.GetReasonerStateActionMessage> {

    private Translator<KbId, Messages.KbId> kbIdTranslator;

    @Inject
    public GetReasonerStateActionTranslator(Translator<KbId, Messages.KbId> kbIdTranslator) {
        this.kbIdTranslator = kbIdTranslator;
    }

    @Override
    public GetReasonerStateAction decode(Messages.GetReasonerStateActionMessage message) {
        return new GetReasonerStateAction(
            kbIdTranslator.decode(message.getKbId())
        );
    }

    @Override
    public GetReasonerStateAction decode(byte[] bytes) throws IOException {
        return decode(Messages.GetReasonerStateActionMessage.parseFrom(bytes));
    }

    @Override
    public Messages.GetReasonerStateActionMessage encode(GetReasonerStateAction object) {
        return Messages.GetReasonerStateActionMessage.newBuilder()
                .setKbId(kbIdTranslator.encode(object.getKbId()))
                .build();
    }
}
