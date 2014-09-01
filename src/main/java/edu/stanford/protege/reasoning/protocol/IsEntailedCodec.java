package edu.stanford.protege.reasoning.protocol;

import edu.stanford.protege.reasoning.action.IsEntailedAction;
import edu.stanford.protege.reasoning.action.IsEntailedResponse;
import org.semanticweb.binaryowl.chunk.ChunkUtil;

import javax.inject.Inject;

/**
 * @author Matthew Horridge, Stanford University, Bio-Medical Informatics Research Group, Date: 29/08/2014
 */
public class IsEntailedCodec extends AbstractCodec<IsEntailedAction, IsEntailedResponse> {

    @Inject
    public IsEntailedCodec(IsEntailedActionTranslator actionTranslator, IsEntailedResponseTranslator responseTranslator) {
        super(
                ChunkUtil.toInt("entd"),
                IsEntailedAction.class, IsEntailedResponse.class,
                actionTranslator, responseTranslator);
    }
}
