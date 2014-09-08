package edu.stanford.protege.reasoning.impl;

import com.google.common.collect.Sets;
import edu.stanford.protege.reasoning.KbDigest;
import org.semanticweb.owlapi.model.OWLAxiom;

import java.util.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
* @author Matthew Horridge, Stanford University, Bio-Medical Informatics Research Group, Date: 08/09/2014
*/
class DigestManager {

    private final ReadWriteLock lock = new ReentrantReadWriteLock();

    private final Lock readLock = lock.readLock();

    private final Lock writeLock = lock.writeLock();

    private KbDigest digest = KbDigest.emptyDigest();

    public KbDigest getDigest() {
        try {
            readLock.lock();
            return digest;
        } finally {
            readLock.unlock();
        }
    }

    public void updateDigest(Collection<OWLAxiom> axioms) {
        try {
            writeLock.lock();
            TreeSet<OWLAxiom> sortedAxioms = Sets.newTreeSet(axioms);
            this.digest = KbDigest.getDigest(sortedAxioms);
        } finally {
            writeLock.unlock();
        }
    }
}
