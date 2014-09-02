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
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.reasoner.NodeSet;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.mockito.Mockito.mock;

/**
 * @author Matthew Horridge, Stanford University, Bio-Medical Informatics Research Group, Date: 29/08/2014
 */
@RunWith(MockitoJUnitRunner.class)
public class GetSubClassesResponse_TestCase {

    @Mock
    private KbId kbId;

    @Mock
    private KbDigest kbDigest;

    @Mock
    private Optional<KbQueryResult<NodeSet<OWLClass>>> result;

    @Mock
    private OWLClassExpression classExpression;

    private GetSubClassesResponse response;

    @Before
    public void setUp() {
        response = new GetSubClassesResponse(kbId, kbDigest, classExpression, result);
    }

    @Test
    public void shouldBeEqual() {
        GetSubClassesResponse actionB = new GetSubClassesResponse(kbId, kbDigest, classExpression, result);
        assertThat(response, is(equalTo(actionB)));
    }

    @Test
    public void shouldHaveSameHashCode() {
        GetSubClassesResponse actionB = new GetSubClassesResponse(kbId, kbDigest, classExpression, result);
        assertThat(response.hashCode(), is(equalTo(actionB.hashCode())));
    }

    @Test
    public void shouldBeEqualToSelf() {
        assertThat(response.equals(response), is(true));
    }

    @Test
    public void shouldReturnFalseForEqualsNull() {
        assertThat(response.equals(null), is(false));
    }

    @Test(expected = NullPointerException.class)
    public void shouldThrowNullPointerExceptionForNullId() {
        new GetSubClassesResponse(null, kbDigest, classExpression, result);
    }

    @Test(expected = NullPointerException.class)
    public void shouldThrowNullPointerExceptionForNullDigest() {
        new GetSubClassesResponse(kbId, null, classExpression, result);
    }

    @Test(expected = NullPointerException.class)
    public void shouldThrowNullPointerExceptionForNullClassExpression() {
        new GetSubClassesResponse(kbId, kbDigest, null, result);
    }

    @Test(expected = NullPointerException.class)
    public void shouldThrowNullPointerExceptionForNullResult() {
        new GetSubClassesResponse(kbId, kbDigest, classExpression, null);
    }

    @Test
    public void shouldNotBeEqual() {
        GetSubClassesResponse actionB = new GetSubClassesResponse(mock(KbId.class), mock(KbDigest.class), classExpression, result);
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
    public void shouldReturnProvidedClassExpression() {
        assertThat(response.getClassExpression(), is(equalTo(classExpression)));
    }

    @Test
    public void shouldReturnProvidedResult() {
        assertThat(response.getResult(), is(equalTo(result)));
    }
}
