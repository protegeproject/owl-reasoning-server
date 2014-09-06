package edu.stanford.protege.reasoning.protocol;

import edu.stanford.protege.reasoning.action.GetProcessingStateAction;
import edu.stanford.protege.reasoning.action.GetProcessingStateResponse;
import edu.stanford.protege.reasoning.action.GetProcessingStateResponseTranslator;
import org.semanticweb.binaryowl.chunk.ChunkUtil;

import javax.inject.Inject;
import java.io.IOException;

/**
 * @author Matthew Horridge, Stanford University, Bio-Medical Informatics Research Group, Date: 06/09/2014
 */
public class GetProcessingStateCodec extends TypeSafeReasoningServerCodec<GetProcessingStateAction, GetProcessingStateResponse> {

    private final GetProcessingStateActionTranslator actionTranslator;

    private final GetProcessingStateResponseTranslator responseTranslator;

    @Inject
    public GetProcessingStateCodec(GetProcessingStateActionTranslator actionTranslator,
                                   GetProcessingStateResponseTranslator responseTranslator) {
        super(GetProcessingStateAction.class, GetProcessingStateResponse.class);
        this.actionTranslator = actionTranslator;
        this.responseTranslator = responseTranslator;
    }

    @Override
    protected byte[] encodeActionSafely(GetProcessingStateAction action) {
        return actionTranslator.encode(action).toByteArray();
    }

    @Override
    protected GetProcessingStateAction decodeActionSafely(byte[] bytes) throws IOException {
        return actionTranslator.decode(bytes);
    }

    @Override
    public byte[] encodeResponseSafely(GetProcessingStateResponse response) {
        return responseTranslator.encode(response).toByteArray();
    }

    @Override
    protected GetProcessingStateResponse decodeResponseSafely(byte[] message) throws IOException {
        return responseTranslator.decode(message);
    }

    @Override
    public int getFrameMarker() {
        return ChunkUtil.toInt("prst");
    }
}
