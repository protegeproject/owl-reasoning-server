package edu.stanford.protege.reasoning.protocol;

import edu.stanford.protege.reasoning.KbId;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

/**
 * @author Matthew Horridge, Stanford University, Bio-Medical Informatics Research Group, Date: 29/08/2014
 */
@RunWith(MockitoJUnitRunner.class)
public class KbIdTranslator_TestCase {

    private KbId kbId;

    @Before
    public void setUp() {
        kbId = new KbId("xyz");
    }

    @Test
    public void shouldRoundTrip() {
        KbIdTranslator translator = new KbIdTranslator();
        KbId decoded = translator.decode(translator.encode(kbId));
        assertThat(kbId, is(equalTo(decoded)));
    }
}
