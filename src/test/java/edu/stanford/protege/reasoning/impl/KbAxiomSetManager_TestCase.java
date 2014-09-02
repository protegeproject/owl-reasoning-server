package edu.stanford.protege.reasoning.impl;

import com.google.common.base.Optional;
import com.google.common.collect.Lists;
import edu.stanford.protege.reasoning.KbDigest;
import org.junit.Before;
import org.junit.Test;
import org.semanticweb.owlapi.change.AddAxiomData;
import org.semanticweb.owlapi.change.AxiomChangeData;
import org.semanticweb.owlapi.change.RemoveAxiomData;
import org.semanticweb.owlapi.model.OWLAnnotationAxiom;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.PrefixManager;
import org.semanticweb.owlapi.util.DefaultPrefixManager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.mock;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.Class;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.SubClassOf;

/**
 * @author Matthew Horridge, Stanford University, Bio-Medical Informatics Research Group, Date: 02/09/2014
 */
public class KbAxiomSetManager_TestCase {

    private KbAxiomSetManager manager;

    private OWLAxiom ax;

    @Before
    public void setUp() throws Exception {
        manager = new KbAxiomSetManager();
        PrefixManager pm = new DefaultPrefixManager();
        ax = SubClassOf(Class("A", pm), Class("B", pm));
    }

    @Test
    public void shouldReturnEmptyDigestByDefault() {
        KbDigest digest = manager.getKbDigest();
        assertThat(digest, is(KbDigest.emptyDigest()));
    }

    @Test
    public void shouldReturnAbsentIfNoChangesAreApplied() {
        Optional<VersionedOntology> ont = manager.applyChanges(Collections.<AxiomChangeData>emptyList());
        assertThat(ont.isPresent(), is(false));
    }

    @Test
    public void shouldReturnAbsentIfAxiomIsNotRemoved() {
        Optional<VersionedOntology> ont = manager.applyChanges(getRemoveAxiomList());
        assertThat(ont.isPresent(), is(false));
    }

    @Test
    public void shouldReturnAbsentIfNoAxiomsAreReplaced() {
        Optional<VersionedOntology> ont = manager.replaceAxioms(Collections.<OWLAxiom>emptyList());
        assertThat(ont.isPresent(), is(false));
    }

    @Test
    public void shouldReturnAbsentIfNoChangesAreExecuted() {
        manager.applyChanges(getAddAxiomList());
        // Add same axiom.  Should not be a change.
        Optional<VersionedOntology> ont = manager.applyChanges(getAddAxiomList());
        assertThat(ont.isPresent(), is(false));
    }

    @Test
    public void shouldReturnAbsentIfReplacementAxiomsAreEqual() {
        manager.replaceAxioms(Arrays.asList(ax));
        // Add same axiom.  Should not be a change.
        Optional<VersionedOntology> ont = manager.replaceAxioms(Arrays.asList(ax));
        assertThat(ont.isPresent(), is(false));
    }


    @Test
    public void shouldReturnOntologyContainingAxiom() {
        Optional<VersionedOntology> ont = manager.applyChanges(getAddAxiomList());
        assertThat(ont.get().getOntology().getAxioms(), is(Collections.singleton(ax)));
    }

    @Test
    public void shouldReturnDigestOfOntologyContainingAxiom() {
        Optional<VersionedOntology> ont = manager.applyChanges(getAddAxiomList());
        assertThat(ont.get().getKbDigest(), is(KbDigest.getDigest(Collections.singleton(ax))));
    }


    @Test
    public void shouldReturnCopyOfOntology() {
        Optional<VersionedOntology> ont = manager.applyChanges(getAddAxiomList());
        KbDigest digest = manager.getKbDigest();
        VersionedOntology versionedOntology = ont.get();
        OWLOntology ontology = versionedOntology.getOntology();
        ontology.getOWLOntologyManager().removeAxiom(ontology, ax);
        assertThat(manager.getKbDigest(), is(digest));
    }


    private ArrayList<AxiomChangeData> getAddAxiomList() {
        return Lists.<AxiomChangeData>newArrayList(new AddAxiomData(ax));
    }

    private ArrayList<AxiomChangeData> getRemoveAxiomList() {
        return Lists.<AxiomChangeData>newArrayList(new RemoveAxiomData(ax));
    }

}
