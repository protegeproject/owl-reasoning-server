package edu.stanford.protege.reasoning.protocol;

import edu.stanford.protege.reasoning.action.Entailed;

import java.io.IOException;

/**
 * @author Matthew Horridge, Stanford University, Bio-Medical Informatics Research Group, Date: 20/08/2014
 */
public class EntailedTranslator implements Translator<Entailed, Messages.EntailmentCheckResult> {

    @Override
    public Entailed decode(Messages.EntailmentCheckResult message) {
        return message == Messages.EntailmentCheckResult.ENTAILED ? Entailed.ENTAILED : Entailed.NOT_ENTAILED;
    }

    @Override
    public Messages.EntailmentCheckResult encode(Entailed object) {
        return object == Entailed.ENTAILED ? Messages.EntailmentCheckResult.ENTAILED : Messages.EntailmentCheckResult.NOT_ENTAILED;
    }

    @Override
    public Entailed decode(byte[] bytes) throws IOException {
        throw new RuntimeException();
    }
}
