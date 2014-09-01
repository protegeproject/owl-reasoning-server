package edu.stanford.protege.reasoning.protocol;

import edu.stanford.protege.reasoning.action.Consistency;

import java.io.IOException;

/**
 * @author Matthew Horridge, Stanford University, Bio-Medical Informatics Research Group, Date: 25/08/2014
 */
public class ConsistencyTranslator implements Translator<Consistency, Messages.Consistency> {
    @Override
    public Consistency decode(Messages.Consistency message) {
        return message == Messages.Consistency.CONSISTENT ? Consistency.CONSISTENT : Consistency.INCONSISTENT;
    }

    @Override
    public Messages.Consistency encode(Consistency object) {
        return object == Consistency.CONSISTENT ? Messages.Consistency.CONSISTENT : Messages.Consistency.INCONSISTENT;
    }

    @Override
    public Consistency decode(byte[] bytes) throws IOException {
        throw new RuntimeException();
    }
}
