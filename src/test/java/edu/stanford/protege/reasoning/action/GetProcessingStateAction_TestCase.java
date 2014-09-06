package edu.stanford.protege.reasoning.action;

import edu.stanford.protege.reasoning.KbId;
import org.hamcrest.core.IsNull;
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
 * @author Matthew Horridge, Stanford University, Bio-Medical Informatics Research Group, Date: 06/09/2014
 */
@RunWith(MockitoJUnitRunner.class)
public class GetProcessingStateAction_TestCase {

    @Mock
    private KbId kbId;

    private GetProcessingStateAction action;

    @Before
    public void setUp() {
        action = new GetProcessingStateAction(kbId);
    }

    @Test
    public void shouldBeEqual() {
        GetProcessingStateAction actionB = new GetProcessingStateAction(kbId);
        assertThat(action, is(equalTo(actionB)));
    }

    @Test
    public void shouldNotBeEqualToNull() {
        assertThat(action.equals(null), is(false));
    }

    @Test
    public void shouldHaveSameHashCode() {
        GetProcessingStateAction actionB = new GetProcessingStateAction(kbId);
        assertThat(action.hashCode(), is(equalTo(actionB.hashCode())));
    }

    @Test
    public void shouldReturnSameActionType() {
        ActionType<GetProcessingStateActionHandler> typeA = action.getType();
        ActionType<GetProcessingStateActionHandler> typeB = action.getType();
        assertThat(typeA, is(typeB));
    }

    @Test
    public void shouldReturnSameActionTypeForDifferentActions() {
        ActionType<GetProcessingStateActionHandler> typeA = action.getType();
        GetProcessingStateAction actionB = new GetProcessingStateAction(kbId);
        ActionType<GetProcessingStateActionHandler> typeB = actionB.getType();
        assertThat(typeA, is(typeB));
    }

    @Test
    public void shouldDispatchToHandler() {
        GetProcessingStateActionHandler handler = mock(GetProcessingStateActionHandler.class);
        action.dispatch(handler);
        verify(handler, times(1)).handleAction(action);
    }

    @Test(expected = NullPointerException.class)
    public void shouldThrowNullPointerException() {
        new GetProcessingStateAction(null);
    }

    @Test
    public void shouldNotBeEqual() {
        GetProcessingStateAction actionB = new GetProcessingStateAction(mock(KbId.class));
        assertThat(action, is(not(equalTo(actionB))));
    }

    @Test
    public void shouldReturnProvidedId() {
        assertThat(action.getKbId(), is(equalTo(kbId)));
    }

    @Test
    public void shouldNotThrowNullPointerInToString() {
        String s = action.toString();
        assertThat(s, is(IsNull.notNullValue()));
    }
}
