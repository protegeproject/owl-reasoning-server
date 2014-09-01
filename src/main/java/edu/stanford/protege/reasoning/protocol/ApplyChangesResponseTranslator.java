package edu.stanford.protege.reasoning.protocol;

import com.google.inject.Inject;
import edu.stanford.protege.reasoning.KbDigest;
import edu.stanford.protege.reasoning.KbId;
import edu.stanford.protege.reasoning.action.ApplyChangesResponse;

import java.io.IOException;

/**
 * @author Matthew Horridge, Stanford University, Bio-Medical Informatics Research Group, Date: 27/08/2014
 */
public class ApplyChangesResponseTranslator implements Translator<ApplyChangesResponse, Messages.ApplyChangesResponseMessage> {

    private Translator<KbId, Messages.KbId> kbIdTranslator;

    private Translator<KbDigest, Messages.KbDigest> kbDigestTranslator;

    @Inject
    public ApplyChangesResponseTranslator(Translator<KbId, Messages.KbId> kbIdTranslator, Translator<KbDigest, Messages.KbDigest> kbDigestTranslator) {
        this.kbIdTranslator = kbIdTranslator;
        this.kbDigestTranslator = kbDigestTranslator;
    }

    @Override
    public ApplyChangesResponse decode(Messages.ApplyChangesResponseMessage message) {
        return new ApplyChangesResponse(
                kbIdTranslator.decode(message.getKbId()),
                kbDigestTranslator.decode(message.getKbDigest())
        );
    }

    @Override
    public Messages.ApplyChangesResponseMessage encode(ApplyChangesResponse object) {
        return Messages.ApplyChangesResponseMessage.newBuilder()
                .setKbId(kbIdTranslator.encode(object.getKbId()))
                .setKbDigest(kbDigestTranslator.encode(object.getKbDigest()))
                .build();
    }

    @Override
    public ApplyChangesResponse decode(byte[] bytes) throws IOException {
        return decode(Messages.ApplyChangesResponseMessage.parseFrom(bytes));
    }
}
