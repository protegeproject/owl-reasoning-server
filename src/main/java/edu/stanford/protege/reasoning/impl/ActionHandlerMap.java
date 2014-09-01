package edu.stanford.protege.reasoning.impl;

import com.google.common.collect.Maps;
import edu.stanford.protege.reasoning.action.ActionHandler;
import edu.stanford.protege.reasoning.action.ActionType;

import java.util.Map;

/**
 * @author Matthew Horridge, Stanford University, Bio-Medical Informatics Research Group, Date: 15/04/2014
 */
public class ActionHandlerMap {


    private final Map<ActionType, ActionHandler> handlerMap = Maps.newConcurrentMap();

    public <H extends ActionHandler> void put(ActionType<H> type, H hander) {
        handlerMap.put(type, hander);
    }

    @SuppressWarnings("unchecked")
    public <H extends ActionHandler> H get(ActionType<H> type) {
        return (H) handlerMap.get(type);
    }

}
