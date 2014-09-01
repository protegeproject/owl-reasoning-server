package edu.stanford.protege.reasoning.protocol;

import edu.stanford.protege.reasoning.KbDigest;
import edu.stanford.protege.reasoning.KbId;
import edu.stanford.protege.reasoning.action.GetKbDigestResponse;

import javax.inject.Inject;
import java.io.IOException;

/**
 * @author Matthew Horridge, Stanford University, Bio-Medical Informatics Research Group, Date: 28/08/2014
 */
public class GetKbDigestResponseTranslator implements Translator<GetKbDigestResponse, Messages.GetKbDigestResponseMessage> {

    private Translator<KbId, Messages.KbId> kbIdTranslator;

    private Translator<KbDigest, Messages.KbDigest> kbDigestTranslator;

    @Inject
    public GetKbDigestResponseTranslator(Translator<KbId, Messages.KbId> kbIdTranslator, Translator<KbDigest, Messages.KbDigest> kbDigestTranslator) {
        this.kbIdTranslator = kbIdTranslator;
        this.kbDigestTranslator = kbDigestTranslator;
    }

    @Override
    public GetKbDigestResponse decode(Messages.GetKbDigestResponseMessage message) {
        return new GetKbDigestResponse(
                kbIdTranslator.decode(message.getKbId()),
                kbDigestTranslator.decode(message.getKbDigest())
        );
    }

    @Override
    public Messages.GetKbDigestResponseMessage encode(GetKbDigestResponse object) {
        return Messages.GetKbDigestResponseMessage.newBuilder()
                .setKbId(kbIdTranslator.encode(object.getKbId()))
                .setKbDigest(kbDigestTranslator.encode(object.getKbDigest()))
                .build();
    }

    @Override
    public GetKbDigestResponse decode(byte[] bytes) throws IOException {
        return decode(Messages.GetKbDigestResponseMessage.parseFrom(bytes));
    }
}
