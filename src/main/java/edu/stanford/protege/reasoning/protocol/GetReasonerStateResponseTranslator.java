package edu.stanford.protege.reasoning.protocol;

import com.google.inject.Inject;
import edu.stanford.protege.reasoning.KbId;
import edu.stanford.protege.reasoning.action.GetReasonerStateResponse;
import edu.stanford.protege.reasoning.action.ReasonerState;

import java.io.IOException;

/**
 * @author Matthew Horridge, Stanford University, Bio-Medical Informatics Research Group, Date: 06/09/2014
 */
public class GetReasonerStateResponseTranslator implements Translator<GetReasonerStateResponse, Messages.GetReasonerStateResponseMessage> {

    private Translator<KbId, Messages.KbId> kbIdTranslator;

    private Translator<ReasonerState, Messages.ReasonerState> processingStateTranslator;

    @Inject
    public GetReasonerStateResponseTranslator(Translator<KbId, Messages.KbId> kbIdTranslator,
                                              Translator<ReasonerState,
                                                      Messages.ReasonerState> processingStateTranslator) {
        this.kbIdTranslator = kbIdTranslator;
        this.processingStateTranslator = processingStateTranslator;
    }

    @Override
    public GetReasonerStateResponse decode(Messages.GetReasonerStateResponseMessage message) {
        return new GetReasonerStateResponse(
                kbIdTranslator.decode(message.getKbId()),
                processingStateTranslator.decode(message.getReasonerState())
        );
    }

    @Override
    public GetReasonerStateResponse decode(byte[] bytes) throws IOException {
        return decode(Messages.GetReasonerStateResponseMessage.parseFrom(bytes));
    }

    @Override
    public Messages.GetReasonerStateResponseMessage encode(GetReasonerStateResponse object) {
        return Messages.GetReasonerStateResponseMessage.newBuilder()
                .setKbId(kbIdTranslator.encode(object.getKbId()))
                .setReasonerState(processingStateTranslator.encode(object.getReasonerState()))
                .build();
    }
}
