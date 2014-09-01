package edu.stanford.protege.reasoning.action;

import com.google.common.collect.ImmutableList;
import edu.stanford.protege.reasoning.KbId;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.semanticweb.owlapi.change.AxiomChangeData;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

/**
 * @author Matthew Horridge, Stanford University, Bio-Medical Informatics Research Group, Date: 31/08/2014
 */
@RunWith(MockitoJUnitRunner.class)
public class ApplyChangesAction_TestCase {


    @Mock
    private KbId kbId;

    @Mock
    private ImmutableList<AxiomChangeData> changeData;

    private ApplyChangesAction action;

    @Before
    public void setUp() {
        action = new ApplyChangesAction(kbId, changeData);
    }

    @Test
    public void shouldBeEqual() {
        ApplyChangesAction actionB = new ApplyChangesAction(kbId, changeData);
        assertThat(action, is(equalTo(actionB)));
    }

    @Test
    public void shouldReturnFalseForEqualsNull() {
        assertThat(action.equals(null), is(false));
    }

    @Test
    public void shouldReturnSameActionType() {
        ActionType<ApplyChangesActionHandler> typeA = action.getType();
        ActionType<ApplyChangesActionHandler> typeB = action.getType();
        assertThat(typeA, is(typeB));
    }

    @Test
    public void shouldReturnSameActionTypeForDifferentActions() {
        ActionType<ApplyChangesActionHandler> typeA = action.getType();
        ApplyChangesAction actionB = new ApplyChangesAction(kbId, changeData);
        ActionType<ApplyChangesActionHandler> typeB = actionB.getType();
        assertThat(typeA, is(typeB));
    }

    @Test
    public void shouldDispatchToHandler() {
        ApplyChangesActionHandler handler = mock(ApplyChangesActionHandler.class);
        action.dispatch(handler);
        verify(handler, times(1)).handleAction(action);
    }

    @Test
    public void shouldHaveSameHashCode() {
        ApplyChangesAction actionB = new ApplyChangesAction(kbId, changeData);
        assertThat(action.hashCode(), is(equalTo(actionB.hashCode())));
    }

    @Test(expected = NullPointerException.class)
    public void shouldThrowNullPointerExceptionIfIdIsNull() {
        new ApplyChangesAction(null, changeData);
    }

    @Test(expected = NullPointerException.class)
    public void shouldThrowNullPointerExceptionIfChangeDataIsNull() {
        new ApplyChangesAction(kbId, null);
    }

    @Test
    public void shouldNotBeEqual() {
        ApplyChangesAction actionB = new ApplyChangesAction(mock(KbId.class), mock(ImmutableList.class));
        assertThat(action, is(not(equalTo(actionB))));
    }

    @Test
    public void shouldReturnProvidedId() {
        assertThat(action.getKbId(), is(equalTo(kbId)));
    }

    @Test
    public void shouldReturnProvidedClassExpression() {
        assertThat(action.getChangeData(), is(equalTo(changeData)));
    }
}
