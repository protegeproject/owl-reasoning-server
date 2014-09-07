package edu.stanford.protege.reasoning.protocol;

import com.google.common.base.Optional;
import edu.stanford.protege.reasoning.KbDigest;
import edu.stanford.protege.reasoning.action.Progress;
import edu.stanford.protege.reasoning.action.ReasonerState;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

/**
 * @author Matthew Horridge, Stanford University, Bio-Medical Informatics Research Group, Date: 06/09/2014
 */
public class ReasonerStateTranslator_TestCase {

    private ReasonerStateTranslator translator;

    @Before
    public void setUp() throws Exception {
        translator = new ReasonerStateTranslator(
                new KbDigestTranslator(),
                new ProgressTranslator()
        );
    }

    @Test
    public void shouldRoundTripWithAbsentProgress() {
        ReasonerState reasonerState = new ReasonerState("TestReasonerName", KbDigest.emptyDigest(), "TestReasonerDescription", Optional.<Progress>absent());
        ReasonerState roundTripped = translator.decode(translator.encode(reasonerState));
        assertThat(roundTripped, is(reasonerState));
    }

    @Test
    public void shouldRoundTripWithPresentProgress() {
        Progress progress = Progress.from(50).to(100).withValue(75);
        ReasonerState reasonerState = new ReasonerState("TestReasonerName", KbDigest.emptyDigest(), "TestReasonerDescriptio", Optional.of(progress));
        ReasonerState roundTripped = translator.decode(translator.encode(reasonerState));
        assertThat(roundTripped, is(reasonerState));
    }
}
