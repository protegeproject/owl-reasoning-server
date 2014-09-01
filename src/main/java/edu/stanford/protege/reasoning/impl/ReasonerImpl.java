package edu.stanford.protege.reasoning.impl;

import com.google.common.base.Optional;
import edu.stanford.protege.reasoning.*;
import edu.stanford.protege.reasoning.action.*;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.reasoner.*;

/**
 * @author Matthew Horridge, Stanford University, Bio-Medical Informatics Research Group, Date: 15/04/2014
 */
public class ReasonerImpl implements Reasoner {


    private KbDigest kbDigest;

    private OWLReasoner delegate;

    public ReasonerImpl(KbDigest kbDigest, OWLReasoner delegate) {
        this.kbDigest = kbDigest;
        this.delegate = delegate;
    }

    public KbDigest getKbDigest() {
        return kbDigest;
    }

    private boolean isInconsistent() {
        return !delegate.isConsistent();
    }

    @Override
    public Optional<Consistency> getConsistency() throws ReasonerInterruptedException, TimeOutException {
        Consistency consistency = delegate.isConsistent() ? Consistency.CONSISTENT : Consistency.INCONSISTENT;
        return Optional.of(consistency);
    }

    @Override
    public Optional<KbQueryResult<Entailed>> isEntailed(OWLAxiom axiom) throws ReasonerInterruptedException, UnsupportedEntailmentTypeException, TimeOutException, AxiomNotInProfileException, FreshEntitiesException, InconsistentOntologyException {
        if (isInconsistent()) {
            return KbQueryResult.optionalOfInconsistentKb();
        }
        else {
            Entailed entailed = delegate.isEntailed(axiom) ? Entailed.ENTAILED : Entailed.NOT_ENTAILED;
            return KbQueryResult.optionalOfValue(entailed);
        }
    }

    @Override
    public Optional<KbQueryResult<NodeSet<OWLClass>>> getSubClasses(OWLClassExpression ce, boolean direct) throws ReasonerInterruptedException, TimeOutException, FreshEntitiesException, InconsistentOntologyException, ClassExpressionNotInProfileException {
        if (isInconsistent()) {
            return KbQueryResult.optionalOfInconsistentKb();
        }
        else {
            return KbQueryResult.optionalOfValue(delegate.getSubClasses(ce, direct));
        }
    }

    @Override
    public Optional<KbQueryResult<NodeSet<OWLClass>>> getSuperClasses(OWLClassExpression ce, boolean direct) throws InconsistentOntologyException, ClassExpressionNotInProfileException, FreshEntitiesException, ReasonerInterruptedException, TimeOutException {
        if (isInconsistent()) {
            return KbQueryResult.optionalOfInconsistentKb();
        }
        else {
            return KbQueryResult.optionalOfValue(delegate.getSuperClasses(ce, direct));
        }
    }

    @Override
    public Optional<KbQueryResult<Node<OWLClass>>> getEquivalentClasses(OWLClassExpression ce) throws InconsistentOntologyException, ClassExpressionNotInProfileException, FreshEntitiesException, ReasonerInterruptedException, TimeOutException {
        if (isInconsistent()) {
            return KbQueryResult.optionalOfInconsistentKb();
        }
        else {
            return KbQueryResult.optionalOfValue(delegate.getEquivalentClasses(ce));
        }
    }

    @Override
    public Optional<KbQueryResult<NodeSet<OWLNamedIndividual>>> getInstances(OWLClassExpression ce, boolean direct) throws InconsistentOntologyException, ClassExpressionNotInProfileException, FreshEntitiesException, ReasonerInterruptedException, TimeOutException {
        if (isInconsistent()) {
            return KbQueryResult.optionalOfInconsistentKb();
        }
        else {
            return KbQueryResult.optionalOfValue(delegate.getInstances(ce, direct));
        }
    }
}
