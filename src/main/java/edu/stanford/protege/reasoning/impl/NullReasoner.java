package edu.stanford.protege.reasoning.impl;

import com.google.common.base.Optional;
import edu.stanford.protege.reasoning.*;
import edu.stanford.protege.reasoning.action.Consistency;
import edu.stanford.protege.reasoning.KbQueryResult;
import edu.stanford.protege.reasoning.action.Entailed;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.reasoner.*;

/**
 * @author Matthew Horridge, Stanford University, Bio-Medical Informatics Research Group, Date: 15/04/2014
 *
 * A reasoner implementation that does not answer any queries.
 */
public class NullReasoner implements Reasoner {

    private KbDigest kbDigest;

    public NullReasoner(KbDigest kbDigest) {
        this.kbDigest = kbDigest;
    }

    @Override
    public KbDigest getKbDigest() {
        return kbDigest;
    }

    @Override
    public Optional<Consistency> getConsistency() throws ReasonerInterruptedException, TimeOutException {
        return Optional.absent();
    }

    @Override
    public Optional<KbQueryResult<Entailed>> isEntailed(OWLAxiom axiom) throws ReasonerInterruptedException, UnsupportedEntailmentTypeException, TimeOutException, AxiomNotInProfileException, FreshEntitiesException, InconsistentOntologyException {
        return Optional.absent();
    }

    @Override
    public Optional<KbQueryResult<NodeSet<OWLClass>>> getSubClasses(OWLClassExpression ce, boolean direct) throws ReasonerInterruptedException, TimeOutException, FreshEntitiesException, InconsistentOntologyException, ClassExpressionNotInProfileException {
        return Optional.absent();
    }

    @Override
    public Optional<KbQueryResult<NodeSet<OWLClass>>> getSuperClasses(OWLClassExpression ce, boolean direct) throws InconsistentOntologyException, ClassExpressionNotInProfileException, FreshEntitiesException, ReasonerInterruptedException, TimeOutException {
        return Optional.absent();
    }

    @Override
    public Optional<KbQueryResult<Node<OWLClass>>> getEquivalentClasses(OWLClassExpression ce) throws InconsistentOntologyException, ClassExpressionNotInProfileException, FreshEntitiesException, ReasonerInterruptedException, TimeOutException {
        return Optional.absent();
    }

    @Override
    public Optional<KbQueryResult<NodeSet<OWLNamedIndividual>>> getInstances(OWLClassExpression ce, boolean direct) throws InconsistentOntologyException, ClassExpressionNotInProfileException, FreshEntitiesException, ReasonerInterruptedException, TimeOutException {
        return Optional.absent();
    }
}
