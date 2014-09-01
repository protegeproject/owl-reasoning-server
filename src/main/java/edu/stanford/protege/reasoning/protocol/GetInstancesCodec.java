package edu.stanford.protege.reasoning.protocol;

import edu.stanford.protege.reasoning.action.GetInstancesAction;
import edu.stanford.protege.reasoning.action.GetInstancesResponse;
import org.semanticweb.binaryowl.chunk.ChunkUtil;

import javax.inject.Inject;

/**
 * @author Matthew Horridge, Stanford University, Bio-Medical Informatics Research Group, Date: 29/08/2014
 */
public class GetInstancesCodec extends AbstractCodec<GetInstancesAction, GetInstancesResponse> {

    @Inject
    public GetInstancesCodec(GetInstancesActionTranslator actionTranslator, GetInstancesResponseTranslator responseTranslator) {
        super(ChunkUtil.toInt("inst"),
                GetInstancesAction.class, GetInstancesResponse.class,
                actionTranslator, responseTranslator);
    }
}
