package edu.stanford.protege.reasoning.protocol;

import java.io.IOException;

/**
 * @author Matthew Horridge, Stanford University, Bio-Medical Informatics Research Group, Date: 20/08/2014
 */
public interface Translator<O, M> {

    O decode(M message);

    O decode(byte [] bytes) throws IOException;

    M encode(O object);
}
