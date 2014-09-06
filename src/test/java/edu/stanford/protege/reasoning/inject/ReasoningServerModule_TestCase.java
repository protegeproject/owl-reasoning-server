package edu.stanford.protege.reasoning.inject;

import com.google.inject.Guice;
import com.google.inject.Injector;
import edu.stanford.protege.reasoning.protocol.ReasoningClient;
import edu.stanford.protege.reasoning.protocol.ReasoningServer;
import org.junit.Before;
import org.junit.Test;

/**
 * @author Matthew Horridge, Stanford University, Bio-Medical Informatics Research Group, Date: 06/09/2014
 */
public class ReasoningServerModule_TestCase {

    private Injector injector;

    @Before
    public void setUp() throws Exception {
        injector = Guice.createInjector(new ReasoningServerModule());
    }

    @Test
    public void shouldInstantiateReasoningServer() {
        injector.getInstance(ReasoningServer.class);
    }

    @Test
    public void shouldInstantiateReaoningClient() {
        injector.getInstance(ReasoningClient.class);
    }
}
