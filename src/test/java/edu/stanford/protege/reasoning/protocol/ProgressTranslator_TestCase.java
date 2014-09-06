package edu.stanford.protege.reasoning.protocol;

import edu.stanford.protege.reasoning.action.Progress;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

/**
 * @author Matthew Horridge, Stanford University, Bio-Medical Informatics Research Group, Date: 06/09/2014
 */
public class ProgressTranslator_TestCase {

    private ProgressTranslator progressTranslator;

    @Before
    public void setUp() throws Exception {
        progressTranslator = new ProgressTranslator();
    }

    @Test
    public void shouldRoundTripWithIndeteminateProgress() {
        Progress progress = Progress.indeterminate();
        Progress roundTripped = progressTranslator.decode(progressTranslator.encode(progress));
        assertThat(roundTripped, is(progress));
    }
}
