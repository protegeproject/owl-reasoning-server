package edu.stanford.protege.reasoning.util;

import com.google.common.collect.ImmutableList;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.semanticweb.owlapi.change.AddAxiomData;
import org.semanticweb.owlapi.change.AxiomChangeData;
import org.semanticweb.owlapi.change.RemoveAxiomData;
import org.semanticweb.owlapi.model.*;
import sun.management.resources.agent_pt_BR;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * @author Matthew Horridge, Stanford University, Bio-Medical Informatics Research Group, Date: 30/09/2014
 */
@RunWith(MockitoJUnitRunner.class)
public class MinimizedLogicalAxiomChanges_Minimize_TestCase {

    private MinimizedLogicalAxiomChanges changes;

    private ImmutableList<OWLOntologyChange> ontologyChanges;

    @Mock
    private OWLOntology ontology;

    @Mock
    private OWLLogicalAxiom axA;

    @Mock
    private OWLLogicalAxiom axB;

    @Test
    public void shouldAddLogicalAxiom() {

    }

    @Test
    public void shouldNotAddNonLogicalAxiom() {
        when(axA.isLogicalAxiom()).thenReturn(false);
        AddAxiom addAxiom = new AddAxiom(ontology, axA);
        ontologyChanges = ImmutableList.<OWLOntologyChange>builder().add(addAxiom).build();
        changes = MinimizedLogicalAxiomChanges.fromOntologyChanges(ontologyChanges);
        assertThat(changes.isEmpty(), is(true));
    }

    @Test
    public void shouldMinimizeToEmptyAddRem() {
        when(axA.isLogicalAxiom()).thenReturn(true);
        AddAxiom addAxiom = new AddAxiom(ontology, axA);
        RemoveAxiom removeAxiom = new RemoveAxiom(ontology, axA);
        ontologyChanges = ImmutableList.<OWLOntologyChange>builder().add(addAxiom, removeAxiom).build();
        changes = MinimizedLogicalAxiomChanges.fromOntologyChanges(ontologyChanges);
        assertThat(changes.isEmpty(), is(true));
    }

    @Test
    public void shouldMinimizeToEmptyRemAdd() {
        when(axA.isLogicalAxiom()).thenReturn(true);
        RemoveAxiom removeAxiom = new RemoveAxiom(ontology, axA);
        AddAxiom addAxiom = new AddAxiom(ontology, axA);
        ontologyChanges = ImmutableList.<OWLOntologyChange>builder().add(removeAxiom, addAxiom).build();
        changes = MinimizedLogicalAxiomChanges.fromOntologyChanges(ontologyChanges);
        assertThat(changes.getLogicalAxiomChangeData().size(), is(0));
    }

    @Test
    public void shouldNotMinimizeToEmpty() {
        when(axA.isLogicalAxiom()).thenReturn(true);
        when(axB.isLogicalAxiom()).thenReturn(true);
        AddAxiom addAxiom = new AddAxiom(ontology, axA);
        RemoveAxiom removeAxiom = new RemoveAxiom(ontology, axB);
        ontologyChanges = ImmutableList.<OWLOntologyChange>builder().add(addAxiom, removeAxiom).build();
        changes = MinimizedLogicalAxiomChanges.fromOntologyChanges(ontologyChanges);
        assertThat(changes.getLogicalAxiomChangeData().size(), is(2));
    }

    @Test
    public void shouldMinimizeToSingleAdd() {
        when(axA.isLogicalAxiom()).thenReturn(true);
        AddAxiom addAxiom = new AddAxiom(ontology, axA);
        ontologyChanges = ImmutableList.<OWLOntologyChange>builder().add(addAxiom, addAxiom, addAxiom).build();
        changes = MinimizedLogicalAxiomChanges.fromOntologyChanges(ontologyChanges);
        assertThat(changes.getLogicalAxiomChangeData().size(), is(1));
    }

    @Test
    public void shouldMinimizeToSingleRemove() {
        when(axA.isLogicalAxiom()).thenReturn(true);
        RemoveAxiom removeAxiom = new RemoveAxiom(ontology, axA);
        ontologyChanges = ImmutableList.<OWLOntologyChange>builder().add(removeAxiom, removeAxiom, removeAxiom).build();
        changes = MinimizedLogicalAxiomChanges.fromOntologyChanges(ontologyChanges);
        assertThat(changes.getLogicalAxiomChangeData().size(), is(1));
    }

}
