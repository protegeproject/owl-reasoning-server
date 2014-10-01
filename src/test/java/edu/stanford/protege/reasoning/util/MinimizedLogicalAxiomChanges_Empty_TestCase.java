package edu.stanford.protege.reasoning.util;

import com.google.common.collect.ImmutableList;
import org.junit.Before;
import org.junit.Test;
import org.semanticweb.owlapi.model.OWLOntologyChange;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

/**
 * @author Matthew Horridge, Stanford University, Bio-Medical Informatics Research Group, Date: 30/09/2014
 */
public class MinimizedLogicalAxiomChanges_Empty_TestCase {
    
    
    private MinimizedLogicalAxiomChanges changes;

    @Before
    public void setUp() throws Exception {
        changes = MinimizedLogicalAxiomChanges.empty();
    }

    @Test
    public void shouldBeEqualToOtherEmpty() {
        MinimizedLogicalAxiomChanges otherEmpty = MinimizedLogicalAxiomChanges.fromOntologyChanges
                (ImmutableList.<OWLOntologyChange>of());
        assertThat(changes.equals(otherEmpty), is(true));
    }

    @Test
    public void shouldBeEqualToEmpty() {
        assertThat(changes, is(MinimizedLogicalAxiomChanges.empty()));
    }

    @Test
    public void shouldNotBeEqualToNull() {
        assertThat(changes.equals(null), is(false));
    }

    @Test
    public void shouldBeEqualToSelf() {
        assertThat(changes.equals(changes), is(true));
    }

    @Test
    public void shouldHaveSameHashCode() {
        assertThat(changes.hashCode(), is(MinimizedLogicalAxiomChanges.empty().hashCode()));
    }


}
