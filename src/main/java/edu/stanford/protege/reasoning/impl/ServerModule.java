package edu.stanford.protege.reasoning.impl;

import com.google.common.eventbus.EventBus;
import com.google.inject.AbstractModule;
import com.google.inject.Singleton;
import edu.stanford.protege.reasoning.ReasoningService;

/**
 * @author Matthew Horridge, Stanford University, Bio-Medical Informatics Research Group, Date: 20/07/2014
 */
public class ServerModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(EventBus.class).in(Singleton.class);
        bind(ReasoningService.class).to(ReasoningServiceImpl.class);
        bind(KbReasoner.class).to(KbReasonerImpl.class);
        bind(OWLReasonerFactorySelector.class).to(DefaultOWLReasonerFactorySelector.class);
    }
}
