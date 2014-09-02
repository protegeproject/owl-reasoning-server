package edu.stanford.protege.reasoning.action;

import edu.stanford.protege.reasoning.KbId;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLLogicalAxiom;

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
public class GetSubClassesAction_TestCase {

    @Mock
    private KbId kbId;

    @Mock
    private OWLClassExpression classExpression;

    private GetSubClassesAction action;

    @Before
    public void setUp() {
        action = new GetSubClassesAction(kbId, classExpression);
    }

    @Test
    public void shouldBeEqual() {
        GetSubClassesAction actionB = new GetSubClassesAction(kbId, classExpression);
        assertThat(action, is(equalTo(actionB)));
    }

    @Test
    public void shouldBeEqualToSelf() {
        assertThat(action.equals(action), is(true));
    }

    @Test
    public void shouldNotBeEqualToNull() {
        assertThat(action.equals(null), is(false));
    }

    @Test
    public void shouldHaveSameHashCode() {
        GetSubClassesAction actionB = new GetSubClassesAction(kbId, classExpression);
        assertThat(action.hashCode(), is(equalTo(actionB.hashCode())));
    }

    @Test
    public void shouldReturnSameActionType() {
        ActionType<GetSubClassesActionHandler> typeA = action.getType();
        ActionType<GetSubClassesActionHandler> typeB = action.getType();
        assertThat(typeA, is(typeB));
    }

    @Test
    public void shouldReturnSameActionTypeForDifferentActions() {
        ActionType<GetSubClassesActionHandler> typeA = action.getType();
        GetSubClassesAction actionB = new GetSubClassesAction(kbId, classExpression);
        ActionType<GetSubClassesActionHandler> typeB = actionB.getType();
        assertThat(typeA, is(typeB));
    }

    @Test
    public void shouldDispatchToHandler() {
        GetSubClassesActionHandler handler = mock(GetSubClassesActionHandler.class);
        action.dispatch(handler);
        verify(handler, times(1)).handleAction(action);
    }

    @Test(expected = NullPointerException.class)
    public void shouldThrowNullPointerExceptionIfIdIsNull() {
        new GetSubClassesAction(null, classExpression);
    }

    @Test(expected = NullPointerException.class)
    public void shouldThrowNullPointerExceptionIfClassExpressionIsNull() {
        new GetSubClassesAction(kbId, null);
    }

    @Test
    public void shouldNotBeEqual() {
        GetSubClassesAction actionB = new GetSubClassesAction(mock(KbId.class), mock(OWLClassExpression.class));
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
}
