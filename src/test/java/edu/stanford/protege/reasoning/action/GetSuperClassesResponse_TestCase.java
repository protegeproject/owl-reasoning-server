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
public class GetSuperClassesResponse_TestCase {


    @Mock
    private KbId kbId;

    @Mock
    private KbDigest kbDigest;

    @Mock
    private Optional<KbQueryResult<NodeSet<OWLClass>>> result;

    @Mock
    private OWLClassExpression classExpression;

    private GetSuperClassesResponse response;

    @Before
    public void setUp() {
        response = new GetSuperClassesResponse(kbId, kbDigest, classExpression, result);
    }

    @Test
    public void shouldBeEqual() {
        GetSuperClassesResponse actionB = new GetSuperClassesResponse(kbId, kbDigest, classExpression, result);
        assertThat(response, is(equalTo(actionB)));
    }

    @Test
    public void shouldReturnFalseForEqualsNull() {
        assertThat(response.equals(null), is(false));
    }

    @Test
    public void shouldHaveSameHashCode() {
        GetSuperClassesResponse actionB = new GetSuperClassesResponse(kbId, kbDigest, classExpression, result);
        assertThat(response.hashCode(), is(equalTo(actionB.hashCode())));
    }

    @Test(expected = NullPointerException.class)
    public void shouldThrowNullPointerExceptionForNullId() {
        new GetSuperClassesResponse(null, kbDigest, classExpression, result);
    }

    @Test(expected = NullPointerException.class)
    public void shouldThrowNullPointerExceptionForNullDigest() {
        new GetSuperClassesResponse(kbId, null, classExpression, result);
    }

    @Test(expected = NullPointerException.class)
    public void shouldThrowNullPointerExceptionForNullClassExpression() {
        new GetSuperClassesResponse(kbId, kbDigest, null, result);
    }

    @Test(expected = NullPointerException.class)
    public void shouldThrowNullPointerExceptionForNullResult() {
        new GetSuperClassesResponse(kbId, kbDigest, classExpression, null);
    }

    @Test
    public void shouldNotBeEqual() {
        GetSuperClassesResponse actionB = new GetSuperClassesResponse(mock(KbId.class), mock(KbDigest.class), classExpression, result);
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
