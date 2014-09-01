package edu.stanford.protege.reasoning.protocol;

import edu.stanford.protege.reasoning.KbDigest;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.mock;

/**
 * @author Matthew Horridge, Stanford University, Bio-Medical Informatics Research Group, Date: 27/08/2014
 */
public class KBDigestTranslator_TestCase {


    public static final byte[] bytes = new byte[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20};

    @Test
    public void shouldRoundTrip() {
        KbDigest digest = KbDigest.fromByteArray(bytes);
        KbDigestTranslator translator = new KbDigestTranslator();
        Messages.KbDigest digestMsg = translator.encode(digest);
        KbDigest decodedDigest = translator.decode(digestMsg);
        assertThat(digest, is(equalTo(decodedDigest)));
    }
}
