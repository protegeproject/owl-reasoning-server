package edu.stanford.protege.reasoning.action;

import org.junit.Test;

/**
 * @author Matthew Horridge, Stanford University, Bio-Medical Informatics Research Group, Date: 06/09/2014
 */
public class Progress_ArgumentCheck_TestCase {


    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowIllegalArgumentExceptionWhenValueIsLessThanInitial() {
        Progress.from(0).to(10).withValue(-1);
    }


    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowIllegalArgumentExceptionWhenValueIsGreaterThanFinal() {
        Progress.from(0).to(10).withValue(11);
    }
}
