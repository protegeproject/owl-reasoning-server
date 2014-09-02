package edu.stanford.protege.reasoning.action;

import edu.stanford.protege.reasoning.KbId;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

/**
 * @author Matthew Horridge, Stanford University, Bio-Medical Informatics Research Group, Date: 29/08/2014
 */
@RunWith(MockitoJUnitRunner.class)
public class GetKbDigestAction_TestCase {

    @Mock
    private KbId kbId;
    private GetKbDigestAction action;

    @Before
    public void setUp() {
        action = new GetKbDigestAction(kbId);
    }

    @Test
    public void shouldBeEqual() {
        GetKbDigestAction actionB = new GetKbDigestAction(kbId);
        assertThat(action, is(equalTo(actionB)));
    }

    @Test
    public void shouldHaveSameHashCode() {
        GetKbDigestAction actionB = new GetKbDigestAction(kbId);
        assertThat(action.hashCode(), is(equalTo(actionB.hashCode())));
    }

    @Test
    public void shouldReturnSameActionType() {
        ActionType<GetKbDigestHandler> typeA = action.getType();
        ActionType<GetKbDigestHandler> typeB = action.getType();
        assertThat(typeA, is(typeB));
    }

    @Test
    public void shouldReturnSameActionTypeForDifferentActions() {
        ActionType<GetKbDigestHandler> typeA = action.getType();
        GetKbDigestAction actionB = new GetKbDigestAction(kbId);
        ActionType<GetKbDigestHandler> typeB = actionB.getType();
        assertThat(typeA, is(typeB));
    }

    @Test
    public void shouldDispatchToHandler() {
        GetKbDigestHandler handler = mock(GetKbDigestHandler.class);
        action.dispatch(handler);
        verify(handler, times(1)).handleAction(action);
    }

    @Test(expected = NullPointerException.class)
    public void shouldThrowNullPointerException() {
        new GetKbDigestAction(null);
    }

    @Test
    public void shouldNotBeEqual() {
        GetKbDigestAction actionB = new GetKbDigestAction(mock(KbId.class));
        assertThat(action, is(not(equalTo(actionB))));
    }

    @Test
    public void shouldReturnProvidedId() {
        assertThat(action.getKbId(), is(equalTo(kbId)));
    }

}
