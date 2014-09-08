package edu.stanford.protege.reasoning.protocol;

import com.google.common.base.Optional;
import com.google.inject.Guice;
import com.google.inject.Injector;
import edu.stanford.protege.reasoning.KbDigest;
import edu.stanford.protege.reasoning.KbId;
import edu.stanford.protege.reasoning.KbQueryResult;
import edu.stanford.protege.reasoning.action.GetEquivalentClassesResponse;
import org.junit.Test;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.PrefixManager;
import org.semanticweb.owlapi.reasoner.Node;
import org.semanticweb.owlapi.util.DefaultPrefixManager;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.Class;

/**
 * @author Matthew Horridge, Stanford University, Bio-Medical Informatics Research Group, Date: 08/09/2014
 */
public class GetEquivalentClassesResponseTranslator_TestCase {


    @Test
    public void shouldRoundTrip() {
        Injector injector = Guice.createInjector(new TranslatorModule());
        GetEquivalentClassesResponseTranslator translator = injector.getInstance(GetEquivalentClassesResponseTranslator
                                                                                         .class);
        PrefixManager pm = new DefaultPrefixManager("http://other.com/ont/");
        GetEquivalentClassesResponse action = new GetEquivalentClassesResponse(new KbId("x"),
                                                                               KbDigest.emptyDigest(),
                                                                               Class("A", pm),
                                                                               Optional.<KbQueryResult<Node<OWLClass>>>absent());
        GetEquivalentClassesResponse decoded = translator.decode(translator.encode(action));
        assertThat(decoded, is(action));
    }
}
