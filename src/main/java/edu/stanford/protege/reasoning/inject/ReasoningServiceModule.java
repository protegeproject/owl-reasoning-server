package edu.stanford.protege.reasoning.inject;

import com.google.common.eventbus.EventBus;
import com.google.inject.AbstractModule;
import com.google.inject.Singleton;
import com.google.inject.assistedinject.FactoryModuleBuilder;
import edu.stanford.protege.reasoning.ReasoningService;
import edu.stanford.protege.reasoning.impl.*;

/**
 * @author Matthew Horridge, Stanford University, Bio-Medical Informatics Research Group, Date: 20/07/2014
 */
public class ReasoningServiceModule extends AbstractModule {
    @Override
    protected void configure() {

        bind(EventBus.class).in(Singleton.class);
        bind(ReasoningService.class).to(ReasoningServiceImpl.class);
        bind(OWLReasonerFactorySelector.class).to(DefaultOWLReasonerFactorySelector.class);
        install(new FactoryModuleBuilder().implement(KbReasoner.class,
                                                     KbReasonerImpl.class).build(KbReasonerFactory.class));
    }
}
