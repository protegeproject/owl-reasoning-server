package edu.stanford.protege.reasoning.protocol;

import com.google.inject.Guice;
import com.google.inject.Injector;
import edu.stanford.protege.reasoning.inject.ReasoningServerModule;

import java.net.InetSocketAddress;

/**
 * @author Matthew Horridge, Stanford University, Bio-Medical Informatics Research Group, Date: 25/08/2014
 */
public class ReasoningServerMain {

    public static void main(String[] args) throws Exception {
        Injector injector = Guice.createInjector(new ReasoningServerModule());
        InetSocketAddress address = new InetSocketAddress(3456);
        ReasoningServer server = injector.getInstance(ReasoningServer.class);
        server.start(address);
    }
}
