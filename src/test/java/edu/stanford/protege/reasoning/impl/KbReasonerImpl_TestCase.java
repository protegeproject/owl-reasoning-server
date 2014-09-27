package edu.stanford.protege.reasoning.impl;

import com.google.common.collect.ImmutableList;
import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.inject.Guice;
import edu.stanford.protege.reasoning.ReasonerInternalErrorException;
import edu.stanford.protege.reasoning.KbId;
import edu.stanford.protege.reasoning.ReasonerTimeOutException;
import edu.stanford.protege.reasoning.Response;
import edu.stanford.protege.reasoning.action.ActionHandler;
import edu.stanford.protege.reasoning.action.ApplyChangesAction;
import edu.stanford.protege.reasoning.action.ApplyChangesResponse;
import edu.stanford.protege.reasoning.action.KbAction;
import edu.stanford.protege.reasoning.inject.ReasoningServerModule;
import edu.stanford.protege.reasoning.inject.ReasoningServiceModule;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.semanticweb.owlapi.change.AddAxiomData;
import org.semanticweb.owlapi.change.AxiomChangeData;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.reasoner.OWLReasonerConfiguration;
import org.semanticweb.owlapi.reasoner.OWLReasonerFactory;
import org.semanticweb.owlapi.reasoner.TimeOutException;
import org.semanticweb.owlapi.util.DefaultPrefixManager;


import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;

import static junit.framework.TestCase.fail;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.*;

/**
 * @author Matthew Horridge, Stanford University, Bio-Medical Informatics Research Group, Date: 02/09/2014
 */
@RunWith(MockitoJUnitRunner.class)
public class KbReasonerImpl_TestCase<A extends KbAction<R, H>, R extends Response, H extends ActionHandler<A, R>> {

    private KbReasonerImpl reasoner;

    @Mock
    private KbId kbId;

    @Mock
    private OWLReasonerFactorySelector reasonerFactorySelector;

    @Mock
    private A action;

    @Mock
    private ActionHandler<A, R> actionHandler;

    @Mock
    private FutureCallback<ApplyChangesResponse> futureCallback;

    @Mock
    private OWLReasonerFactory reasonerFactory;

    private ApplyChangesAction applyChangesAction;

    @Before
    public void setUp() throws Exception {
        DefaultPrefixManager pm = new DefaultPrefixManager();
        pm.setDefaultPrefix("http://other/ont/");
        ImmutableList<AxiomChangeData> list = ImmutableList.<AxiomChangeData>builder().add(
                new AddAxiomData(SubClassOf(Class("A", pm), Class("B", pm)))
        ).build();

        applyChangesAction = new ApplyChangesAction(kbId, list);


        KbAxiomSetManager axiomSetManager = new KbAxiomSetManager();
        when(reasonerFactorySelector.getQueryExecutorService()).thenReturn(Executors.newSingleThreadScheduledExecutor());
        when(reasonerFactorySelector.getReasonerFactory(any(OWLOntology.class))).thenReturn(reasonerFactory);
        when(reasonerFactory.getReasonerName()).thenReturn("MockReasoner");

        reasoner = new KbReasonerImpl(kbId, new HandlerRegistry(new ActionHandlerMap()),
                                      axiomSetManager, reasonerFactorySelector, 33);

    }

    /**
     * Verifies that the correct time out exception is thrown when an OWL API TimeOutException is thrown internally.
     */
    @Test
    @SuppressWarnings("ThrowableResultOfMethodCallIgnored")
    public void shouldThrowReasonerTimeOutException() throws InterruptedException {
        when(reasonerFactory.createNonBufferingReasoner(
                any(OWLOntology.class), any(OWLReasonerConfiguration.class)))
                .thenThrow(new TimeOutException());

        ListenableFuture<ApplyChangesResponse> future = reasoner.execute(applyChangesAction);
        try {
            future.get();
            fail("Expected ExecutionException, but it was not thrown");
        } catch (ExecutionException e) {
            assertThat(e.getCause() instanceof ReasonerTimeOutException, is(true));
        }
    }

    /**
     * Verifies that the correct exception is thrown if a runtime exception occurs internally.
     */
    @Test
    @SuppressWarnings("ThrowableResultOfMethodCallIgnored")
    public void shouldThrowReasonerInternalExceptionForArbitraryRuntimeException() throws InterruptedException {
        Throwable cause = mock(RuntimeException.class);
        when(reasonerFactory.createNonBufferingReasoner(
                any(OWLOntology.class), any(OWLReasonerConfiguration.class)))
                .thenThrow(cause);
        ListenableFuture<ApplyChangesResponse> future = reasoner.execute(applyChangesAction);
        try {
            future.get();
            fail("Expected ExecutionException, but it was not thrown");
        } catch (ExecutionException e) {
            Throwable throwable = e.getCause();
            assertThat(throwable instanceof ReasonerInternalErrorException, is(true));
            assertThat(throwable.getCause(), is(cause));
        }

    }

    /**
     * Verifies that the correct exception is thrown if a ReasonerInternalErrorException exception occurs internally.
     */
    @Test
    @SuppressWarnings("ThrowableResultOfMethodCallIgnored")
    public void shouldReThrowReasonerInternalException() throws InterruptedException {
        ReasonerInternalErrorException cause = mock(ReasonerInternalErrorException.class);
        when(reasonerFactory.createNonBufferingReasoner(
                any(OWLOntology.class), any(OWLReasonerConfiguration.class)))
                .thenThrow(cause);
        ListenableFuture<ApplyChangesResponse> future = reasoner.execute(applyChangesAction);
        try {
            future.get();
        } catch (ExecutionException e) {
            Throwable throwable = e.getCause();
            assertThat(throwable instanceof ReasonerInternalErrorException, is(true));
            assertThat(throwable.getCause(), is(nullValue()));
        }
    }
}
