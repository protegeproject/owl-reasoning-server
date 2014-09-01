package edu.stanford.protege.reasoning.protocol;

import com.google.protobuf.ByteString;
import edu.stanford.protege.reasoning.KbDigest;

import java.io.IOException;

/**
 * @author Matthew Horridge, Stanford University, Bio-Medical Informatics Research Group, Date: 25/08/2014
 */
public class KbDigestTranslator implements Translator<KbDigest, Messages.KbDigest> {

    @Override
    public KbDigest decode(Messages.KbDigest message) {
        return KbDigest.fromByteArray(message.getDigest().toByteArray());
    }

    @Override
    public Messages.KbDigest encode(KbDigest object) {
        return Messages.KbDigest.newBuilder().setDigest(ByteString.copyFrom(object.toByteArray())).build();
    }

    @Override
    public KbDigest decode(byte[] bytes) throws IOException {
        return decode(Messages.KbDigest.parseFrom(bytes));
    }
}
