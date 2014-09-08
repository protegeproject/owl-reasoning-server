package edu.stanford.protege.reasoning.action;

import edu.stanford.protege.reasoning.KbId;
import org.hamcrest.core.IsNull;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.semanticweb.owlapi.model.OWLClassExpression;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

/**
 * @author Matthew Horridge, Stanford University, Bio-Medical Informatics Research Group, Date: 08/09/2014
 */
@RunWith(MockitoJUnitRunner.class)
public class GetEquivalentClassesAction_TestCase {

    @Mock
    private KbId kbId;

    @Mock
    private OWLClassExpression classExpression;

    private GetEquivalentClassesAction action;

    @Before
    public void setUp() {
        action = new GetEquivalentClassesAction(kbId, classExpression);
    }

    @Test
    public void shouldBeEqual() {
        GetEquivalentClassesAction actionB = new GetEquivalentClassesAction(kbId, classExpression);
        assertThat(action, is(equalTo(actionB)));
    }

    @Test
    public void shouldNotBeEqualToNull() {
        assertThat(action.equals(null), is(false));
    }

    @Test
    public void shouldHaveSameHashCode() {
        GetEquivalentClassesAction actionB = new GetEquivalentClassesAction(kbId, classExpression);
        assertThat(action.hashCode(), is(equalTo(actionB.hashCode())));
    }

    @Test
    public void shouldReturnSameActionType() {
        ActionType<GetEquivalentClassesActionHandler> typeA = action.getType();
        ActionType<GetEquivalentClassesActionHandler> typeB = action.getType();
        assertThat(typeA, is(typeB));
    }

    @Test
    public void shouldReturnSameActionTypeForDifferentActions() {
        ActionType<GetEquivalentClassesActionHandler> typeA = action.getType();
        GetEquivalentClassesAction actionB = new GetEquivalentClassesAction(kbId, classExpression);
        ActionType<GetEquivalentClassesActionHandler> typeB = actionB.getType();
        assertThat(typeA, is(typeB));
    }

    @Test
    public void shouldDispatchToHandler() {
        GetEquivalentClassesActionHandler handler = mock(GetEquivalentClassesActionHandler.class);
        action.dispatch(handler);
        verify(handler, times(1)).handleAction(action);
    }

    @Test(expected = NullPointerException.class)
    public void shouldThrowNullPointerExceptionIfIdIsNull() {
        new GetEquivalentClassesAction(null, classExpression);
    }

    @Test(expected = NullPointerException.class)
    public void shouldThrowNullPointerExceptionIfClassExpressionIsNull() {
        new GetEquivalentClassesAction(kbId, null);
    }

    @Test
    public void shouldNotBeEqual() {
        GetEquivalentClassesAction actionB = new GetEquivalentClassesAction(mock(KbId.class), mock(OWLClassExpression.class));
        assertThat(action, is(not(equalTo(actionB))));
    }

    @Test
    public void shouldReturnProvidedId() {
        assertThat(action.getKbId(), is(equalTo(kbId)));
    }

    @Test
    public void shouldReturnProvidedClassExpression() {
        assertThat(action.getClassExpression(), is(equalTo(classExpression)));
    }

    @Test
    public void shouldNotThrowNullPointerInToString() {
        String s = action.toString();
        assertThat(s, is(IsNull.notNullValue()));
    }
}
