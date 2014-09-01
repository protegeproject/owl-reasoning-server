package edu.stanford.protege.reasoning.protocol;

import edu.stanford.protege.reasoning.action.Consistency;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

/**
 * @author Matthew Horridge, Stanford University, Bio-Medical Informatics Research Group, Date: 29/08/2014
 */
public class ConsistencyTranslator_TestCase {

    @Test
    public void shouldRoundTripConsistent() {
        ConsistencyTranslator translator = new ConsistencyTranslator();
        Consistency decoded = translator.decode(translator.encode(Consistency.CONSISTENT));
        assertThat(decoded, is(Consistency.CONSISTENT));
    }


    @Test
    public void shouldRoundTripInconsistent() {
        ConsistencyTranslator translator = new ConsistencyTranslator();
        Consistency decoded = translator.decode(translator.encode(Consistency.INCONSISTENT));
        assertThat(decoded, is(Consistency.INCONSISTENT));
    }
}
