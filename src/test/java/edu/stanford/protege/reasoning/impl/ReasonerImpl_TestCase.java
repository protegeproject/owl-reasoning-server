package edu.stanford.protege.reasoning.impl;

import com.google.common.base.Optional;
import edu.stanford.protege.reasoning.KbDigest;
import edu.stanford.protege.reasoning.ReasonerInternalErrorException;
import edu.stanford.protege.reasoning.ReasonerTimeOutException;
import edu.stanford.protege.reasoning.action.Consistency;
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
import org.semanticweb.owlapi.reasoner.TimeOutException;

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

    @Test(expected = ReasonerInternalErrorException.class)
    public void shouldTranslateRuntimeExceptionOn_IsConsistent() {
        when(delegate.isConsistent()).thenThrow(new RuntimeException());
        reasoner.getConsistency();
    }

    @Test(expected = ReasonerInternalErrorException.class)
    public void shouldTranslateRuntimeExceptionOn_IsEntailed() {
        when(delegate.isEntailed(ax)).thenThrow(new RuntimeException());
        reasoner.isEntailed(ax);
    }

    @Test(expected = ReasonerInternalErrorException.class)
    public void shouldTranslateRuntimeExceptionOn_GetSubClasses() {
        when(delegate.getSubClasses(classExpression, true)).thenThrow(new RuntimeException());
        reasoner.getSubClasses(classExpression, true);
    }

    @Test(expected = ReasonerInternalErrorException.class)
    public void shouldTranslateRuntimeExceptionOn_GetSuperClasses() {
        when(delegate.getSuperClasses(classExpression, true)).thenThrow(new RuntimeException());
        reasoner.getSuperClasses(classExpression, true);
    }

    @Test(expected = ReasonerInternalErrorException.class)
    public void shouldTranslateRuntimeExceptionOn_GetEquivalentClasses() {
        when(delegate.getEquivalentClasses(classExpression)).thenThrow(new RuntimeException());
        reasoner.getEquivalentClasses(classExpression);
    }

    @Test(expected = ReasonerInternalErrorException.class)
    public void shouldTranslateRuntimeExceptionOn_GetInstances() {
        when(delegate.getInstances(classExpression, true)).thenThrow(new RuntimeException());
        reasoner.getInstances(classExpression, true);
    }

    @Test(expected = ReasonerTimeOutException.class)
    public void shouldTranslateTimeOutExceptionOn_IsConsistent() {
        when(delegate.isConsistent()).thenThrow(new TimeOutException());
        reasoner.getConsistency();
    }

    @Test(expected = ReasonerTimeOutException.class)
    public void shouldTranslateTimeOutExceptionOn_IsEntailed() {
        when(delegate.isEntailed(ax)).thenThrow(new TimeOutException());
        reasoner.isEntailed(ax);
    }

    @Test(expected = ReasonerTimeOutException.class)
    public void shouldTranslateTimeOutExceptionOn_GetSubClasses() {
        when(delegate.getSubClasses(classExpression, true)).thenThrow(new TimeOutException());
        reasoner.getSubClasses(classExpression, true);
    }

    @Test(expected = ReasonerTimeOutException.class)
    public void shouldTranslateTimeOutExceptionOn_GetSuperClasses() {
        when(delegate.getSuperClasses(classExpression, true)).thenThrow(new TimeOutException());
        reasoner.getSuperClasses(classExpression, true);
    }

    @Test(expected = ReasonerTimeOutException.class)
    public void shouldTranslateTimeOutExceptionOn_GetEquivalentClasses() {
        when(delegate.getEquivalentClasses(classExpression)).thenThrow(new TimeOutException());
        reasoner.getEquivalentClasses(classExpression);
    }

    @Test(expected = ReasonerTimeOutException.class)
    public void shouldTranslateTimeOutExceptionOn_GetInstances() {
        when(delegate.getInstances(classExpression, true)).thenThrow(new TimeOutException());
        reasoner.getInstances(classExpression, true);
    }

}
