package edu.stanford.protege.reasoning.protocol;

import edu.stanford.protege.reasoning.KbId;
import edu.stanford.protege.reasoning.action.IsConsistentAction;

import javax.inject.Inject;
import java.io.IOException;

/**
 * @author Matthew Horridge, Stanford University, Bio-Medical Informatics Research Group, Date: 20/08/2014
 */
public class IsConsistentActionTranslator implements Translator<IsConsistentAction, Messages.IsConsistentActionMessage> {

    private final Translator<KbId, Messages.KbId> kbIdTranslator;

    @Inject
    public IsConsistentActionTranslator(Translator<KbId, Messages.KbId> kbIdTranslator) {
        this.kbIdTranslator = kbIdTranslator;
    }

    @Override
    public IsConsistentAction decode(Messages.IsConsistentActionMessage message) {
        return new IsConsistentAction(
                kbIdTranslator.decode(message.getKbId())
        );
    }

    @Override
    public Messages.IsConsistentActionMessage encode(IsConsistentAction object) {
        Messages.IsConsistentActionMessage.Builder builder = Messages.IsConsistentActionMessage.newBuilder();
        builder.setKbId(kbIdTranslator.encode(object.getKbId()));
        return builder.build();
    }

    @Override
    public IsConsistentAction decode(byte[] bytes) throws IOException {
        return decode(Messages.IsConsistentActionMessage.parseFrom(bytes));
    }
}
