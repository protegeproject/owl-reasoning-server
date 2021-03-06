package edu.stanford.protege.reasoning.impl;

import edu.stanford.protege.reasoning.reasonerimpl.HermiTReasonerFactory;
import org.semanticweb.HermiT.Reasoner;
import org.semanticweb.elk.owlapi.ElkReasonerFactory;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.reasoner.OWLReasonerFactory;
import uk.ac.manchester.cs.jfact.JFactFactory;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author Matthew Horridge, Stanford University, Bio-Medical Informatics Research Group, Date: 16/04/2014
 */
public class DefaultOWLReasonerFactorySelector implements OWLReasonerFactorySelector {

    @Override
    public OWLReasonerFactory getReasonerFactory(OWLOntology ontology) {
//        return new JFactFactory();
        return new HermiTReasonerFactory();
//        return new ElkReasonerFactory();
    }

    @Override
    public ExecutorService getQueryExecutorService() {
        // HermiT cannot deal with concurrent queries.
        return Executors.newSingleThreadExecutor();
    }
}
