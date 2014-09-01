package edu.stanford.protege.reasoning;

import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * @author Matthew Horridge, Stanford University, Bio-Medical Informatics Research Group, Date: 20/07/2014
 */
public class KbDigest_TestCase {

    private byte[] bytes;

    @Before
    public void setUp() throws Exception {
        final int totalBytes = 20;
        bytes = new byte[totalBytes];
        for(int i = 0; i < totalBytes; i++) {
            bytes[i] = totalBytes + 1;
        }
    }

    @Test
    public void shouldBeEqualToSelf() {
        KbDigest revisionId = KbDigest.emptyDigest();
        assertThat(revisionId, is(equalTo(revisionId)));
    }

    @Test
    public void shouldBeEqualToDigestWithEqualBytes() {
        KbDigest digestA = KbDigest.fromByteArray(bytes);
        KbDigest digestB = KbDigest.fromByteArray(bytes);
        assertThat(digestA, is(equalTo(digestB)));
    }

    @Test
    public void shouldHaveSameHashCode() {
        bytes[0] = 1;
        bytes[1] = 2;
        bytes[2] = 3;
        KbDigest digestA = KbDigest.fromByteArray(bytes);
        KbDigest digestB = KbDigest.fromByteArray(bytes);
        assertThat(digestA.hashCode(), is(equalTo(digestB.hashCode())));
    }

    @Test(expected = NullPointerException.class)
    public void shouldThrowNullPointerExceptionIfBytesIsNull() {
        KbDigest.fromByteArray((byte[]) null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowIllegalArgumentExceptionIfBytesIsNot20() {
        bytes = new byte[2];
        KbDigest.fromByteArray(bytes);
    }

    @Test
    public void shouldReturnSuppliedBytes() {
        KbDigest digest = KbDigest.fromByteArray(bytes);
        assertThat(Arrays.equals(digest.toByteArray(), bytes), is(true));
    }
}
