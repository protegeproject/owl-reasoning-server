package edu.stanford.protege.reasoning.action;

import com.google.common.collect.ImmutableList;
import edu.stanford.protege.reasoning.KbId;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.semanticweb.owlapi.model.OWLAxiom;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.mockito.Mockito.mock;

/**
 * @author Matthew Horridge, Stanford University, Bio-Medical Informatics Research Group, Date: 31/08/2014
 */
@RunWith(MockitoJUnitRunner.class)
public class ReplaceAxiomsAction_TestCase {
   
    @Mock
    private KbId kbId;

    @Mock
    private ImmutableList<OWLAxiom> axioms;

    private ReplaceAxiomsAction action;

    @Before
    public void setUp() {
        action = new ReplaceAxiomsAction(kbId, axioms);
    }

    @Test
    public void shouldBeEqual() {
        ReplaceAxiomsAction actionB = new ReplaceAxiomsAction(kbId, axioms);
        assertThat(action, is(equalTo(actionB)));
    }

    @Test
    public void shouldHaveSameHashCode() {
        ReplaceAxiomsAction actionB = new ReplaceAxiomsAction(kbId, axioms);
        assertThat(action.hashCode(), is(equalTo(actionB.hashCode())));
    }

    @Test(expected = NullPointerException.class)
    public void shouldThrowNullPointerExceptionIfIdIsNull() {
        new ReplaceAxiomsAction(null, axioms);
    }

    @Test(expected = NullPointerException.class)
    public void shouldThrowNullPointerExceptionIfAxiomsIsNull() {
        new ReplaceAxiomsAction(kbId, null);
    }

    @SuppressWarnings("unchecked")
    @Test
    public void shouldNotBeEqual() {
        ReplaceAxiomsAction actionB = new ReplaceAxiomsAction(mock(KbId.class), mock(ImmutableList.class));
        assertThat(action, is(not(equalTo(actionB))));
    }

    @Test
    public void shouldReturnProvidedId() {
        assertThat(action.getKbId(), is(equalTo(kbId)));
    }

    @Test
    public void shouldReturnProvidedClassExpression() {
        assertThat(action.getAxioms(), is(equalTo(axioms)));
    }
}
