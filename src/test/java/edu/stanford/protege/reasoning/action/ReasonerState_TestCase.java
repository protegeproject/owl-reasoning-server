package edu.stanford.protege.reasoning.action;

import org.hamcrest.core.IsNull;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

/**
 * @author Matthew Horridge, Stanford University, Bio-Medical Informatics Research Group, Date: 06/09/2014
 */
public class ReasonerState_TestCase {

    private String reasonerName = "TestName";

    private String currentTaskDescription = "CurrentTask";

    private int percentProcessed = 33;

    private ReasonerState state;

    @Before
    public void setUp() throws Exception {
        state = new ReasonerState(reasonerName, currentTaskDescription, percentProcessed);
    }

    @Test(expected = NullPointerException.class)
    public void shouldThrowNullPointerExceptionIf_ReasonerName_IsNull() {
        new ReasonerState(null, currentTaskDescription, percentProcessed);
    }

    @Test(expected = NullPointerException.class)
    public void shouldThrowNullPointerExceptionIf_CurrentTaskDescription_IsNull() {
        new ReasonerState(reasonerName, null, 0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowIllegalArgumentExceptionIf_PercentageProcessed_IsLessThanZero() {
        new ReasonerState(reasonerName, currentTaskDescription, -1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowIllegalArgumentExceptionIf_PercentageProcessed_IsGreaterThanOneHundred() {
        new ReasonerState(reasonerName, currentTaskDescription, 101);
    }

    @Test
    public void shouldReturnSuppliedReasonerName() {
        assertThat(state.getReasonerName(), is(reasonerName));
    }

    @Test
    public void shouldReturnSuppliedCurrentTaskDescription() {
        assertThat(state.getStateDescription(), is(currentTaskDescription));
    }

    @Test
    public void shouldReturnSuppliedPercentProcessed() {
        assertThat(state.getPercentageProcessed(), is(percentProcessed));
    }

    @Test
    public void shouldBeEqualToOther() {
        ReasonerState other = new ReasonerState(reasonerName, currentTaskDescription, percentProcessed);
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
