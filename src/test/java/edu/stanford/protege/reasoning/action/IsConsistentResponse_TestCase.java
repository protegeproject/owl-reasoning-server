package edu.stanford.protege.reasoning.action;

import com.google.common.base.Optional;
import edu.stanford.protege.reasoning.KbDigest;
import edu.stanford.protege.reasoning.KbId;
import edu.stanford.protege.reasoning.protocol.Messages;
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
 * @author Matthew Horridge, Stanford University, Bio-Medical Informatics Research Group, Date: 29/08/2014
 */
@RunWith(MockitoJUnitRunner.class)
public class IsConsistentResponse_TestCase {


    @Mock
    private KbId kbId;

    @Mock
    private KbDigest kbDigest;

    @Mock
    private Optional<Consistency> consistency;

    private IsConsistentResponse action;

    @Before
    public void setUp() {
        action = new IsConsistentResponse(kbId, kbDigest, consistency);
    }

    @Test
    public void shouldBeEqual() {
        IsConsistentResponse actionB = new IsConsistentResponse(kbId, kbDigest, consistency);
        assertThat(action, is(equalTo(actionB)));
    }

    @Test
    public void shouldHaveSameHashCode() {
        IsConsistentResponse actionB = new IsConsistentResponse(kbId, kbDigest, consistency);
        assertThat(action.hashCode(), is(equalTo(actionB.hashCode())));
    }

    @Test(expected = NullPointerException.class)
    public void shouldThrowNullPointerExceptionForNullId() {
        new IsConsistentResponse(null, kbDigest, consistency);
    }

    @Test(expected = NullPointerException.class)
    public void shouldThrowNullPointerExceptionForNullDigest() {
        new IsConsistentResponse(kbId, null, consistency);
    }

    @Test
    public void shouldNotBeEqual() {
        IsConsistentResponse actionB = new IsConsistentResponse(mock(KbId.class), mock(KbDigest.class), consistency);
        assertThat(action, is(not(equalTo(actionB))));
    }

    @Test
    public void shouldReturnProvidedId() {
        assertThat(action.getKbId(), is(equalTo(kbId)));
    }

    @Test
    public void shouldReturnProvidedDigest() {
        assertThat(action.getKbDigest(), is(equalTo(kbDigest)));
    }

    @Test
    public void shouldReturnProvidedConsistency() {
        assertThat(action.getConsistency(), is(equalTo(consistency)));
    }
}
