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
public class IsConsistentAction_TestCase {

    @Mock
    private KbId kbId;

    private IsConsistentAction action;

    @Before
    public void setUp() {
        action = new IsConsistentAction(kbId);
    }

    @Test
    public void shouldBeEqual() {
        IsConsistentAction actionB = new IsConsistentAction(kbId);
        assertThat(action, is(equalTo(actionB)));
    }

    @Test
    public void shouldHaveSameHashCode() {
        IsConsistentAction actionB = new IsConsistentAction(kbId);
        assertThat(action.hashCode(), is(equalTo(actionB.hashCode())));
    }

    @Test
    public void shouldReturnSameActionType() {
        ActionType<IsConsistentHandler> typeA = action.getType();
        ActionType<IsConsistentHandler> typeB = action.getType();
        assertThat(typeA, is(typeB));
    }

    @Test
    public void shouldReturnSameActionTypeForDifferentActions() {
        ActionType<IsConsistentHandler> typeA = action.getType();
        IsConsistentAction actionB = new IsConsistentAction(kbId);
        ActionType<IsConsistentHandler> typeB = actionB.getType();
        assertThat(typeA, is(typeB));
    }

    @Test
    public void shouldDispatchToHandler() {
        IsConsistentHandler handler = mock(IsConsistentHandler.class);
        action.dispatch(handler);
        verify(handler, times(1)).handleAction(action);
    }

    @Test(expected = NullPointerException.class)
    public void shouldThrowNullPointerException() {
        new IsConsistentAction(null);
    }

    @Test
    public void shouldNotBeEqual() {
        IsConsistentAction actionB = new IsConsistentAction(mock(KbId.class));
        assertThat(action, is(not(equalTo(actionB))));
    }

    @Test
    public void shouldReturnProvidedId() {
        assertThat(action.getKbId(), is(equalTo(kbId)));
    }
}
