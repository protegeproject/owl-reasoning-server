package edu.stanford.protege.reasoning.protocol;

import java.net.InetSocketAddress;

/**
 * @author Matthew Horridge, Stanford University, Bio-Medical Informatics Research Group, Date: 24/09/2014
 */
public interface ReasoningClientFactory {

    ReasoningClient createReasoningClient(InetSocketAddress inetSocketAddress);
}
