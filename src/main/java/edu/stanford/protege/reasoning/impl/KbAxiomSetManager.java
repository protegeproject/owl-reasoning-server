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
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * @author Matthew Horridge, Stanford University, Bio-Medical Informatics Research Group, Date: 15/04/2014
 */
public class KbAxiomSetManager {

    private TreeSet<OWLAxiom> axioms = Sets.newTreeSet();

    private KbDigest kbDigest = KbDigest.emptyDigest();

    private VersionedOntology ontology;

    private final ReadWriteLock lock = new ReentrantReadWriteLock();

    private final Lock readLock = lock.readLock();

    private final Lock writeLock = lock.writeLock();

    public KbAxiomSetManager() {
        createOntology();
    }

    public Optional<VersionedOntology> replaceAxioms(List<OWLAxiom> replacementAxioms) {
        try {
            checkNotNull(replacementAxioms);
            writeLock.lock();
            if (replacementAxioms.equals(axioms)) {
                return Optional.absent();
            }
            axioms.clear();
            axioms.addAll(replacementAxioms);
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
                return Optional.of(createOntology());
            }
            else {
                return Optional.absent();
            }
        } finally {
            writeLock.unlock();
        }
    }

    public KbDigest getKbDigest() {
        return kbDigest;
    }

    public VersionedOntology getOntology() {
        try {
            readLock.lock();
            if (ontology == null) {
                throw new IllegalStateException("ontology is null");
            }
            return ontology;
        } finally {
            readLock.unlock();
        }
    }


    private VersionedOntology createOntology() {
        try {
            writeLock.lock();
            OWLOntologyManagerImpl manager = new OWLOntologyManagerImpl(new OWLDataFactoryImpl());
            manager.addOntologyFactory(new EmptyInMemOWLOntologyFactory());
            kbDigest = KbDigest.getDigest(axioms);
            return ontology = new VersionedOntology(manager.createOntology(axioms), kbDigest);
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
}
