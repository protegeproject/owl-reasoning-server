package edu.stanford.protege.reasoning.impl;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import edu.stanford.protege.reasoning.KbDigest;
import org.junit.Before;
import org.junit.Test;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.PrefixManager;
import org.semanticweb.owlapi.util.DefaultPrefixManager;

import java.util.List;
import java.util.Set;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.*;

/**
 * @author Matthew Horridge, Stanford University, Bio-Medical Informatics Research Group, Date: 08/09/2014
 */
public class DigestManager_TestCase {

    private DigestManager digestManager;

    @Before
    public void setUp() throws Exception {
        digestManager = new DigestManager();
    }

    @Test
    public void shouldReturnEmptyDigest() {
        KbDigest digest = digestManager.getDigest();
        assertThat(digest, is(KbDigest.emptyDigest()));
    }

    @Test
    public void shouldReturnDigestOfSortedAxioms() {
        PrefixManager pm = new DefaultPrefixManager("http://stuff.com/ont");
        List<OWLAxiom> axioms = Lists.<OWLAxiom>newArrayList(
                SubClassOf(Class("X", pm), Class("Y", pm)),
                SubClassOf(Class("A", pm), Class("B", pm)),
                SubClassOf(Class("P", pm), Class("Q", pm))
        );
        digestManager.updateDigest(axioms);
        Set<OWLAxiom> sortedAxioms = Sets.newTreeSet(axioms);
        KbDigest expectedDigest = KbDigest.getDigest(sortedAxioms);
        assertThat(digestManager.getDigest(), is(expectedDigest));
    }
}
