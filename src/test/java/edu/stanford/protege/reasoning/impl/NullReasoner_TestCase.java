package edu.stanford.protege.reasoning.impl;

import com.google.common.base.Optional;
import edu.stanford.protege.reasoning.KbDigest;
import edu.stanford.protege.reasoning.KbQueryResult;
import edu.stanford.protege.reasoning.action.Consistency;
import edu.stanford.protege.reasoning.action.Entailed;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.reasoner.Node;
import org.semanticweb.owlapi.reasoner.NodeSet;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.mock;

/**
 * @author Matthew Horridge, Stanford University, Bio-Medical Informatics Research Group, Date: 02/09/2014
 */
@RunWith(MockitoJUnitRunner.class)
public class NullReasoner_TestCase {

    private NullReasoner reasoner;

    @Mock
    private KbDigest kbDigest;

    @Before
    public void setUp() throws Exception {
        reasoner = new NullReasoner(kbDigest);
    }

    @Test(expected = NullPointerException.class)
    public void shouldThrowNullPointerExceptionIfKbDigestIsNull() {
        new NullReasoner(null);
    }

    @Test
    public void shouldReturnSuppliedDigest() {
        assertThat(reasoner.getKbDigest(), is(equalTo(kbDigest)));
    }

    @Test
    public void shouldReturnOptionalAbsentForGetConsistent() {
        assertThat(reasoner.getConsistency(), is(Optional.<Consistency>absent()));
    }

    @Test
    public void shouldReturnOptionalAbsentForIsEntailed() {
        assertThat(reasoner.isEntailed(mock(OWLAxiom.class)), is(Optional.<KbQueryResult<Entailed>>absent()));
    }

    @Test
    public void shouldReturnOptionalAbsentForGetSubClasses() {
        assertThat(reasoner.getSubClasses(mock(OWLClassExpression.class), true),
                   is(Optional.<KbQueryResult<NodeSet<OWLClass>>>absent()));
    }

    @Test
    public void shouldReturnOptionalAbsentForGetEquivalentClasses() {
        assertThat(reasoner.getEquivalentClasses(mock(OWLClassExpression.class)),
                   is(Optional.<KbQueryResult<Node<OWLClass>>>absent()));
    }

    @Test
    public void shouldReturnOptionalAbsentForGetSuperClasses() {
        assertThat(reasoner.getSuperClasses(mock(OWLClassExpression.class), true),
                   is(Optional.<KbQueryResult<NodeSet<OWLClass>>>absent()));
    }

    @Test
    public void shouldReturnOptionalAbsentForGetInstances() {
        assertThat(reasoner.getInstances(mock(OWLClassExpression.class), true),
                   is(Optional.<KbQueryResult<NodeSet<OWLNamedIndividual>>>absent()));
    }
}
