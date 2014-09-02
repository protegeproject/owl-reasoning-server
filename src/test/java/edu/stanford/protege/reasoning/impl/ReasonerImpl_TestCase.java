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
import org.semanticweb.owlapi.reasoner.NodeSet;
import org.semanticweb.owlapi.reasoner.OWLReasoner;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;

/**
 * @author Matthew Horridge, Stanford University, Bio-Medical Informatics Research Group, Date: 02/09/2014
 */
@RunWith(MockitoJUnitRunner.class)
public class ReasonerImpl_TestCase {

    @Mock
    private KbDigest kbDigest;

    @Mock
    private OWLReasoner delegate;


    @Mock
    private OWLAxiom ax;

    @Mock
    private OWLClassExpression classExpression;

    @Mock
    private NodeSet<OWLClass> classNodeSet;

    private ReasonerImpl reasoner;


    @Before
    public void setUp() throws Exception {
        reasoner = new ReasonerImpl(kbDigest, delegate);
        // Return consistent by default
        when(delegate.isConsistent()).thenReturn(true);
    }

    @Test(expected = NullPointerException.class)
    public void shouldThrowNullPointerExceptionForNullDigest() {
        new ReasonerImpl(null, delegate);
    }

    @Test(expected = NullPointerException.class)
    public void shouldThrowNullPointerExceptionForNullDelegate() {
        new ReasonerImpl(kbDigest, null);
    }

    @Test
    public void shouldReturnSuppliedDigest() {
        assertThat(reasoner.getKbDigest(), is(equalTo(kbDigest)));
    }

    @Test
    public void shouldDelegateForGetConsistency() {
        reasoner.getConsistency();
        verify(delegate, times(1)).isConsistent();
    }

    @Test
    public void shouldReturnDelegateConsistent() {
        when(delegate.isConsistent()).thenReturn(true);
        Optional<Consistency> result = reasoner.getConsistency();
        assertThat(result.get(), is(Consistency.CONSISTENT));
    }

    @Test
    public void shouldReturnDelegateInconsistent() {
        when(delegate.isConsistent()).thenReturn(false);
        Optional<Consistency> result = reasoner.getConsistency();
        assertThat(result.get(), is(Consistency.INCONSISTENT));
    }



}
