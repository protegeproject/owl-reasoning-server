package edu.stanford.protege.reasoning.protocol;

import edu.stanford.protege.reasoning.action.Entailed;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

/**
 * @author Matthew Horridge, Stanford University, Bio-Medical Informatics Research Group, Date: 29/08/2014
 */
public class EntailedTranslator_TestCase {

    @Test
    public void shouldRoundTripEntailed() {
        EntailedTranslator translator = new EntailedTranslator();
        Entailed decoded = translator.decode(translator.encode(Entailed.ENTAILED));
        assertThat(decoded, is(Entailed.ENTAILED));
    }


    @Test
    public void shouldRoundTripNotEntailed() {
        EntailedTranslator translator = new EntailedTranslator();
        Entailed decoded = translator.decode(translator.encode(Entailed.NOT_ENTAILED));
        assertThat(decoded, is(Entailed.NOT_ENTAILED));
    }
}
