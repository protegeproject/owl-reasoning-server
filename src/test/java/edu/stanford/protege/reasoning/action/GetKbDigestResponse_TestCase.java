package edu.stanford.protege.reasoning.action;

import edu.stanford.protege.reasoning.KbDigest;
import edu.stanford.protege.reasoning.KbId;
import org.hamcrest.core.IsNull;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.mockito.Mockito.mock;

/**
 * @author Matthew Horridge, Stanford University, Bio-Medical Informatics Research Group, Date: 29/08/2014
 */
@RunWith(MockitoJUnitRunner.class)
public class GetKbDigestResponse_TestCase {

    @Mock
    private KbId kbId;

    @Mock
    private KbDigest kbDigest;

    private GetKbDigestResponse response;

    @Before
    public void setUp() {
        response = new GetKbDigestResponse(kbId, kbDigest);
    }

    @Test
    public void shouldBeEqual() {
        GetKbDigestResponse actionB = new GetKbDigestResponse(kbId, kbDigest);
        assertThat(response, is(equalTo(actionB)));
    }

    @Test
    public void shouldReturnFalseForEqualsNull() {
        assertThat(response.equals(null), is(false));
    }

    @Test
    public void shouldBeEqualToSelf() {
        assertThat(response.equals(response), is(true));
    }

    @Test
    public void shouldHaveSameHashCode() {
        GetKbDigestResponse actionB = new GetKbDigestResponse(kbId, kbDigest);
        assertThat(response.hashCode(), is(equalTo(actionB.hashCode())));
    }

    @Test(expected = NullPointerException.class)
    public void shouldThrowNullPointerExceptionForNullId() {
        new GetKbDigestResponse(null, kbDigest);
    }

    @Test(expected = NullPointerException.class)
    public void shouldThrowNullPointerExceptionForNullDigest() {
        new GetKbDigestResponse(kbId, null);
    }

    @Test
    public void shouldNotBeEqual() {
        GetKbDigestResponse actionB = new GetKbDigestResponse(mock(KbId.class), mock(KbDigest.class));
        assertThat(response, is(not(equalTo(actionB))));
    }

    @Test
    public void shouldReturnProvidedId() {
        assertThat(response.getKbId(), is(equalTo(kbId)));
    }

    @Test
    public void shouldReturnProvidedDigest() {
        assertThat(response.getKbDigest(), is(equalTo(kbDigest)));
    }

    @Test
    public void shouldNotThrowNullPointerInToString() {
        String s = response.toString();
        assertThat(s, is(notNullValue()));
    }
}
