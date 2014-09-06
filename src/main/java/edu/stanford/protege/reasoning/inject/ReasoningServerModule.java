package edu.stanford.protege.reasoning.inject;

import com.google.inject.AbstractModule;

/**
 * @author Matthew Horridge, Stanford University, Bio-Medical Informatics Research Group, Date: 06/09/2014
 */
public class ReasoningServerModule extends AbstractModule {

    @Override
    protected void configure() {
        install(new ReasoningServerCodecModule());
        install(new ReasoningServiceModule());
    }
}
