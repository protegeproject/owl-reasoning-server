package edu.stanford.protege.reasoning.protocol;

import edu.stanford.protege.reasoning.action.GetKbDigestAction;
import edu.stanford.protege.reasoning.action.GetKbDigestResponse;
import org.semanticweb.binaryowl.chunk.ChunkUtil;

import javax.inject.Inject;

/**
 * @author Matthew Horridge, Stanford University, Bio-Medical Informatics Research Group, Date: 29/08/2014
 */
public class GetKbDigestCodec extends AbstractCodec<GetKbDigestAction, GetKbDigestResponse> {

    @Inject
    public GetKbDigestCodec(GetKbDigestActionTranslator actionTranslator, GetKbDigestResponseTranslator responseTranslator) {
        super(ChunkUtil.toInt("kbdg"),
                GetKbDigestAction.class, GetKbDigestResponse.class,
                actionTranslator, responseTranslator);
    }
}
