package edu.stanford.protege.reasoning.inject;

import com.google.common.eventbus.EventBus;
import com.google.inject.Guice;
import com.google.inject.Injector;
import edu.stanford.protege.reasoning.KbId;
import edu.stanford.protege.reasoning.ReasoningService;
import edu.stanford.protege.reasoning.impl.KbReasonerFactory;
import edu.stanford.protege.reasoning.inject.ReasoningServiceModule;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

/**
 * @author Matthew Horridge, Stanford University, Bio-Medical Informatics Research Group, Date: 02/09/2014
 */
@RunWith(MockitoJUnitRunner.class)
public class ReasoningServiceModule_TestCase {

    private Injector injector;

    @Mock
    private KbId kbId;

    @Before
    public void setUp() throws Exception {
        injector = Guice.createInjector(new ReasoningServiceModule());
    }

    @Test
    public void shouldInstantiateEventBus() {
        injector.getInstance(EventBus.class);
    }

    @Test
    public void shouldInstantiateKbReasonerFactory() {
        injector.getInstance(KbReasonerFactory.class);
    }

    @Test
    public void shouldInstantiateKbReasoner() {
        injector.getInstance(KbReasonerFactory.class).createReasoner(kbId);
    }

    @Test
    public void shouldInstantiateReasoningService() {
        injector.getInstance(ReasoningService.class);
    }
}
