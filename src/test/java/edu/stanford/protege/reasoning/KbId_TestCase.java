package edu.stanford.protege.reasoning;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

/**
 * @author Matthew Horridge, Stanford University, Bio-Medical Informatics Research Group, Date: 20/07/2014
 */
public class KbId_TestCase {

    @Test(expected = NullPointerException.class)
    public void shouldThrowNullPointerException() {
        new KbId(null);
    }

    @Test
    public void shouldReturnSuppliedId() {
        KbId id = new KbId("x");
        assertThat(id.getLexicalForm(), is(equalTo("x")));
    }


    @Test
    public void shouldEqualSelf() {
        KbId kbId = new KbId("x");
        assertThat(kbId, is(equalTo(kbId)));
    }

    @Test
    public void shouldBeEqual() {
        KbId kbIdA = new KbId("x");
        KbId kbIdB = new KbId("x");
        assertThat(kbIdA, is((equalTo(kbIdB))));
    }

    @Test
    public void shouldHaveTheSameHashCode() {
        KbId kbIdA = new KbId("x");
        KbId kbIdB = new KbId("x");
        assertThat(kbIdA.hashCode(), is((equalTo(kbIdB.hashCode()))));
    }

    @Test
    public void shouldNotBeEqual() {
        KbId kbIdA = new KbId("x");
        KbId kbIdB = new KbId("y");
        assertThat(kbIdA, is(not(equalTo(kbIdB))));
    }

    @Test
    public void shouldNotHaveTheSameHashCode() {
        KbId kbIdA = new KbId("x");
        KbId kbIdB = new KbId("y");
        assertThat(kbIdA.hashCode(), is(not(equalTo(kbIdB.hashCode()))));
    }
}
