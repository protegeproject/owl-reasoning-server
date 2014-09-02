package edu.stanford.protege.reasoning.impl;

import edu.stanford.protege.reasoning.KbDigest;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.semanticweb.owlapi.model.OWLOntology;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

/**
 * @author Matthew Horridge, Stanford University, Bio-Medical Informatics Research Group, Date: 02/09/2014
 */
@RunWith(MockitoJUnitRunner.class)
public class VersionedOntology_TestCase {

    @Mock
    private OWLOntology ontology;

    @Mock
    private KbDigest kbDigest;

    private VersionedOntology versionedOntology;

    @Before
    public void setUp() throws Exception {
        versionedOntology = new VersionedOntology(ontology, kbDigest);
    }

    @Test
    public void shouldBeEqualToOther() {
        VersionedOntology other = new VersionedOntology(ontology, kbDigest);
        assertThat(versionedOntology.equals(other), is(true));
    }

    @Test
    public void shouldNotBeEqualToNull() {
        assertThat(versionedOntology.equals(null), is(false));
    }

    @Test
    public void shouldBeEqualToSelf() {
        assertThat(versionedOntology.equals(versionedOntology), is(true));
    }

    @Test(expected = NullPointerException.class)
    public void shouldThrowNullPointerExceptionIfOntologyIsNull() {
        new VersionedOntology(null, kbDigest);
    }

    @Test(expected = NullPointerException.class)
    public void shouldThrowNullPointerExceptionIfDigestIsNull() {
        new VersionedOntology(ontology, null);
    }

    @Test
    public void getOntologyShouldReturnSuppliedOntology() {
        assertThat(versionedOntology.getOntology(), is(equalTo(ontology)));
    }

    @Test
    public void getKbDigestShouldReturnSuppliedDigest() {
        assertThat(versionedOntology.getKbDigest(), is(equalTo(kbDigest)));
    }
}
