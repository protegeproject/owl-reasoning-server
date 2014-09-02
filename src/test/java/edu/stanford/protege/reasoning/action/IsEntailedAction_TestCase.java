package edu.stanford.protege.reasoning.action;

import edu.stanford.protege.reasoning.KbId;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.semanticweb.owlapi.model.OWLAxiom;
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
public class IsEntailedAction_TestCase {


    @Mock
    private KbId kbId;

    @Mock
    private OWLLogicalAxiom axiom;

    private IsEntailedAction action;

    @Before
    public void setUp() {
        action = new IsEntailedAction(kbId, axiom);
    }

    @Test
    public void shouldBeEqual() {
        IsEntailedAction actionB = new IsEntailedAction(kbId, axiom);
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
        IsEntailedAction actionB = new IsEntailedAction(kbId, axiom);
        assertThat(action.hashCode(), is(equalTo(actionB.hashCode())));
    }

    @Test
    public void shouldReturnSameActionType() {
        ActionType<IsEntailedActionHandler> typeA = action.getType();
        ActionType<IsEntailedActionHandler> typeB = action.getType();
        assertThat(typeA, is(typeB));
    }

    @Test
    public void shouldReturnSameActionTypeForDifferentActions() {
        ActionType<IsEntailedActionHandler> typeA = action.getType();
        IsEntailedAction actionB = new IsEntailedAction(kbId, axiom);
        ActionType<IsEntailedActionHandler> typeB = actionB.getType();
        assertThat(typeA, is(typeB));
    }

    @Test
    public void shouldDispatchToHandler() {
        IsEntailedActionHandler handler = mock(IsEntailedActionHandler.class);
        action.dispatch(handler);
        verify(handler, times(1)).handleAction(action);
    }

    @Test(expected = NullPointerException.class)
    public void shouldThrowNullPointerExceptionIfIdIsNull() {
        new IsEntailedAction(null, axiom);
    }

    @Test(expected = NullPointerException.class)
    public void shouldThrowNullPointerExceptionIfAxiomIsNull() {
        new IsEntailedAction(kbId, null);
    }

    @Test
    public void shouldNotBeEqual() {
        IsEntailedAction actionB = new IsEntailedAction(mock(KbId.class), mock(OWLLogicalAxiom.class));
        assertThat(action, is(not(equalTo(actionB))));
    }

    @Test
    public void shouldReturnProvidedId() {
        assertThat(action.getKbId(), is(equalTo(kbId)));
    }

    @Test
    public void shouldReturnProvidedAxiom() {
        assertThat(action.getAxiom(), is(equalTo(axiom)));
    }
}
