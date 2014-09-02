package edu.stanford.protege.reasoning.impl;

import com.google.common.base.Optional;
import com.google.common.base.Stopwatch;
import com.google.common.collect.Sets;
import edu.stanford.protege.reasoning.KbDigest;
import org.semanticweb.binaryowl.lookup.IRILookupTable;
import org.semanticweb.binaryowl.lookup.LookupTable;
import org.semanticweb.binaryowl.owlobject.OWLObjectBinaryType;
import org.semanticweb.binaryowl.stream.BinaryOWLOutputStream;
import org.semanticweb.owlapi.change.*;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import uk.ac.manchester.cs.owl.owlapi.EmptyInMemOWLOntologyFactory;
import uk.ac.manchester.cs.owl.owlapi.OWLDataFactoryImpl;
import uk.ac.manchester.cs.owl.owlapi.OWLOntologyManagerImpl;

import java.io.*;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * @author Matthew Horridge, Stanford University, Bio-Medical Informatics Research Group, Date: 15/04/2014
 */
public class KbAxiomSetManager {

    private Set<OWLAxiom> axioms = Sets.newHashSet();

    private final DigestManager digestManager = new DigestManager();

    private final ReadWriteLock lock = new ReentrantReadWriteLock();

    private final Lock readLock = lock.readLock();

    private final Lock writeLock = lock.writeLock();

    public KbAxiomSetManager() {
        createOntology();
    }

    public KbDigest getKbDigest() {
        return digestManager.getDigest();
    }


    public Optional<VersionedOntology> replaceAxioms(List<OWLAxiom> replacementAxioms) {
        try {
            checkNotNull(replacementAxioms);
            writeLock.lock();
            if (axioms.containsAll(replacementAxioms) && replacementAxioms.containsAll(axioms)) {
                return Optional.absent();
            }
            axioms.clear();
            axioms.addAll(replacementAxioms);
            digestManager.updateDigest(axioms);
            return Optional.of(createOntology());
        } finally {
            writeLock.unlock();
        }
    }


    public Optional<VersionedOntology> applyChanges(List<AxiomChangeData> changeDataList) {
        try {
            checkNotNull(changeDataList);
            writeLock.lock();
            final ChangeDataVisitor changeDataVisitor = new ChangeDataVisitor(axioms);
            for (AxiomChangeData changeData : changeDataList) {
                changeData.accept(changeDataVisitor);
            }
            if (changeDataVisitor.isChanged()) {
                digestManager.updateDigest(axioms);
                return Optional.of(createOntology());
            }
            else {
                return Optional.absent();
            }
        } finally {
            writeLock.unlock();
        }
    }

    private VersionedOntology createOntology() {
        try {
            writeLock.lock();
            OWLOntologyManagerImpl manager = new OWLOntologyManagerImpl(new OWLDataFactoryImpl());
            manager.addOntologyFactory(new EmptyInMemOWLOntologyFactory());
            return new VersionedOntology(manager.createOntology(axioms), digestManager.getDigest());
        } catch (OWLOntologyCreationException e) {
            throw new RuntimeException("Fatal error");
        } finally {
            writeLock.unlock();
        }
    }

    private static class ChangeDataVisitor implements OWLOntologyChangeDataVisitor<Void, RuntimeException> {


        public static final Void VOID = null;

        private Set<OWLAxiom> axioms;

        private boolean changed = false;

        public boolean isChanged() {
            return changed;
        }

        private ChangeDataVisitor(Set<OWLAxiom> axioms) {
            this.axioms = axioms;
        }

        @Override
        public Void visit(AddAxiomData addAxiomData) throws RuntimeException {
            changed = axioms.add(addAxiomData.getAxiom());
            return VOID;
        }

        @Override
        public Void visit(RemoveAxiomData removeAxiomData) throws RuntimeException {
            changed = axioms.remove(removeAxiomData.getAxiom());
            return VOID;
        }

        @Override
        public Void visit(AddOntologyAnnotationData addOntologyAnnotationData) throws RuntimeException {
            return VOID;
        }

        @Override
        public Void visit(RemoveOntologyAnnotationData removeOntologyAnnotationData) throws RuntimeException {
            return VOID;
        }

        @Override
        public Void visit(SetOntologyIDData setOntologyIDData) throws RuntimeException {
            return VOID;
        }

        @Override
        public Void visit(AddImportData addImportData) throws RuntimeException {
            return VOID;
        }

        @Override
        public Void visit(RemoveImportData removeImportData) throws RuntimeException {
            return VOID;
        }
    }


    private static class DigestManager {

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
                List<OWLAxiom> sortedAxioms = new ArrayList<>(axioms);
                Collections.sort(sortedAxioms);
                this.digest = KbDigest.getDigest(axioms);
            } finally {
                writeLock.unlock();
            }
        }
    }
}
