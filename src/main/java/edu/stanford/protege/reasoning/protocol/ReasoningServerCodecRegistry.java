package edu.stanford.protege.reasoning.protocol;

import com.google.common.collect.Maps;
import edu.stanford.protege.reasoning.Response;
import edu.stanford.protege.reasoning.Action;

import java.util.Map;

/**
 * @author Matthew Horridge, Stanford University, Bio-Medical Informatics Research Group, Date: 29/08/2014
 */
public class ReasoningServerCodecRegistry {


    private Map<Class<? extends Action>, ReasoningServerCodec> actionCodecMap = Maps.newHashMap();

    private Map<Class<? extends Response>, ReasoningServerCodec> responseCodecMap = Maps.newHashMap();

    private Map<Integer, ReasoningServerCodec> marker2CodedMap = Maps.newHashMap();

    public void register(ReasoningServerCodec codec) {
        actionCodecMap.put(codec.getActionClass(), codec);
        responseCodecMap.put(codec.getResponseClass(), codec);
        marker2CodedMap.put(codec.getFrameMarker(), codec);
    }

    public ReasoningServerCodec getCodec(Action action) {
        ReasoningServerCodec codec = (ReasoningServerCodec) actionCodecMap.get(action.getClass());
        if(codec == null) {
            throw new RuntimeException("No codec registered for action");
        }
        return codec;
    }

    public ReasoningServerCodec getCodec(Response response) {
        ReasoningServerCodec codec = responseCodecMap.get(response.getClass());
        if(codec == null) {
            throw new RuntimeException("No codec registered for response");
        }
        return codec;
    }

    public ReasoningServerCodec getCodec(int marker) {
        ReasoningServerCodec codec = marker2CodedMap.get(marker);
        if(codec == null) {
            throw new RuntimeException("No codec registered for marker: " + marker);
        }
        return codec;
    }

}
