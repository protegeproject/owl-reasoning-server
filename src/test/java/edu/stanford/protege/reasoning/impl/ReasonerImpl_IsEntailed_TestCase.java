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
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyBoolean;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.when;

/**
 * @author Matthew Horridge, Stanford University, Bio-Medical Informatics Research Group, Date: 02/09/2014
 */
@RunWith(MockitoJUnitRunner.class)
public class ReasonerImpl_IsEntailed_TestCase {


    @Mock
    private KbDigest kbDigest;

    @Mock
    private OWLReasoner delegate;

    @Mock
    private OWLAxiom ax;

    private ReasonerImpl reasoner;


    @Before
    public void setUp() throws Exception {
        reasoner = new ReasonerImpl(kbDigest, delegate);
        // Return consistent by default
        when(delegate.isConsistent()).thenReturn(true);
    }


    @Test
    public void shouldDoDelegationFor_IsEntailed_WhenConsistent() {
        reasoner.isEntailed(ax);
        verify(delegate, times(1)).isEntailed(ax);
    }

    @Test
    public void shouldNotDoDelegationFor_IsEntailed_WhenInconsistent() {
        when(delegate.isConsistent()).thenReturn(false);
        reasoner.isEntailed(ax);
        verify(delegate, never()).isEntailed(ax);
    }

    @Test(expected = IllegalStateException.class)
    public void shouldThrowIllegalStateExceptionWhen_IsEntailed_OnGetForInconsistentKb() {
        when(delegate.isConsistent()).thenReturn(false);
        Optional<KbQueryResult<Entailed>> result = reasoner.isEntailed(ax);
        result.get().getValue();
    }

    @Test
    public void shouldReturnDelegate_IsEntailed_True() {
        when(delegate.isEntailed(ax)).thenReturn(true);
        Optional<KbQueryResult<Entailed>> result = reasoner.isEntailed(ax);
        KbQueryResult<Entailed> queryResult = result.get();
        assertThat(queryResult.getValue(), is(Entailed.ENTAILED));
    }

    @Test
    public void shouldReturnDelegate_IsEntailed_False() {
        when(delegate.isEntailed(ax)).thenReturn(false);
        Optional<KbQueryResult<Entailed>> result = reasoner.isEntailed(ax);
        KbQueryResult<Entailed> queryResult = result.get();
        assertThat(queryResult.getValue(), is(Entailed.NOT_ENTAILED));
    }


}
