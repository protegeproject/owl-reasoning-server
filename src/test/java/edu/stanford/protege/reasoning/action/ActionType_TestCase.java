package edu.stanford.protege.reasoning.action;

import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;

/**
 * @author Matthew Horridge, Stanford University, Bio-Medical Informatics Research Group, Date: 31/08/2014
 */
public class ActionType_TestCase<H extends ActionHandler> {

    @Test
    public void shouldEqualSelf() {
        ActionType<H> actionType = ActionType.create();
        assertThat(actionType, is((equalTo(actionType))));
    }

    @Test
    public void shouldEqualSelfHashCode() {
        ActionType<H> actionType = ActionType.create();
        assertThat(actionType.hashCode(), is((equalTo(actionType.hashCode()))));
    }

    @Test
    public void shouldReturnNonEqualType() {
        ActionType<H> actionTypeA = ActionType.create();
        ActionType<H> actionTypeB = ActionType.create();
        assertThat(actionTypeA, is(not(equalTo(actionTypeB))));
    }

    @Test
    public void shouldReturnNonEqualHashCodes() {
        ActionType<H> actionTypeA = ActionType.create();
        ActionType<H> actionTypeB = ActionType.create();
        assertThat(actionTypeA.hashCode(), is(not(equalTo(actionTypeB.hashCode()))));
    }
}
