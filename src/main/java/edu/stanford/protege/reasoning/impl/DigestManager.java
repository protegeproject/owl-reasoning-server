package edu.stanford.protege.reasoning.impl;

import com.google.common.collect.Sets;
import edu.stanford.protege.reasoning.KbDigest;
import org.semanticweb.owlapi.model.OWLAxiom;

import java.util.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import static com.google.common.base.Preconditions.checkNotNull;

/**
* @author Matthew Horridge, Stanford University, Bio-Medical Informatics Research Group, Date: 08/09/2014
 *
 * A thread safe set of axioms and digest.
*/
class DigestManager {

    private final ReadWriteLock lock = new ReentrantReadWriteLock();

    private final Lock readLock = lock.readLock();

    private final Lock writeLock = lock.writeLock();

    private KbDigest digest = KbDigest.emptyDigest();

    /**
     * Gets the digest of the axioms in this {@link DigestManager}.
     * @return The digest.  Not {@code null}.
     */
    public KbDigest getDigest() {
        try {
            readLock.lock();
            return digest;
        } finally {
            readLock.unlock();
        }
    }

    /**
     * Sets the axioms.
     * @param axioms The axioms.  Not {@code null}.
     * @throws NullPointerException if {@code axioms} is {@code null}.
     */
    public void updateDigest(Collection<OWLAxiom> axioms) {
        try {
            writeLock.lock();
            TreeSet<OWLAxiom> sortedAxioms;
            if (!(axioms instanceof TreeSet)) {
                sortedAxioms = Sets.newTreeSet(checkNotNull(axioms));
            }
            else {
                sortedAxioms = (TreeSet<OWLAxiom>) axioms;
            }
            this.digest = KbDigest.getDigest(sortedAxioms);
        } finally {
            writeLock.unlock();
        }
    }
}
