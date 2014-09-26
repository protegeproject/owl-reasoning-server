package edu.stanford.protege.reasoning.inject;

import com.google.common.eventbus.EventBus;
import com.google.inject.AbstractModule;
import com.google.inject.Singleton;
import com.google.inject.assistedinject.FactoryModuleBuilder;
import com.google.inject.name.Named;
import com.google.inject.name.Names;
import edu.stanford.protege.reasoning.ReasoningService;
import edu.stanford.protege.reasoning.impl.*;
import edu.stanford.protege.reasoning.protocol.ReasoningClient;
import edu.stanford.protege.reasoning.protocol.ReasoningClientFactory;

/**
 * @author Matthew Horridge, Stanford University, Bio-Medical Informatics Research Group, Date: 20/07/2014
 */
public class ReasoningServiceModule extends AbstractModule {

    private static final long REASONER_TIME_OUT_MS = 1 * 60 * 1000;

    @Override
    protected void configure() {
        bindConstant().annotatedWith(Names.named(KbReasonerImpl.TIMEOUT_VARIABLE_NAME)).to(REASONER_TIME_OUT_MS);
        bind(EventBus.class).in(Singleton.class);
        bind(ReasoningService.class).to(ReasoningServiceImpl.class);
        bind(OWLReasonerFactorySelector.class).to(DefaultOWLReasonerFactorySelector.class);
        install(new FactoryModuleBuilder().implement(KbReasoner.class,
                                                     KbReasonerImpl.class).build(KbReasonerFactory.class));
        install(new FactoryModuleBuilder().build(ReasoningClientFactory.class));
    }
}
