package edu.stanford.protege.reasoning.inject;

import com.google.inject.AbstractModule;
import com.google.inject.multibindings.Multibinder;
import edu.stanford.protege.reasoning.protocol.*;

/**
 * @author Matthew Horridge, Stanford University, Bio-Medical Informatics Research Group, Date: 29/08/2014
 */
public class ReasoningServerCodecModule extends AbstractModule {

    @Override
    protected void configure() {
        install(new TranslatorModule());
        Multibinder<ReasoningServerCodec> codecBinder = Multibinder.newSetBinder(binder(), ReasoningServerCodec.class);
        codecBinder.addBinding().to(GetKbDigestCodec.class);
        codecBinder.addBinding().to(ApplyChangesCodec.class);
        codecBinder.addBinding().to(ReplaceAxiomsCodec.class);

        codecBinder.addBinding().to(IsConsistentCodec.class);
        codecBinder.addBinding().to(IsEntailedCodec.class);

        codecBinder.addBinding().to(GetSubClassesCodec.class);
        codecBinder.addBinding().to(GetSuperClassesCodec.class);
        codecBinder.addBinding().to(GetInstancesCodec.class);

        codecBinder.addBinding().to(GetProcessingStateCodec.class);
    }
}
