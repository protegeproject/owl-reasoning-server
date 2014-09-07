package edu.stanford.protege.reasoning.action;

import com.google.common.base.Optional;
import edu.stanford.protege.reasoning.KbDigest;
import org.hamcrest.core.IsNull;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

/**
 * @author Matthew Horridge, Stanford University, Bio-Medical Informatics Research Group, Date: 06/09/2014
 */
@RunWith(MockitoJUnitRunner.class)
public class ReasonerState_TestCase {

    private String reasonerName = "TestName";

    private String stateDescription = "CurrentTask";

    @Mock
    private Optional<Progress> progress;

    @Mock
    private KbDigest kbDigest;


    private ReasonerState state;

    @Before
    public void setUp() throws Exception {
        state = new ReasonerState(reasonerName, kbDigest, stateDescription, progress);
    }

    @Test(expected = NullPointerException.class)
    public void shouldThrowNullPointerExceptionIf_ReasonerName_IsNull() {
        new ReasonerState(null, kbDigest, stateDescription, progress);
    }

    @Test(expected = NullPointerException.class)
    public void shouldThrowNullPointerExceptionIf_CurrentTaskDescription_IsNull() {
        new ReasonerState(reasonerName, kbDigest, null, progress);
    }


    @Test(expected = NullPointerException.class)
    public void shouldThrowNullPointerExceptionIf_Progress_IsNull() {
        new ReasonerState(reasonerName, kbDigest, stateDescription, null);
    }

    @Test(expected = NullPointerException.class)
    public void shouldThrowNullPointerExceptionIf_KbDigest_IsNull() {
        new ReasonerState(reasonerName, null, stateDescription, null);
    }


    @Test
    public void shouldReturnSuppliedReasonerName() {
        assertThat(state.getReasonerName(), is(reasonerName));
    }

    @Test
    public void shouldReturnSuppliedCurrentTaskDescription() {
        assertThat(state.getStateDescription(), is(stateDescription));
    }

    @Test
    public void shouldReturnSuppliedPercentProcessed() {
        assertThat(state.getProgress(), is(progress));
    }

    @Test
    public void shouldBeEqualToOther() {
        ReasonerState other = new ReasonerState(reasonerName, kbDigest, stateDescription, progress);
        assertThat(state.equals(other), is(true));
    }

    @Test
    public void shouldNotBeEqualToNull() {
        assertThat(state.equals(null), is(false));
    }

    @Test
    public void shouldBeEqualToSelf() {
        assertThat(state.equals(state), is(true));
    }

    @Test
    public void shouldNotThrowNullPointerInToString() {
        String s = state.toString();
        assertThat(s, is(IsNull.notNullValue()));
    }
}
