package edu.stanford.protege.reasoning.action;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

/**
 * @author Matthew Horridge, Stanford University, Bio-Medical Informatics Research Group, Date: 06/09/2014
 */
public class Progress_Determinate_TestCase {

    private Progress progress;

    @Before
    public void setUp() throws Exception {
        progress = Progress.from(50).to(100).withValue(75);
    }

    @Test
    public void shouldReturnFalse() {
        assertThat(progress.isIndeterminate(), is(false));
    }

    @Test
    public void shouldBeEqualToOther() {
        Progress other = Progress.from(50).to(100).withValue(75);
        assertThat(progress.equals(other), is(true));
    }

    @Test
    public void shouldNotBeEqualToNull() {
        assertThat(progress.equals(null), is(false));
    }

    @Test
    public void shouldBeEqualToSelf() {
        assertThat(progress.equals(progress), is(true));
    }
}
