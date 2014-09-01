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
    public void shouldHaveSameHashCode() {
        GetSubClassesAction actionB = new GetSubClassesAction(kbId, classExpression);
        assertThat(action.hashCode(), is(equalTo(actionB.hashCode())));
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
