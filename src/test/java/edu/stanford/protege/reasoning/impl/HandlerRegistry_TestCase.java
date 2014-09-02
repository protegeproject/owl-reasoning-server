package edu.stanford.protege.reasoning.impl;

import edu.stanford.protege.reasoning.Action;
import edu.stanford.protege.reasoning.Response;
import edu.stanford.protege.reasoning.action.ActionHandler;
import edu.stanford.protege.reasoning.action.ActionType;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Mockito.*;

/**
 * @author Matthew Horridge, Stanford University, Bio-Medical Informatics Research Group, Date: 02/09/2014
 */
@RunWith(MockitoJUnitRunner.class)
public class HandlerRegistry_TestCase<A extends Action<R, H>, R extends Response, H extends ActionHandler> {

    @Mock
    private ActionHandlerMap handlerMap;

    @Mock
    private A action;

    @Mock
    private H actionHandler;

    @Mock
    private ActionType<H> type;


    private HandlerRegistry registry;

    @Before
    public void setUp() {
        when(handlerMap.get(type)).thenReturn(actionHandler);
        when(action.getType()).thenReturn(type);
        registry = new HandlerRegistry(handlerMap);
        registry.registerHandler(type, actionHandler);
    }

    @Test
    public void shouldHandleAction() {
        registry.handleAction(action);
        verify(action, times(1)).dispatch(actionHandler);
    }

    @Test(expected = RuntimeException.class)
    public void shouldThrowException() {
        registry.handleAction(mock(Action.class));
    }

    @Test(expected = NullPointerException.class)
    public void shouldThrowNullPointerExceptionIfActionTypeIsNull() {
        registry.registerHandler(null, actionHandler);
    }

    @Test(expected = NullPointerException.class)
    public void shouldThrowNullPointerExceptionIfHandlerIsNull() {
        registry.registerHandler(type, null);
    }
}
