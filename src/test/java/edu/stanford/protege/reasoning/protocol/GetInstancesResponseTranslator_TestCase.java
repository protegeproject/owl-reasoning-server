package edu.stanford.protege.reasoning.protocol;

import com.google.common.base.Optional;
import edu.stanford.protege.reasoning.KbDigest;
import edu.stanford.protege.reasoning.KbId;
import edu.stanford.protege.reasoning.KbQueryResult;
import edu.stanford.protege.reasoning.action.GetInstancesResponse;
import org.junit.Test;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.PrefixManager;
import org.semanticweb.owlapi.reasoner.NodeSet;
import org.semanticweb.owlapi.util.DefaultPrefixManager;
import uk.ac.manchester.cs.owl.owlapi.OWLDataFactoryImpl;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.Class;

/**
 * @author Matthew Horridge, Stanford University, Bio-Medical Informatics Research Group, Date: 27/08/2014
 */
public class GetInstancesResponseTranslator_TestCase {

    @Test
    public void shouldTranslate() {
        KbId kbId = new KbId("x");
        PrefixManager pm = new DefaultPrefixManager();
        OWLClassExpression ce = Class("owl:Thing", pm);
        GetInstancesResponse action = new GetInstancesResponse(kbId,
                                                               KbDigest.emptyDigest(),
                                                               ce,
                                                               Optional.<KbQueryResult<NodeSet<OWLNamedIndividual>>>absent());
        GetInstancesResponseTranslator translator = new GetInstancesResponseTranslator(new KbIdTranslator(),
                                                                                       new KbDigestTranslator(),
                                                                                       new ClassExpressionTranslator(new BinaryOWLHelper(
                                                                                               new OWLDataFactoryImpl())),
                                                                                       new NamedIndividualTranslator(new OWLDataFactoryImpl()));
        Messages.GetInstancesResponseMessage msg = translator.encode(action);
        GetInstancesResponse decodedResponse = translator.decode(msg);
        assertThat(decodedResponse, is(equalTo(action)));

    }
}
