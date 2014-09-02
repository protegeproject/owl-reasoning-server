package edu.stanford.protege.reasoning.impl;

import com.google.common.base.Optional;
import edu.stanford.protege.reasoning.Action;
import edu.stanford.protege.reasoning.Response;
import edu.stanford.protege.reasoning.action.ActionHandler;
import edu.stanford.protege.reasoning.action.ActionType;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.mock;

/**
 * @author Matthew Horridge, Stanford University, Bio-Medical Informatics Research Group, Date: 02/09/2014
 */
@RunWith(MockitoJUnitRunner.class)
public class ActionHandlerMap_TestCase<A extends Action<R, H>, R extends Response, H extends ActionHandler<A, R>> {


    private ActionHandlerMap actionHandlerMap;

    @Mock
    private ActionType<H> type;

    @Mock
    private H handler;

    @Before
    public void setUp() {
        actionHandlerMap = new ActionHandlerMap();
        actionHandlerMap.put(type, handler);
    }

    @Test
    public void shouldReturnRegisteredType() {
        Optional<H> handler = actionHandlerMap.get(type);
        assertThat(handler.isPresent(), is(true));
        assertThat(handler.get(), is(equalTo(this.handler)));
    }

    @Test
    public void shouldReturnAbsentForUnregisteredType() {
        assertThat(actionHandlerMap.get(mock(ActionType.class)).isPresent(), is(false));
    }

    @Test(expected = NullPointerException.class)
    public void shouldThrowNullPointerExceptionIfTypeIsNull() {
        actionHandlerMap.put(null, handler);
    }

    @Test(expected = NullPointerException.class)
    public void shouldThrowNullPointerExceptionIfHandlerIsNull() {
        actionHandlerMap.put(type, null);
    }
}
