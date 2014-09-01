package edu.stanford.protege.reasoning.impl;

import org.semanticweb.HermiT.Reasoner;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.reasoner.OWLReasonerFactory;

/**
 * @author Matthew Horridge, Stanford University, Bio-Medical Informatics Research Group, Date: 16/04/2014
 */
public class DefaultOWLReasonerFactorySelector implements OWLReasonerFactorySelector {

    @Override
    public OWLReasonerFactory getReasonerFactory(OWLOntology ontology) {
        return new Reasoner.ReasonerFactory();
    }
}
