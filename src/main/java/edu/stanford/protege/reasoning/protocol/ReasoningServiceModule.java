package edu.stanford.protege.reasoning.protocol;

import com.google.inject.AbstractModule;
import edu.stanford.protege.reasoning.ReasoningService;
import edu.stanford.protege.reasoning.impl.ReasoningServiceImpl;

/**
 * @author Matthew Horridge, Stanford University, Bio-Medical Informatics Research Group, Date: 27/08/2014
 */
public class ReasoningServiceModule extends AbstractModule {

    @Override
    protected void configure() {
        install(new TranslatorModule());

        bind(ReasoningService.class).to(ReasoningServiceImpl.class);

    }
}
