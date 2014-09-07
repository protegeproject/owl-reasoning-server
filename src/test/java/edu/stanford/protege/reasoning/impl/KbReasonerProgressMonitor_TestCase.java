package edu.stanford.protege.reasoning.impl;

import edu.stanford.protege.reasoning.KbDigest;
import edu.stanford.protege.reasoning.action.ReasonerState;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
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

    private ArgumentCaptor<ReasonerState> captor;

    @Mock
    private KbDigest kbDigest;

    @Before
    public void setUp() throws Exception {
        monitor = spy(new KbReasonerProgressMonitor(REASONER_NAME, kbDigest) {
            @Override
            public void stateChanged(ReasonerState state) {

            }
        });
        captor = ArgumentCaptor.forClass(ReasonerState.class);
    }

    @Test
    public void shouldCallStateChangedOnTaskStarted() {
        monitor.reasonerTaskStarted(TASK_NAME);
        verify(monitor, times(1)).stateChanged(captor.capture());
        ReasonerState capturedState = captor.getValue();
        assertThat(capturedState.getReasonerName(), is(REASONER_NAME));
        assertThat(capturedState.getStateDescription(), is(TASK_NAME));
    }

    @Test
    public void shouldCallStateChangedOnTaskBusy() {
        monitor.reasonerTaskBusy();
        verify(monitor, times(1)).stateChanged(captor.capture());
        assertThat(captor.getValue().getReasonerName(), is(REASONER_NAME));
    }

    @Test
    public void shouldCallStateChangedOnTaskStopped() {
        monitor.reasonerTaskStarted(TASK_NAME);
        monitor.reasonerTaskStopped();
        verify(monitor, times(2)).stateChanged(captor.capture());
        ReasonerState capturedState = captor.getValue();
        assertThat(capturedState.getReasonerName(), is(REASONER_NAME));
        assertThat(capturedState.getStateDescription(), is(TASK_NAME));
    }


    @Test
    public void shouldCallStateChangedOnProgressChanged() {
        monitor.reasonerTaskStarted(TASK_NAME);
        monitor.reasonerTaskProgressChanged(10, 20);
        verify(monitor, times(2)).stateChanged(captor.capture());
        ReasonerState capturedState = captor.getValue();
        assertThat(capturedState.getReasonerName(), is(REASONER_NAME));
        assertThat(capturedState.getStateDescription(), is(TASK_NAME));
        assertThat(capturedState.getProgress().get().getPercentageValue(), is(50));
    }


}
