package edu.stanford.protege.reasoning.protocol;

import com.google.protobuf.Message;
import edu.stanford.protege.reasoning.Response;
import edu.stanford.protege.reasoning.Action;

import java.io.IOException;

/**
 * @author Matthew Horridge, Stanford University, Bio-Medical Informatics Research Group, Date: 29/08/2014
 */
public class AbstractCodec<A extends Action, R extends Response> extends TypeSafeReasoningServerCodec<A, R> {

    private final Translator<A, ? extends Message> actionTranslator;

    private final Translator<R, ? extends Message> responseTranslator;

    private final int marker;

    public AbstractCodec(int marker, Class<A> actionClass, Class<R> responseClass, Translator<A, ? extends Message> actionTranslator, Translator<R, ? extends Message> responseTranslator) {
        super(actionClass, responseClass);
        this.marker = marker;
        this.actionTranslator = actionTranslator;
        this.responseTranslator = responseTranslator;
    }

    @Override
    protected byte[] encodeActionSafely(A action) {
        return actionTranslator.encode(action).toByteArray();
    }

    @Override
    protected A decodeActionSafely(byte[] bytes) throws IOException {
        return actionTranslator.decode(bytes);
    }

    @Override
    public byte[] encodeResponseSafely(R response) {
        return responseTranslator.encode(response).toByteArray();
    }

    @Override
    protected R decodeResponseSafely(byte[] message) throws IOException {
        return responseTranslator.decode(message);
    }

    @Override
    public int getFrameMarker() {
        return marker;
    }
}
