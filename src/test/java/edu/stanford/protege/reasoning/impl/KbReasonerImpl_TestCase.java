package edu.stanford.protege.reasoning.impl;

import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import edu.stanford.protege.reasoning.InternalReasonerException;
import edu.stanford.protege.reasoning.KbId;
import edu.stanford.protege.reasoning.Response;
import edu.stanford.protege.reasoning.action.ActionHandler;
import edu.stanford.protege.reasoning.action.KbAction;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.semanticweb.owlapi.reasoner.TimeOutException;


import java.util.concurrent.Executors;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * @author Matthew Horridge, Stanford University, Bio-Medical Informatics Research Group, Date: 02/09/2014
 */
@RunWith(MockitoJUnitRunner.class)
public class KbReasonerImpl_TestCase<A extends KbAction<R, H>, R extends Response, H extends ActionHandler<A, R>> {

    private KbReasonerImpl reasoner;

    @Mock
    private KbId kbId;

    @Mock
    private HandlerRegistry handlerRegistry;

    @Mock
    private KbAxiomSetManager axiomSetManager;

    @Mock
    private OWLReasonerFactorySelector reasonerFactorySelector;

    @Mock
    private A action;

    @Mock
    private ActionHandler<A, R> actionHandler;

    @Mock
    private FutureCallback<R> futureCallback;

    @Mock
    private Throwable runtimeException;


    @Before
    public void setUp() throws Exception {
        when(reasonerFactorySelector.getQueryExecutorService()).thenReturn(Executors.newSingleThreadScheduledExecutor());
        reasoner = new KbReasonerImpl(kbId, handlerRegistry, axiomSetManager, reasonerFactorySelector);
    }

    @Test
    public void shouldWrapArbitraryExceptionInInternalReasonerException() {
//        runtimeException = new RuntimeException();
//        when(handlerRegistry.handleAction(action)).thenThrow(runtimeException);
//
//        ListenableFuture<R> future = reasoner.execute(action);
//        Futures.addCallback(future, futureCallback);
//
//        ArgumentCaptor<Throwable> argumentCaptor = ArgumentCaptor.forClass(Throwable.class);
//        verify(futureCallback, times(1)).onFailure(argumentCaptor.capture());
//        Throwable throwable = argumentCaptor.getValue();
//        assertThat(throwable instanceof InternalReasonerException, is(true));
//        Throwable cause = throwable.getCause();
//        assertThat(cause, is(runtimeException));
    }

    @Test
    public void shouldFailWithTimeoutException() {
//        TimeOutException timeoutException = new TimeOutException();
//        when(handlerRegistry.handleAction(action)).thenThrow(timeoutException);
//        ListenableFuture<R> future = reasoner.execute(action);
//        Futures.addCallback(future, futureCallback);
//        verify(futureCallback, times(1)).onFailure(timeoutException);
    }
}
