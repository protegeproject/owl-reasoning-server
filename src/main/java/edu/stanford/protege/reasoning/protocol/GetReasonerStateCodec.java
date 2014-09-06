package edu.stanford.protege.reasoning.protocol;

import edu.stanford.protege.reasoning.action.GetReasonerStateAction;
import edu.stanford.protege.reasoning.action.GetReasonerStateResponse;
import org.semanticweb.binaryowl.chunk.ChunkUtil;

import javax.inject.Inject;
import java.io.IOException;

/**
 * @author Matthew Horridge, Stanford University, Bio-Medical Informatics Research Group, Date: 06/09/2014
 */
public class GetReasonerStateCodec extends TypeSafeReasoningServerCodec<GetReasonerStateAction, GetReasonerStateResponse> {

    private final GetReasonerStateActionTranslator actionTranslator;

    private final GetReasonerStateResponseTranslator responseTranslator;

    @Inject
    public GetReasonerStateCodec(GetReasonerStateActionTranslator actionTranslator,
                                   GetReasonerStateResponseTranslator responseTranslator) {
        super(GetReasonerStateAction.class, GetReasonerStateResponse.class);
        this.actionTranslator = actionTranslator;
        this.responseTranslator = responseTranslator;
    }

    @Override
    protected byte[] encodeActionSafely(GetReasonerStateAction action) {
        return actionTranslator.encode(action).toByteArray();
    }

    @Override
    protected GetReasonerStateAction decodeActionSafely(byte[] bytes) throws IOException {
        return actionTranslator.decode(bytes);
    }

    @Override
    public byte[] encodeResponseSafely(GetReasonerStateResponse response) {
        return responseTranslator.encode(response).toByteArray();
    }

    @Override
    protected GetReasonerStateResponse decodeResponseSafely(byte[] message) throws IOException {
        return responseTranslator.decode(message);
    }

    @Override
    public int getFrameMarker() {
        return ChunkUtil.toInt("prst");
    }
}
