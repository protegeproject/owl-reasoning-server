package edu.stanford.protege.reasoning;

import org.hamcrest.core.IsNull;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

/**
 * @author Matthew Horridge, Stanford University, Bio-Medical Informatics Research Group, Date: 06/09/2014
 */
public class ReasoningStats_TestCase {

    private String reasonerName = "TestName";

    private long processingTime = 33;

    private ReasoningStats stats;

    @Before
    public void setUp() throws Exception {
        stats = new ReasoningStats(reasonerName, processingTime);
    }

    @Test(expected = NullPointerException.class)
    public void shouldThrowNullPointerExceptionIf_ReasonerName_IsNull() {
        new ReasoningStats(null, processingTime);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowIllegalArgumentExceptionIf_PercentageProcessed_IsLessThanZero() {
        new ReasoningStats(reasonerName, -1);
    }

    @Test
    public void shouldReturnSuppliedReasonerName() {
        assertThat(stats.getReasonerName(), is(reasonerName));
    }

    @Test
    public void shouldBeEqualToOther() {
        ReasoningStats other = new ReasoningStats(reasonerName, processingTime);
        assertThat(stats.equals(other), is(true));
    }

    @Test
    public void shouldNotBeEqualToNull() {
        assertThat(stats.equals(null), is(false));
    }

    @Test
    public void shouldBeEqualToSelf() {
        assertThat(stats.equals(stats), is(true));
    }

    @Test
    public void shouldNotThrowNullPointerInToString() {
        String s = stats.toString();
        assertThat(s, is(IsNull.notNullValue()));
    }
}
