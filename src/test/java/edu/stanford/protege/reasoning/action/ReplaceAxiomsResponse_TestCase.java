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
import static org.mockito.Mockito.mock;

/**
 * @author Matthew Horridge, Stanford University, Bio-Medical Informatics Research Group, Date: 31/08/2014
 */
@RunWith(MockitoJUnitRunner.class)
public class ReplaceAxiomsResponse_TestCase {

    @Mock
    private KbId kbId;

    @Mock
    private KbDigest kbDigest;

    private ReplaceAxiomsResponse response;

    @Before
    public void setUp() {
        response = new ReplaceAxiomsResponse(kbId, kbDigest);
    }

    @Test
    public void shouldBeEqual() {
        ReplaceAxiomsResponse actionB = new ReplaceAxiomsResponse(kbId, kbDigest);
        assertThat(response, is(equalTo(actionB)));
    }

    @Test
    public void shouldBeEqualToSelf() {
        assertThat(response.equals(response), is(true));
    }

    @Test
    public void shouldNotBeEqualToNull() {
        assertThat(response.equals(null), is(false));
    }

    @Test
    public void shouldReturnFalseForEqualsNull() {
        assertThat(response.equals(null), is(false));
    }

    @Test
    public void shouldHaveSameHashCode() {
        ReplaceAxiomsResponse actionB = new ReplaceAxiomsResponse(kbId, kbDigest);
        assertThat(response.hashCode(), is(equalTo(actionB.hashCode())));
    }

    @Test(expected = NullPointerException.class)
    public void shouldThrowNullPointerExceptionForNullId() {
        new ReplaceAxiomsResponse(null, kbDigest);
    }

    @Test(expected = NullPointerException.class)
    public void shouldThrowNullPointerExceptionForNullDigest() {
        new ReplaceAxiomsResponse(kbId, null);
    }

    @Test
    public void shouldNotBeEqual() {
        ReplaceAxiomsResponse actionB = new ReplaceAxiomsResponse(mock(KbId.class), mock(KbDigest.class));
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
        assertThat(s, is(IsNull.notNullValue()));
    }
}
