package edu.stanford.protege.reasoning.reasonerimpl;

import org.semanticweb.HermiT.Configuration;
import org.semanticweb.HermiT.Reasoner;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.reasoner.IllegalConfigurationException;
import org.semanticweb.owlapi.reasoner.OWLReasoner;
import org.semanticweb.owlapi.reasoner.OWLReasonerConfiguration;
import org.semanticweb.owlapi.reasoner.OWLReasonerFactory;

/**
 * @author Matthew Horridge, Stanford University, Bio-Medical Informatics Research Group, Date: 02/09/2014
 */
public class HermiTReasonerFactory implements OWLReasonerFactory {

    private Reasoner.ReasonerFactory delegate;

    public HermiTReasonerFactory() {
        delegate = new Reasoner.ReasonerFactory();
    }

    @Override
    public String getReasonerName() {
        return "HermiT";
    }

    @Override
    public OWLReasoner createNonBufferingReasoner(OWLOntology ontology) {
        return delegate.createNonBufferingReasoner(ontology, getConfiguration());
    }

    private Configuration getConfiguration() {
        Configuration configuration = new Configuration();
        configuration.ignoreUnsupportedDatatypes = true;
        return configuration;
    }

    private Configuration getConfiguration(OWLReasonerConfiguration configuration) {
        Configuration con = getConfiguration();
        con.reasonerProgressMonitor = configuration.getProgressMonitor();
        con.freshEntityPolicy = configuration.getFreshEntityPolicy();
        con.individualNodeSetPolicy = configuration.getIndividualNodeSetPolicy();
        con.individualTaskTimeout = configuration.getTimeOut();
        return con;
    }

    @Override
    public OWLReasoner createReasoner(OWLOntology ontology) {
        return delegate.createReasoner(ontology);
    }

    @Override
    public OWLReasoner createNonBufferingReasoner(OWLOntology ontology, OWLReasonerConfiguration config) throws
                                                                                                         IllegalConfigurationException {
        return delegate.createNonBufferingReasoner(ontology, getConfiguration(config));
    }

    @Override
    public OWLReasoner createReasoner(OWLOntology ontology, OWLReasonerConfiguration config) throws
                                                                                             IllegalConfigurationException {
        return delegate.createNonBufferingReasoner(ontology, getConfiguration(config));
    }
}
