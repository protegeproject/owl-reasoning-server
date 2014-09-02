package edu.stanford.protege.reasoning.impl;

import com.google.common.eventbus.EventBus;
import com.google.common.util.concurrent.ListenableFuture;
import edu.stanford.protege.reasoning.KbId;
import edu.stanford.protege.reasoning.Response;
import edu.stanford.protege.reasoning.action.ActionHandler;
import edu.stanford.protege.reasoning.action.KbAction;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * @author Matthew Horridge, Stanford University, Bio-Medical Informatics Research Group, Date: 02/09/2014
 */
@RunWith(MockitoJUnitRunner.class)
public class ReasoningServiceImpl_TestCase<A extends KbAction<R, H>, R extends Response, H extends ActionHandler> {

    private ReasoningServiceImpl reasoningService;

    @Mock
    private EventBus eventBus;

    @Mock
    private HandlerRegistry handlerRegistry;

    @Mock
    private KbReasonerFactory reasonerFactory;

    @Mock
    private KbId kbId;

    @Mock
    private KbReasoner kbReasoner;

    @Mock
    private A kbAction;

    @Mock
    private ListenableFuture<R> future;

    @Before
    public void setUp() throws Exception {
        when(kbAction.getKbId()).thenReturn(kbId);
        when(reasonerFactory.createReasoner(kbId)).thenReturn(kbReasoner);
        reasoningService = new ReasoningServiceImpl(reasonerFactory, eventBus, handlerRegistry);
        when(kbReasoner.execute(kbAction)).thenReturn(future);
    }

    @Test
    public void shouldInstantiateReasoner() {
        reasoningService.execute(kbAction);
        verify(reasonerFactory, times(1)).createReasoner(kbId);
    }

    @Test
    public void shouldExecuteActionOnReasoner() {
        reasoningService.execute(kbAction);
        verify(kbReasoner, times(1)).execute(kbAction);
    }

    @Test
    public void shouldReturnResponse() {
        ListenableFuture<R> response = reasoningService.execute(kbAction);
        assertThat(response, is(equalTo(future)));
    }

    @Test
    public void shouldShutDownInstantiatedReasonersOnShutDown() {
        reasoningService.execute(kbAction);
        reasoningService.shutDown();
        verify(kbReasoner, times(1)).shutDown();
    }
}
