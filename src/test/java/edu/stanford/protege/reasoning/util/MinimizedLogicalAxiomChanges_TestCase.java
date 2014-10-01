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

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * @author Matthew Horridge, Stanford University, Bio-Medical Informatics Research Group, Date: 30/09/2014
 */
@RunWith(MockitoJUnitRunner.class)
public class MinimizedLogicalAxiomChanges_TestCase {

    private MinimizedLogicalAxiomChanges changes;

    private ImmutableList<OWLOntologyChange> ontologyChanges;

    @Mock
    private OWLLogicalAxiom addedAxiom;

    @Mock
    private OWLLogicalAxiom removedAxiom;

    @Before
    public void setUp() throws Exception {
        when(addedAxiom.isLogicalAxiom()).thenReturn(true);
        when(removedAxiom.isLogicalAxiom()).thenReturn(true);
        OWLOntology mock = mock(OWLOntology.class);
        AddAxiom addAxiom = new AddAxiom(mock, addedAxiom);
        RemoveAxiom removeAxiom = new RemoveAxiom(mock, removedAxiom);
        ontologyChanges = ImmutableList.<OWLOntologyChange>builder().add(removeAxiom, addAxiom).build();
        changes = MinimizedLogicalAxiomChanges.fromOntologyChanges(ontologyChanges);
    }

    @Test(expected = NullPointerException.class)
    public void shouldThrowNullPointerException() {
        MinimizedLogicalAxiomChanges.fromOntologyChanges(null);
    }

    @Test
    public void shouldReturnChanges() {
        List<AxiomChangeData> changeData = changes.getLogicalAxiomChangeData();
        assertThat(changeData.get(0), is((AxiomChangeData) new RemoveAxiomData(removedAxiom)));
        assertThat(changeData.get(1), is((AxiomChangeData) new AddAxiomData(addedAxiom)));
    }

    @Test
    public void shouldBeEqualToOther() {
        MinimizedLogicalAxiomChanges other = MinimizedLogicalAxiomChanges.fromOntologyChanges(ontologyChanges);
        assertThat(changes.equals(other), is(true));
    }

    @Test
    public void shouldHaveSameHashCode() {
        MinimizedLogicalAxiomChanges other = MinimizedLogicalAxiomChanges.fromOntologyChanges(ontologyChanges);
        assertThat(changes.hashCode(), is(other.hashCode()));
    }

    @Test
    public void shouldBeEqualToSelf() {
        assertThat(changes.equals(changes), is(true));
    }

}
