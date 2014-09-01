package edu.stanford.protege.reasoning.protocol;

import edu.stanford.protege.reasoning.KbId;

import java.io.IOException;

/**
 * @author Matthew Horridge, Stanford University, Bio-Medical Informatics Research Group, Date: 20/08/2014
 */
public class KbIdTranslator implements Translator<KbId, Messages.KbId> {

    @Override
    public KbId decode(Messages.KbId message) {
        return new KbId(message.getId());
    }

    @Override
    public Messages.KbId encode(KbId object) {
        return Messages.KbId.newBuilder().setId(object.getLexicalForm()).build();
    }

    @Override
    public KbId decode(byte[] bytes) throws IOException {
        return decode(Messages.KbId.parseFrom(bytes));
    }
}
