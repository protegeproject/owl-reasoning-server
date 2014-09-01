package edu.stanford.protege.reasoning.protocol;

import edu.stanford.protege.reasoning.action.GetSuperClassesAction;
import edu.stanford.protege.reasoning.action.GetSuperClassesResponse;
import org.semanticweb.binaryowl.chunk.ChunkUtil;

import javax.inject.Inject;

/**
 * @author Matthew Horridge, Stanford University, Bio-Medical Informatics Research Group, Date: 29/08/2014
 */
public class GetSuperClassesCodec extends AbstractCodec<GetSuperClassesAction, GetSuperClassesResponse> {

    @Inject
    public GetSuperClassesCodec(GetSuperClassesActionTranslator actionTranslator, GetSuperClassesResponseTranslator responseTranslator) {
        super(ChunkUtil.toInt("supc"),
                GetSuperClassesAction.class, GetSuperClassesResponse.class, actionTranslator, responseTranslator);
    }
}
