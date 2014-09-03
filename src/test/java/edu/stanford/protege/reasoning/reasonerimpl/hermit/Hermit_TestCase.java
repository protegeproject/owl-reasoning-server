package edu.stanford.protege.reasoning.reasonerimpl.hermit;

import edu.stanford.protege.reasoning.reasonerimpl.HermiTReasonerFactory;
import org.junit.Before;
import org.junit.Test;
import org.semanticweb.HermiT.Configuration;
import org.semanticweb.HermiT.Reasoner;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.*;
import org.semanticweb.owlapi.reasoner.OWLReasoner;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.*;

/**
 * @author Matthew Horridge, Stanford University, Bio-Medical Informatics Research Group, Date: 02/09/2014
 */
public class Hermit_TestCase {

    private OWLReasoner reasoner;

    private OWLOntology ontology;

    private OWLOntologyManager manager;

    @Before
    public void setUp() throws Exception {
        ontology = OWLManager.createOWLOntologyManager().createOntology();
        HermiTReasonerFactory reasonerFactory = new HermiTReasonerFactory();
        reasoner = reasonerFactory.createReasoner(ontology);
        manager = ontology.getOWLOntologyManager();
    }

    @Test
    public void shouldHandleFreshEntitiesWithoutThrowingException() {
        boolean entailed = reasoner.isEntailed(SubClassOf(Class(IRI.create("A")), Class(IRI.create("B"))));
        assertThat(entailed, is(false));
    }

    @Test
    public void shouldHandleIsInconsistentWithoutThrowingException() {
        manager.addAxiom(ontology, SubClassOf(OWLThing(), OWLNothing()));
        reasoner.flush();
        boolean consistent = reasoner.isConsistent();
        assertThat(consistent, is(false));
    }

    /**
     * Ensures that HermiT throws the same exception for global restriction violations between releases..
     */
    @Test(expected = IllegalArgumentException.class)
    public void shouldHandleOutOfOWL2DL() {
        OWLObjectProperty property = ObjectProperty(IRI.create("p"));
        manager.addAxiom(ontology, FunctionalObjectProperty(property));
        manager.addAxiom(ontology, TransitiveObjectProperty(property));
        reasoner.flush();
        reasoner.isConsistent();
    }

    /**
     * Ensures that HermiT does not throw an unsupported datatype exception.  This requires the configuration to be
     * set up properly.
     */
    @Test
    public void shouldHandleUnsupportedDatatype() {
        OWLDatatype datatype = Datatype(IRI.create("dt"));
        OWLDataProperty property = DataProperty(IRI.create("p"));
        manager.addAxiom(ontology, DataPropertyRange(property, datatype));
        reasoner.flush();
    }
}
