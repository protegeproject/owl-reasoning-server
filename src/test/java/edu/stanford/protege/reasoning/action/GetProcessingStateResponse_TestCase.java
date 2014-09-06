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
public class GetProcessingStateResponse_TestCase {

    @Mock
    private KbId kbId;

    @Mock
    private ProcessingState processingState;

    private GetProcessingStateResponse response;

    @Before
    public void setUp() {
        response = new GetProcessingStateResponse(kbId, processingState);
    }

    @Test
    public void shouldBeEqual() {
        GetProcessingStateResponse actionB = new GetProcessingStateResponse(kbId, processingState);
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
        GetProcessingStateResponse actionB = new GetProcessingStateResponse(kbId, processingState);
        assertThat(response.hashCode(), is(equalTo(actionB.hashCode())));
    }

    @Test(expected = NullPointerException.class)
    public void shouldThrowNullPointerExceptionForNullId() {
        new GetProcessingStateResponse(null, processingState);
    }

    @Test(expected = NullPointerException.class)
    public void shouldThrowNullPointerExceptionForNullDigest() {
        new GetProcessingStateResponse(kbId, null);
    }

    @Test
    public void shouldNotBeEqual() {
        GetProcessingStateResponse actionB = new GetProcessingStateResponse(mock(KbId.class), mock(ProcessingState.class));
        assertThat(response, is(not(equalTo(actionB))));
    }

    @Test
    public void shouldReturnProvidedId() {
        assertThat(response.getKbId(), is(equalTo(kbId)));
    }

    @Test
    public void shouldReturnProvidedState() {
        assertThat(response.getProcessingState(), is(equalTo(processingState)));
    }

    @Test
    public void shouldNotThrowNullPointerInToString() {
        String s = response.toString();
        assertThat(s, is(notNullValue()));
    }
}
