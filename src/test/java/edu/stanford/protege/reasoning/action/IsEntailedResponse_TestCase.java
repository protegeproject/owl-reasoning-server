package edu.stanford.protege.reasoning.action;

import com.google.common.base.Optional;
import edu.stanford.protege.reasoning.KbDigest;
import edu.stanford.protege.reasoning.KbId;
import edu.stanford.protege.reasoning.KbQueryResult;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
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
public class IsEntailedResponse_TestCase {


    @Mock
    private KbId kbId;

    @Mock
    private KbDigest kbDigest;

    @Mock
    private Optional<KbQueryResult<Entailed>> entailed;

    @Mock
    private OWLLogicalAxiom axiom;

    private IsEntailedResponse response;

    @Before
    public void setUp() {
        response = new IsEntailedResponse(kbId, kbDigest, axiom, entailed);
    }

    @Test
    public void shouldBeEqual() {
        IsEntailedResponse actionB = new IsEntailedResponse(kbId, kbDigest, axiom, entailed);
        assertThat(response, is(equalTo(actionB)));
    }

    @Test
    public void shouldNotBeEqualToNull() {
        assertThat(response.equals(null), is(false));
    }

    @Test
    public void shouldBeEqualToSelf() {
        assertThat(response.equals(response), is(true));
    }

    @Test
    public void shouldHaveSameHashCode() {
        IsEntailedResponse actionB = new IsEntailedResponse(kbId, kbDigest, axiom, entailed);
        assertThat(response.hashCode(), is(equalTo(actionB.hashCode())));
    }

    @Test(expected = NullPointerException.class)
    public void shouldThrowNullPointerExceptionForNullId() {
        new IsEntailedResponse(null, kbDigest, axiom, entailed);
    }

    @Test(expected = NullPointerException.class)
     public void shouldThrowNullPointerExceptionForNullDigest() {
        new IsEntailedResponse(kbId, null, axiom, entailed);
    }

    @Test(expected = NullPointerException.class)
    public void shouldThrowNullPointerExceptionForNullAxiom() {
        new IsEntailedResponse(kbId, kbDigest, null, entailed);
    }

    @Test(expected = NullPointerException.class)
    public void shouldThrowNullPointerExceptionForNullResult() {
        new IsEntailedResponse(kbId, kbDigest, axiom, null);
    }

    @Test
    public void shouldNotBeEqual() {
        IsEntailedResponse actionB = new IsEntailedResponse(mock(KbId.class), mock(KbDigest.class), axiom, entailed);
        assertThat(response, is(not(equalTo(actionB))));
    }

    @Test
    public void shouldReturnProvidedId() {
        assertThat(response.getKbId(), is(equalTo(kbId)));
    }

    @Test
    public void shouldReturnProvidedDigest() {
        assertThat(response.getKbDigest(), is(equalTo(kbDigest)));
    }

    @Test
    public void shouldReturnProvidedEntailed() {
        assertThat(response.getResult(), is(equalTo(entailed)));
    }

    @Test
    public void shouldReturnProvidedEntailment() {
        assertThat(response.getAxiom(), is(equalTo(axiom)));
    }
}
