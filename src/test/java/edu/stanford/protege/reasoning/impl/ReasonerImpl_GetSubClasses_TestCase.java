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
import org.semanticweb.owlapi.reasoner.NodeSet;
import org.semanticweb.owlapi.reasoner.OWLReasoner;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyBoolean;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.when;

/**
 * @author Matthew Horridge, Stanford University, Bio-Medical Informatics Research Group, Date: 02/09/2014
 */
@RunWith(MockitoJUnitRunner.class)
public class ReasonerImpl_GetSubClasses_TestCase {


    @Mock
    private KbDigest kbDigest;

    @Mock
    private OWLReasoner delegate;


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


    @Test
    public void shouldDoDelegationFor_GetSubClasses_WhenConsistent() {
        when(delegate.getSubClasses(classExpression, true)).thenReturn(classNodeSet);
        reasoner.getSubClasses(classExpression, true);
        verify(delegate, times(1)).getSubClasses(classExpression, true);
    }

    @Test
    public void shouldNotDoDelegationFor_GetSubClasses_WhenInconsistent() {
        when(delegate.isConsistent()).thenReturn(false);
        reasoner.getSubClasses(classExpression, true);
        verify(delegate, never()).getSubClasses(any(OWLClassExpression.class), anyBoolean());
    }

    @Test(expected = IllegalStateException.class)
    public void shouldThrowIllegalStateExceptionWhen_GetSubClasses_OnGetForInconsistentKb() {
        when(delegate.isConsistent()).thenReturn(false);
        Optional<KbQueryResult<NodeSet<OWLClass>>> result = reasoner.getSubClasses(classExpression, true);
        result.get().getValue();
    }

    @Test
    public void shouldReturnDelegate_GetSubClasses_Value() {
        when(delegate.getSubClasses(classExpression, true)).thenReturn(classNodeSet);
        Optional<KbQueryResult<NodeSet<OWLClass>>> result = reasoner.getSubClasses(classExpression, true);
        KbQueryResult<NodeSet<OWLClass>> queryResult = result.get();
        assertThat(queryResult.getValue(), is(classNodeSet));
    }
}
