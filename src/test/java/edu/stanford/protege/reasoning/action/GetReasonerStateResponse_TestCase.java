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
import static org.hamcrest.core.IsNull.notNullValue;
import static org.mockito.Mockito.mock;

/**
 * @author Matthew Horridge, Stanford University, Bio-Medical Informatics Research Group, Date: 06/09/2014
 */
@RunWith(MockitoJUnitRunner.class)
public class GetReasonerStateResponse_TestCase {

    @Mock
    private KbId kbId;

    @Mock
    private ReasonerState reasonerState;

    private GetReasonerStateResponse response;

    @Before
    public void setUp() {
        response = new GetReasonerStateResponse(kbId, reasonerState);
    }

    @Test
    public void shouldBeEqual() {
        GetReasonerStateResponse actionB = new GetReasonerStateResponse(kbId, reasonerState);
        assertThat(response, is(equalTo(actionB)));
    }

    @Test
    public void shouldReturnFalseForEqualsNull() {
        assertThat(response.equals(null), is(false));
    }

    @Test
    public void shouldBeEqualToSelf() {
        assertThat(response.equals(response), is(true));
    }

    @Test
    public void shouldHaveSameHashCode() {
        GetReasonerStateResponse actionB = new GetReasonerStateResponse(kbId, reasonerState);
        assertThat(response.hashCode(), is(equalTo(actionB.hashCode())));
    }

    @Test(expected = NullPointerException.class)
    public void shouldThrowNullPointerExceptionForNullId() {
        new GetReasonerStateResponse(null, reasonerState);
    }

    @Test(expected = NullPointerException.class)
    public void shouldThrowNullPointerExceptionForNullDigest() {
        new GetReasonerStateResponse(kbId, null);
    }

    @Test
    public void shouldNotBeEqual() {
        GetReasonerStateResponse actionB = new GetReasonerStateResponse(mock(KbId.class), mock(ReasonerState.class));
        assertThat(response, is(not(equalTo(actionB))));
    }

    @Test
    public void shouldReturnProvidedId() {
        assertThat(response.getKbId(), is(equalTo(kbId)));
    }

    @Test
    public void shouldReturnProvidedState() {
        assertThat(response.getReasonerState(), is(equalTo(reasonerState)));
    }

    @Test
    public void shouldNotThrowNullPointerInToString() {
        String s = response.toString();
        assertThat(s, is(notNullValue()));
    }
}
