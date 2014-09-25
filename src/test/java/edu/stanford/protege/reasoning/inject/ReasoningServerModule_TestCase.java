package edu.stanford.protege.reasoning.inject;

import com.google.inject.Guice;
import com.google.inject.Injector;
import edu.stanford.protege.reasoning.protocol.ReasoningClient;
import edu.stanford.protege.reasoning.protocol.ReasoningClientFactory;
import edu.stanford.protege.reasoning.protocol.ReasoningServer;
import org.junit.Before;
import org.junit.Test;

import java.net.InetSocketAddress;

import static org.mockito.Mockito.mock;

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
    public void shouldInstantiateReasoningService() {
        InetSocketAddress address = mock(InetSocketAddress.class);
        ReasoningClientFactory factory = injector.getInstance(ReasoningClientFactory.class);
        factory.createReasoningClient(address);
    }
}
