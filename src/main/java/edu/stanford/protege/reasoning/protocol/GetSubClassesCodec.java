package edu.stanford.protege.reasoning.protocol;

import edu.stanford.protege.reasoning.action.GetSubClassesAction;
import edu.stanford.protege.reasoning.action.GetSubClassesResponse;
import org.semanticweb.binaryowl.chunk.ChunkUtil;

import javax.inject.Inject;

/**
 * @author Matthew Horridge, Stanford University, Bio-Medical Informatics Research Group, Date: 29/08/2014
 */
public class GetSubClassesCodec extends AbstractCodec<GetSubClassesAction, GetSubClassesResponse> {

    @Inject
    public GetSubClassesCodec(GetSubClassesActionTranslator actionTranslator, GetSubClassesResponseTranslator responseTranslator) {
        super(ChunkUtil.toInt("subc"),
                GetSubClassesAction.class,
                GetSubClassesResponse.class,
                actionTranslator, responseTranslator);
    }
}
