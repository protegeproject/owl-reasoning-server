package edu.stanford.protege.reasoning.protocol;

import edu.stanford.protege.reasoning.action.IsConsistentAction;
import edu.stanford.protege.reasoning.action.IsConsistentResponse;
import org.semanticweb.binaryowl.chunk.ChunkUtil;

import javax.inject.Inject;

/**
 * @author Matthew Horridge, Stanford University, Bio-Medical Informatics Research Group, Date: 28/08/2014
 */
public class IsConsistentCodec extends AbstractCodec<IsConsistentAction, IsConsistentResponse> {


    @Inject
    public IsConsistentCodec(IsConsistentActionTranslator actionTranslator, IsConsistentResponseTranslator responseTranslator) {
        super(ChunkUtil.toInt("cons"),
                IsConsistentAction.class, IsConsistentResponse.class,
                actionTranslator, responseTranslator);
    }

}
