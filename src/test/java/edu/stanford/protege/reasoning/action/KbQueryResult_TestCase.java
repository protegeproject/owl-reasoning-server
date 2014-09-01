package edu.stanford.protege.reasoning.action;

import com.google.common.base.Optional;
import edu.stanford.protege.reasoning.KbQueryResult;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

/**
 * @author Matthew Horridge, Stanford University, Bio-Medical Informatics Research Group, Date: 31/08/2014
 */
@RunWith(MockitoJUnitRunner.class)
public class KbQueryResult_TestCase<R> {

    @Mock
    private R resultValue;

    @Test(expected = NullPointerException.class)
    public void shouldThrowNullPointerForNullResult() {
        KbQueryResult.optionalOfValue(null);
    }

    @Test
    public void shouldReturnInconsistentResult() {
        KbQueryResult<R> result = KbQueryResult.ofInconsistentKb();
        assertThat(result.getConsistency(), is(Consistency.INCONSISTENT));
    }

    @Test
    public void shouldReturnConsistentResultFalse() {
        KbQueryResult<R> result = KbQueryResult.ofInconsistentKb();
        assertThat(result.isConsistent(), is(false));
    }

    @Test(expected = IllegalStateException.class)
    public void shouldThrowIllegalStateException() {
        KbQueryResult.ofInconsistentKb().getValue();
    }

    @Test
    public void shouldReturnPresentResultForInconsistentKb() {
        Optional<KbQueryResult<R>> result = KbQueryResult.optionalOfInconsistentKb();
        assertThat(result.isPresent(), is(true));
    }

    @Test
    public void shouldReturnOptionalOfInconsistentResult() {
        Optional<KbQueryResult<R>> result = KbQueryResult.optionalOfInconsistentKb();
        KbQueryResult<R> inconsistentResult = KbQueryResult.ofInconsistentKb();
        assertThat(result.get(), is(equalTo(inconsistentResult)));
    }

    @Test
    public void shouldReturnConsistent() {
        KbQueryResult<R> result = KbQueryResult.ofValue(resultValue);
        assertThat(result.getConsistency(), is(Consistency.CONSISTENT));
    }

    @Test
    public void shouldReturnConsistentResultTrue() {
        KbQueryResult<R> result = KbQueryResult.ofValue(resultValue);
        assertThat(result.isConsistent(), is(true));
    }

    @Test
    public void shouldReturnSuppliedResultValue() {
        KbQueryResult<R> result = KbQueryResult.ofValue(resultValue);
        assertThat(result.getValue(), is(equalTo(resultValue)));
    }

    @Test
    public void shouldBeEqualForEqualResultValue() {
        KbQueryResult<R> resultA = KbQueryResult.ofValue(resultValue);
        KbQueryResult<R> resultB = KbQueryResult.ofValue(resultValue);
        assertThat(resultA, is(equalTo(resultB)));
    }

    @Test
    public void shouldHaveSameHashCodeForEqualResultValue() {
        KbQueryResult<R> resultA = KbQueryResult.ofValue(resultValue);
        KbQueryResult<R> resultB = KbQueryResult.ofValue(resultValue);
        assertThat(resultA.hashCode(), is(equalTo(resultB.hashCode())));
    }

    @Test
    public void shouldReturnOptionalOfSuppliedValue() {
        KbQueryResult<R> result = KbQueryResult.ofValue(resultValue);
        Optional<KbQueryResult<R>> optional = KbQueryResult.optionalOfValue(resultValue);
        assertThat(optional, is(equalTo(Optional.of(result))));
    }


}
