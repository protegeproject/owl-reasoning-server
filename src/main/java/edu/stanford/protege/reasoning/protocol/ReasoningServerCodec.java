package edu.stanford.protege.reasoning.protocol;

import edu.stanford.protege.reasoning.Response;
import edu.stanford.protege.reasoning.Action;

import java.io.IOException;

/**
 * @author Matthew Horridge, Stanford University, Bio-Medical Informatics Research Group, Date: 28/08/2014
 */
public interface ReasoningServerCodec {

    int getFrameMarker();

    Class<? extends Action> getActionClass();

    Class<? extends Response> getResponseClass();

    byte [] encodeAction(Action action);

    Action decodeAction(byte [] message) throws IOException;

    byte [] encodeResponse(Response response);

    Response decodeResponse(byte [] message) throws IOException;
}
