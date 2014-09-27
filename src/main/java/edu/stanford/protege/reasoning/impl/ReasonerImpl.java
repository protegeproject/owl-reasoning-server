package edu.stanford.protege.reasoning.impl;

import com.google.common.base.Optional;
import edu.stanford.protege.reasoning.*;
import edu.stanford.protege.reasoning.action.*;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.reasoner.*;

import java.util.concurrent.TimeoutException;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * @author Matthew Horridge, Stanford University, Bio-Medical Informatics Research Group, Date: 15/04/2014
 */
public class ReasonerImpl implements Reasoner {


    private KbDigest kbDigest;

    private OWLReasoner delegate;

    public ReasonerImpl(KbDigest kbDigest, OWLReasoner delegate) {
        this.kbDigest = checkNotNull(kbDigest);
        this.delegate = checkNotNull(delegate);
    }

    public KbDigest getKbDigest() {
        return kbDigest;
    }

    private boolean isInconsistent() {
        return !delegate.isConsistent();
    }

    private static RuntimeException translateException(RuntimeException e) {
        if(e instanceof TimeOutException) {
            return new ReasonerTimeOutException();
        }
        else {
            return new ReasonerInternalErrorException(e);
        }
    }

    @Override
    public Optional<Consistency> getConsistency() {
        try {
            Consistency consistency = delegate.isConsistent() ? Consistency.CONSISTENT : Consistency.INCONSISTENT;
            return Optional.of(consistency);
        } catch (RuntimeException e) {
            throw translateException(e);
        }
    }

    @Override
    public Optional<KbQueryResult<Entailed>> isEntailed(OWLAxiom axiom) {
        try {
            if (isInconsistent()) {
                return KbQueryResult.optionalOfInconsistentKb();
            }
            else {
                Entailed entailed = delegate.isEntailed(axiom) ? Entailed.ENTAILED : Entailed.NOT_ENTAILED;
                return KbQueryResult.optionalOfValue(entailed);
            }
        } catch (RuntimeException e) {
            throw translateException(e);
        }
    }

    @Override
    public Optional<KbQueryResult<NodeSet<OWLClass>>> getSubClasses(OWLClassExpression ce, boolean direct) {
        try {
            if (isInconsistent()) {
                return KbQueryResult.optionalOfInconsistentKb();
            }
            else {
                return KbQueryResult.optionalOfValue(delegate.getSubClasses(ce, direct));
            }
        } catch (RuntimeException e) {
            throw translateException(e);
        }
    }

    @Override
    public Optional<KbQueryResult<NodeSet<OWLClass>>> getSuperClasses(OWLClassExpression ce, boolean direct) {
        try {
            if (isInconsistent()) {
                return KbQueryResult.optionalOfInconsistentKb();
            }
            else {
                return KbQueryResult.optionalOfValue(delegate.getSuperClasses(ce, direct));
            }
        } catch (RuntimeException e) {
            throw translateException(e);
        }
    }

    @Override
    public Optional<KbQueryResult<Node<OWLClass>>> getEquivalentClasses(OWLClassExpression ce) {
        try {
            if (isInconsistent()) {
                return KbQueryResult.optionalOfInconsistentKb();
            }
            else {
                return KbQueryResult.optionalOfValue(delegate.getEquivalentClasses(ce));
            }
        } catch (RuntimeException e) {
            throw translateException(e);
        }
    }

    @Override
    public Optional<KbQueryResult<NodeSet<OWLNamedIndividual>>> getInstances(OWLClassExpression ce,
                                                                             boolean direct) {
        try {
            if (isInconsistent()) {
                return KbQueryResult.optionalOfInconsistentKb();
            }
            else {
                return KbQueryResult.optionalOfValue(delegate.getInstances(ce, direct));
            }
        } catch (RuntimeException e) {
            throw translateException(e);
        }
    }
}
