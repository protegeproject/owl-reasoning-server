package edu.stanford.protege.reasoning.protocol;

import com.google.inject.Inject;
import edu.stanford.protege.reasoning.Response;
import edu.stanford.protege.reasoning.Action;

import java.io.IOException;

/**
 * @author Matthew Horridge, Stanford University, Bio-Medical Informatics Research Group, Date: 29/08/2014
 */
public abstract class TypeSafeReasoningServerCodec<A extends Action, R extends Response> implements ReasoningServerCodec {

    private final Class<A> actionClass;

    private final Class<R> responseClass;

    @Inject
    protected TypeSafeReasoningServerCodec(Class<A> actionClass, Class<R> responseClass) {
        this.actionClass = actionClass;
        this.responseClass = responseClass;
    }

    @Override
    public final Class<? extends Action> getActionClass() {
        return actionClass;
    }

    @Override
    public Class<? extends Response> getResponseClass() {
        return responseClass;
    }

    @SuppressWarnings("unchecked")
    @Override
    public final byte[] encodeAction(Action action) {
        return encodeActionSafely((A) action);
    }

    protected abstract byte [] encodeActionSafely(A action);

    @Override
    public final Action decodeAction(byte[] message) throws IOException {
        return decodeActionSafely(message);
    }

    protected abstract A decodeActionSafely(byte [] bytes) throws IOException;

    @SuppressWarnings("unchecked")
    @Override
    public final byte[] encodeResponse(Response response) {
        return encodeResponseSafely((R) response);
    }

    public abstract byte [] encodeResponseSafely(R response);

    @Override
    public final Response decodeResponse(byte[] message) throws IOException {
        return decodeResponseSafely(message);
    }

    protected abstract R decodeResponseSafely(byte [] message) throws IOException;
}
