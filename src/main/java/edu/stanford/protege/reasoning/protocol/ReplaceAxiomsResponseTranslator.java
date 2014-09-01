package edu.stanford.protege.reasoning.protocol;

import edu.stanford.protege.reasoning.KbDigest;
import edu.stanford.protege.reasoning.KbId;
import edu.stanford.protege.reasoning.action.ReplaceAxiomsResponse;

import javax.inject.Inject;
import java.io.IOException;

/**
 * @author Matthew Horridge, Stanford University, Bio-Medical Informatics Research Group, Date: 28/08/2014
 */
public class ReplaceAxiomsResponseTranslator implements Translator<ReplaceAxiomsResponse, Messages.ReplaceAxiomsResponseMessage> {

    private final Translator<KbId, Messages.KbId> kbIdTranslator;

    private final Translator<KbDigest, Messages.KbDigest> kbDigestTranslator;

    @Inject
    public ReplaceAxiomsResponseTranslator(Translator<KbId, Messages.KbId> kbIdTranslator, Translator<KbDigest, Messages.KbDigest> kbDigestTranslator) {
        this.kbIdTranslator = kbIdTranslator;
        this.kbDigestTranslator = kbDigestTranslator;
    }

    @Override
    public ReplaceAxiomsResponse decode(Messages.ReplaceAxiomsResponseMessage message) {
        return new ReplaceAxiomsResponse(
                kbIdTranslator.decode(message.getKbId()),
                kbDigestTranslator.decode(message.getKbDigest())
        );
    }

    @Override
    public Messages.ReplaceAxiomsResponseMessage encode(ReplaceAxiomsResponse object) {
        return Messages.ReplaceAxiomsResponseMessage.newBuilder()
                .setKbId(kbIdTranslator.encode(object.getKbId()))
                .setKbDigest(kbDigestTranslator.encode(object.getKbDigest()))
                .build();
    }

    @Override
    public ReplaceAxiomsResponse decode(byte[] bytes) throws IOException {
        return decode(Messages.ReplaceAxiomsResponseMessage.parseFrom(bytes));
    }
}
