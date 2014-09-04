package edu.stanford.protege.reasoning.impl;


import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.reasoner.OWLReasonerFactory;

import java.util.concurrent.ExecutorService;

/**
 * @author Matthew Horridge, Stanford University, Bio-Medical Informatics Research Group, Date: 16/04/2014
 */
public interface OWLReasonerFactorySelector {

    OWLReasonerFactory getReasonerFactory(OWLOntology ontology);

    ExecutorService getQueryExecutorService();
}
