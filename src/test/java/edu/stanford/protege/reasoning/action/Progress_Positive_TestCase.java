package edu.stanford.protege.reasoning.action;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

/**
 * @author Matthew Horridge, Stanford University, Bio-Medical Informatics Research Group, Date: 06/09/2014
 */
public class Progress_Positive_TestCase {

    private Progress progress;

    public static final int INITIAL = 20;

    private static final int FINAL = 40;

    private static  final int VALUE = 30;

    @Before
    public void setUp() throws Exception {
        progress = Progress.from(INITIAL).to(FINAL).withValue(VALUE);
    }

    @Test
    public void shouldSetInitialValue() {
        assertThat(progress.getInitialValue(), is(INITIAL));
    }

    @Test
    public void shouldSetFinalValue() {
        assertThat(progress.getFinalValue(), is(FINAL));
    }

    @Test
    public void shouldSetValue() {
        assertThat(progress.getValue(), is(VALUE));
    }

    @Test
    public void shouldReturnPercentage() {
        assertThat(progress.getPercentageValue(), is(50));
    }

}
