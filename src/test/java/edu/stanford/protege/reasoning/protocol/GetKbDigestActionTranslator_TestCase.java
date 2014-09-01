package edu.stanford.protege.reasoning.protocol;

import edu.stanford.protege.reasoning.KbId;
import edu.stanford.protege.reasoning.action.GetKbDigestAction;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

/**
 * @author Matthew Horridge, Stanford University, Bio-Medical Informatics Research Group, Date: 29/08/2014
 */
public class GetKbDigestActionTranslator_TestCase {


    private GetKbDigestActionTranslator translator;
    private GetKbDigestAction action;

    @Before
    public void setUp() throws Exception {
        KbId kbId = new KbId("kbId");
        action = new GetKbDigestAction(kbId);
        translator = new GetKbDigestActionTranslator(new KbIdTranslator());
    }

    @Test
    public void shouldRoundTrip() {
        GetKbDigestAction decoded = translator.decode(translator.encode(action));
        assertThat(decoded, is(equalTo(action)));
    }
}
