package edu.stanford.protege.reasoning.action;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

/**
 * @author Matthew Horridge, Stanford University, Bio-Medical Informatics Research Group, Date: 06/09/2014
 */
public class Progress_Indeterminate_TestCase {

    private Progress progress;

    @Before
    public void setUp() throws Exception {
        progress = Progress.indeterminate();
    }

    @Test
    public void shouldReturnTrue() {
        assertThat(progress.isIndeterminate(), is(true));
    }

    @Test(expected = IllegalStateException.class)
    public void shouldThrowIllegalStateExceptionOnGetInitialValue() {
        progress.getInitialValue();
    }

    @Test(expected = IllegalStateException.class)
    public void shouldThrowIllegalStateExceptionOnGetFinalValue() {
        progress.getInitialValue();
    }

    @Test(expected = IllegalStateException.class)
    public void shouldThrowIllegalStateExceptionOnGetValue() {
        progress.getInitialValue();
    }

    @Test(expected = IllegalStateException.class)
    public void shouldThrowIllegalStateExceptionOnGetPerscentageValue() {
        progress.getPercentageValue();
    }
}
