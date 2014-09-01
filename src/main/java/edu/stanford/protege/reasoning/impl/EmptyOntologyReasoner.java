package edu.stanford.protege.reasoning.impl;

import com.google.common.base.Optional;
import edu.stanford.protege.reasoning.action.Consistency;
import edu.stanford.protege.reasoning.action.Entailed;
import edu.stanford.protege.reasoning.KbDigest;
import edu.stanford.protege.reasoning.KbQueryResult;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.reasoner.*;

/**
 * @author Matthew Horridge, Stanford University, Bio-Medical Informatics Research Group, Date: 25/08/2014
 */
public class EmptyOntologyReasoner implements Reasoner {

    @Override
    public KbDigest getKbDigest() {
        return KbDigest.emptyDigest();
    }

    @Override
    public Optional<Consistency> getConsistency() throws ReasonerInterruptedException, TimeOutException {
        return Optional.of(Consistency.CONSISTENT);
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
