package edu.stanford.protege.reasoning.action;

import com.google.inject.Inject;
import edu.stanford.protege.reasoning.KbId;
import edu.stanford.protege.reasoning.protocol.Messages;
import edu.stanford.protege.reasoning.protocol.Translator;

import java.io.IOException;

/**
 * @author Matthew Horridge, Stanford University, Bio-Medical Informatics Research Group, Date: 06/09/2014
 */
public class GetProcessingStateResponseTranslator implements Translator<GetProcessingStateResponse, Messages.GetProcessingStateResponseMessage> {

    private Translator<KbId, Messages.KbId> kbIdTranslator;

    private Translator<ProcessingState, Messages.ProcessingState> processingStateTranslator;

    @Inject
    public GetProcessingStateResponseTranslator(Translator<KbId, Messages.KbId> kbIdTranslator,
                                                Translator<ProcessingState,
                                                        Messages.ProcessingState> processingStateTranslator) {
        this.kbIdTranslator = kbIdTranslator;
        this.processingStateTranslator = processingStateTranslator;
    }

    @Override
    public GetProcessingStateResponse decode(Messages.GetProcessingStateResponseMessage message) {
        return new GetProcessingStateResponse(
                kbIdTranslator.decode(message.getKbId()),
                processingStateTranslator.decode(message.getProcessingState())
        );
    }

    @Override
    public GetProcessingStateResponse decode(byte[] bytes) throws IOException {
        return decode(Messages.GetProcessingStateResponseMessage.parseFrom(bytes));
    }

    @Override
    public Messages.GetProcessingStateResponseMessage encode(GetProcessingStateResponse object) {
        return Messages.GetProcessingStateResponseMessage.newBuilder()
                .setKbId(kbIdTranslator.encode(object.getKbId()))
                .setProcessingState(processingStateTranslator.encode(object.getProcessingState()))
                .build();
    }
}
