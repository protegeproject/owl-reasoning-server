package edu.stanford.protege.reasoning.protocol;

import edu.stanford.protege.reasoning.action.GetEquivalentClassesAction;
import edu.stanford.protege.reasoning.action.GetEquivalentClassesResponse;
import org.semanticweb.binaryowl.chunk.ChunkUtil;

import javax.inject.Inject;
import java.io.IOException;

/**
 * @author Matthew Horridge, Stanford University, Bio-Medical Informatics Research Group, Date: 08/09/2014
 */
public class GetEquivalentClassesCodec extends TypeSafeReasoningServerCodec<GetEquivalentClassesAction, GetEquivalentClassesResponse> {


    private Translator<GetEquivalentClassesAction, Messages.GetEquivalentClassesActionMessage> actionTranslator;

    private Translator<GetEquivalentClassesResponse, Messages.GetEquivalentClassesResponseMessage> responseTranslator;

    @Inject
    public GetEquivalentClassesCodec(
            Translator<GetEquivalentClassesAction, Messages.GetEquivalentClassesActionMessage> actionTranslator,
            Translator<GetEquivalentClassesResponse, Messages.GetEquivalentClassesResponseMessage> responseTranslator) {
        super(GetEquivalentClassesAction.class, GetEquivalentClassesResponse.class);
        this.actionTranslator = actionTranslator;
        this.responseTranslator = responseTranslator;
    }

    @Override
    protected byte[] encodeActionSafely(GetEquivalentClassesAction action) {
        return actionTranslator.encode(action).toByteArray();
    }

    @Override
    protected GetEquivalentClassesAction decodeActionSafely(byte[] bytes) throws IOException {
        return actionTranslator.decode(bytes);
    }

    @Override
    public byte[] encodeResponseSafely(GetEquivalentClassesResponse response) {
        return responseTranslator.encode(response).toByteArray();
    }

    @Override
    protected GetEquivalentClassesResponse decodeResponseSafely(byte[] message) throws IOException {
        return responseTranslator.decode(message);
    }

    @Override
    public int getFrameMarker() {
        return ChunkUtil.toInt("eqcl");
    }
}
