package edu.stanford.protege.reasoning.impl;

import com.google.common.base.Optional;
import edu.stanford.protege.reasoning.KbDigest;
import edu.stanford.protege.reasoning.KbQueryResult;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.reasoner.Node;
import org.semanticweb.owlapi.reasoner.OWLReasoner;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.when;

/**
 * @author Matthew Horridge, Stanford University, Bio-Medical Informatics Research Group, Date: 02/09/2014
 */
@RunWith(MockitoJUnitRunner.class)
public class ReasonerImpl_GetEquivalentClasses_TestCase {

    @Mock
    private KbDigest kbDigest;

    @Mock
    private OWLReasoner delegate;


    @Mock
    private OWLClassExpression classExpression;

    @Mock
    private Node<OWLClass> classNode;

    private ReasonerImpl reasoner;


    @Before
    public void setUp() throws Exception {
        reasoner = new ReasonerImpl(kbDigest, delegate);
        // Return consistent by default
        when(delegate.isConsistent()).thenReturn(true);
    }


    @Test
    public void shouldDoDelegationFor_GetEquivalentClasses_WhenConsistent() {
        when(delegate.getEquivalentClasses(classExpression)).thenReturn(classNode);
        reasoner.getEquivalentClasses(classExpression);
        verify(delegate, times(1)).getEquivalentClasses(classExpression);
    }

    @Test
    public void shouldNotDoDelegationFor_GetEquivalentClasses_WhenInconsistent() {
        when(delegate.isConsistent()).thenReturn(false);
        reasoner.getEquivalentClasses(classExpression);
        verify(delegate, never()).getEquivalentClasses(any(OWLClassExpression.class));
    }

    @Test(expected = IllegalStateException.class)
    public void shouldThrowIllegalStateExceptionWhen_GetEquivalentClasses_OnGetForInconsistentKb() {
        when(delegate.isConsistent()).thenReturn(false);
        Optional<KbQueryResult<Node<OWLClass>>> result = reasoner.getEquivalentClasses(classExpression);
        result.get().getValue();
    }

    @Test
    public void shouldReturnDelegate_GetEquivalentClasses_Value() {
        when(delegate.getEquivalentClasses(classExpression)).thenReturn(classNode);
        Optional<KbQueryResult<Node<OWLClass>>> result = reasoner.getEquivalentClasses(classExpression);
        KbQueryResult<Node<OWLClass>> queryResult = result.get();
        assertThat(queryResult.getValue(), is(classNode));
    }
}
