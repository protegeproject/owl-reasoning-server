package edu.stanford.protege.reasoning.protocol;

import com.google.inject.Guice;
import com.google.inject.Injector;
import edu.stanford.protege.reasoning.KbId;
import edu.stanford.protege.reasoning.action.GetEquivalentClassesAction;
import org.junit.Test;
import org.semanticweb.owlapi.model.PrefixManager;
import org.semanticweb.owlapi.util.DefaultPrefixManager;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.*;

/**
 * @author Matthew Horridge, Stanford University, Bio-Medical Informatics Research Group, Date: 08/09/2014
 */
public class GetEquivalentClassesActionTranslator_TestCase {

    @Test
    public void shouldRoundTrip() {
        Injector injector = Guice.createInjector(new TranslatorModule());
        GetEquivalentClassesActionTranslator translator = injector.getInstance(GetEquivalentClassesActionTranslator
                                                                                       .class);
        PrefixManager pm = new DefaultPrefixManager("http://other.com/ont/");
        GetEquivalentClassesAction action = new GetEquivalentClassesAction(new KbId("x"), Class("A", pm));
        GetEquivalentClassesAction decoded = translator.decode(translator.encode(action));
        assertThat(decoded, is(action));
    }

}
