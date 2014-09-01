package edu.stanford.protege.reasoning.protocol;

import com.google.common.base.Optional;
import edu.stanford.protege.reasoning.action.Consistency;
import edu.stanford.protege.reasoning.KbDigest;
import edu.stanford.protege.reasoning.KbId;
import edu.stanford.protege.reasoning.action.IsConsistentResponse;

import javax.inject.Inject;
import java.io.IOException;

import static edu.stanford.protege.reasoning.protocol.Messages.IsConsistentResponseMessage;

/**
 * @author Matthew Horridge, Stanford University, Bio-Medical Informatics Research Group, Date: 20/08/2014
 */
public class IsConsistentResponseTranslator implements Translator<IsConsistentResponse, Messages.IsConsistentResponseMessage> {

    private final Translator<KbId, Messages.KbId> kbIdTranslator;

    private final Translator<KbDigest, Messages.KbDigest> kbDigestTranslator;

    private final Translator<Consistency, Messages.Consistency> consistencyTranslator;

    @Inject
    public IsConsistentResponseTranslator(Translator<KbId, Messages.KbId> kbIdTranslator, Translator<KbDigest, Messages.KbDigest> kbDigestTranslator, Translator<Consistency, Messages.Consistency> consistencyTranslator) {
        this.kbIdTranslator = kbIdTranslator;
        this.kbDigestTranslator = kbDigestTranslator;
        this.consistencyTranslator = consistencyTranslator;
    }

    @Override
    public IsConsistentResponse decode(IsConsistentResponseMessage message) {

        Optional<Consistency> consistency;
        if(message.getPresent()) {
            consistency = Optional.of(consistencyTranslator.decode(message.getResult()));
        }
        else {
            consistency = Optional.absent();
        }
        return new IsConsistentResponse(
                kbIdTranslator.decode(message.getKbId()),
                kbDigestTranslator.decode(message.getKbDigest()),
                consistency);
    }

    @Override
    public IsConsistentResponseMessage encode(IsConsistentResponse object) {
        IsConsistentResponseMessage.Builder builder = IsConsistentResponseMessage.newBuilder();
        builder.setKbId(kbIdTranslator.encode(object.getKbId()));
        builder.setKbDigest(kbDigestTranslator.encode(object.getKbDigest()));
        Optional<Consistency> consistency = object.getConsistency();
        if (consistency.isPresent()) {
            builder.setPresent(true);
            builder.setResult(consistencyTranslator.encode(consistency.get()));
        }
        else {
            builder.setPresent(false);
        }
        return builder.build();
    }

    @Override
    public IsConsistentResponse decode(byte[] bytes) throws IOException {
        return decode(IsConsistentResponseMessage.parseFrom(bytes));
    }
}
