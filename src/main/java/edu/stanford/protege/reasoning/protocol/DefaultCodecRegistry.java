package edu.stanford.protege.reasoning.protocol;

import com.google.inject.Guice;
import com.google.inject.Injector;

/**
 * @author Matthew Horridge, Stanford University, Bio-Medical Informatics Research Group, Date: 29/08/2014
 */
public class DefaultCodecRegistry {

    public static ReasoningServerCodecRegistry getRegistry() {
        Injector injector = Guice.createInjector(new TranslatorModule());
        ReasoningServerCodecRegistry registry = new ReasoningServerCodecRegistry();

        registry.register(injector.getInstance(GetKbDigestCodec.class));
        registry.register(injector.getInstance(ApplyChangesCodec.class));
        registry.register(injector.getInstance(ReplaceAxiomsCodec.class));

        registry.register(injector.getInstance(IsConsistentCodec.class));
        registry.register(injector.getInstance(IsEntailedCodec.class));

        registry.register(injector.getInstance(GetSubClassesCodec.class));
        registry.register(injector.getInstance(GetSuperClassesCodec.class));
        registry.register(injector.getInstance(GetInstancesCodec.class));

        return registry;
    }
}
