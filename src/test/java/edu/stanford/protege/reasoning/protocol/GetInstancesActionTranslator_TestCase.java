package edu.stanford.protege.reasoning.protocol;

import edu.stanford.protege.reasoning.action.HierarchyQueryType;
import edu.stanford.protege.reasoning.KbId;
import edu.stanford.protege.reasoning.action.GetInstancesAction;
import org.junit.Test;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.PrefixManager;
import org.semanticweb.owlapi.util.DefaultPrefixManager;
import uk.ac.manchester.cs.owl.owlapi.OWLDataFactoryImpl;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.*;

/**
 * @author Matthew Horridge, Stanford University, Bio-Medical Informatics Research Group, Date: 27/08/2014
 */
public class GetInstancesActionTranslator_TestCase {

    @Test
    public void shouldTranslate() {
        KbId kbId = new KbId("x");
        PrefixManager pm = new DefaultPrefixManager();
        OWLClassExpression ce = Class("owl:Thing", pm);
        GetInstancesAction action = new GetInstancesAction(kbId, ce, HierarchyQueryType.DIRECT);
        GetInstancesActionTranslator translator = new GetInstancesActionTranslator(new KbIdTranslator(), new ClassExpressionTranslator(new BinaryOWLHelper(new OWLDataFactoryImpl())), new HierachyQueryTypeTranslator());
        Messages.GetInstancesActionMessage msg = translator.encode(action);
        GetInstancesAction decodedAction = translator.decode(msg);
        assertThat(decodedAction, is(equalTo(action)));
    }
}
