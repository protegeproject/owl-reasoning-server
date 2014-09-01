package edu.stanford.protege.reasoning.action;

import org.hamcrest.MatcherAssert;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

/**
 * @author Matthew Horridge, Stanford University, Bio-Medical Informatics Research Group, Date: 29/08/2014
 */
public class HierarchyQueryType_TestCase {

    @Test
    public void shouldReturnTrueForDirect() {
        MatcherAssert.assertThat(HierarchyQueryType.DIRECT.isDirect(), is(true));
    }

    @Test
    public void shouldReturnFalseForIndirect() {
        assertThat(HierarchyQueryType.INDIRECT.isDirect(), is(false));
    }
}
