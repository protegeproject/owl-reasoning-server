package edu.stanford.protege.reasoning.impl;

import com.google.common.base.Optional;
import com.google.common.collect.Maps;
import edu.stanford.protege.reasoning.action.ActionHandler;
import edu.stanford.protege.reasoning.action.ActionType;

import java.util.Map;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * @author Matthew Horridge, Stanford University, Bio-Medical Informatics Research Group, Date: 15/04/2014
 */
public class ActionHandlerMap {


    private final Map<ActionType, ActionHandler> handlerMap = Maps.newConcurrentMap();

    public <H extends ActionHandler> void put(ActionType<H> type, H hander) {
        handlerMap.put(checkNotNull(type), checkNotNull(hander));
    }

    @SuppressWarnings("unchecked")
    public <H extends ActionHandler> Optional<H> get(ActionType<H> type) {
        return Optional.fromNullable((H) handlerMap.get(type));
    }

}
