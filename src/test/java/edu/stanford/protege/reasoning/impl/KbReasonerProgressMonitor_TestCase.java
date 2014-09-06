package edu.stanford.protege.reasoning.impl;

import edu.stanford.protege.reasoning.action.ProcessingState;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

/**
 * @author Matthew Horridge, Stanford University, Bio-Medical Informatics Research Group, Date: 06/09/2014
 */
@RunWith(MockitoJUnitRunner.class)
public class KbReasonerProgressMonitor_TestCase {

    public static final String REASONER_NAME = "TestReasonerName";

    public static final String TASK_NAME = "TaskName";

    private KbReasonerProgressMonitor monitor;

    private ArgumentCaptor<ProcessingState> captor;

    @Before
    public void setUp() throws Exception {
        monitor = spy(new KbReasonerProgressMonitor(REASONER_NAME) {
            @Override
            public void stateChanged(ProcessingState state) {

            }
        });
        captor = ArgumentCaptor.forClass(ProcessingState.class);
    }

    @Test
    public void shouldCallStateChangedOnTaskStarted() {
        monitor.reasonerTaskStarted(TASK_NAME);
        verify(monitor, times(1)).stateChanged(captor.capture());
        ProcessingState capturedState = captor.getValue();
        assertThat(capturedState.getReasonerName(), is(REASONER_NAME));
        assertThat(capturedState.getCurrentTaskDescription(), is(TASK_NAME));
    }

    @Test
    public void shouldCallStateChangedOnTaskBusy() {
        monitor.reasonerTaskBusy();
        verify(monitor, times(1)).stateChanged(captor.capture());
        assertThat(captor.getValue().getReasonerName(), is(REASONER_NAME));
    }

    @Test
    public void shouldCallStateChangedOnTaskStopped() {
        monitor.reasonerTaskStopped();
        verify(monitor, times(1)).stateChanged(captor.capture());
        ProcessingState capturedState = captor.getValue();
        assertThat(capturedState.getReasonerName(), is(REASONER_NAME));
        assertThat(capturedState.getCurrentTaskDescription(), is(TASK_NAME));
    }


    @Test
    public void shouldCallStateChangedOnProgressChanged() {
        monitor.reasonerTaskProgressChanged(10, 20);
        verify(monitor, times(1)).stateChanged(captor.capture());
        ProcessingState capturedState = captor.getValue();
        assertThat(capturedState.getReasonerName(), is(REASONER_NAME));
        assertThat(capturedState.getCurrentTaskDescription(), is(TASK_NAME));
        assertThat(capturedState.getPercentageProcessed(), is(50));
    }


}
