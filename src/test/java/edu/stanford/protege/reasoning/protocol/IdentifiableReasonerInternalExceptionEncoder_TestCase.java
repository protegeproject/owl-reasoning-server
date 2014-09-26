package edu.stanford.protege.reasoning.protocol;

import edu.stanford.protege.reasoning.ReasonerInternalErrorException;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;

/**
 * @author Matthew Horridge, Stanford University, Bio-Medical Informatics Research Group, Date: 26/09/2014
 */
@RunWith(MockitoJUnitRunner.class)
public class IdentifiableReasonerInternalExceptionEncoder_TestCase {

    private static final int EXPECTED_ID = Integer.MAX_VALUE;

    public static final String EXPECTED_MESSAGE = "Test message";

    private IdentifiableReasonerInternalErrorExceptionEncoder encoder;

    @Mock
    private List<Object> objects;

    @Mock
    private IdentifiableReasonerInternalErrorException exception;

    @Mock
    private ReasonerInternalErrorException cause;

    @Before
    public void setUp() throws Exception {
        encoder = new IdentifiableReasonerInternalErrorExceptionEncoder();
        when(exception.getId()).thenReturn(EXPECTED_ID);
        when(exception.getException()).thenReturn(cause);
        when(cause.getMessage()).thenReturn(EXPECTED_MESSAGE);
    }

    @Test
    public void shouldEncodeCorrectly() throws Exception {
        encoder.encode(mock(ChannelHandlerContext.class), exception, objects);
        ArgumentCaptor<ByteBuf> captor = ArgumentCaptor.forClass(ByteBuf.class);
        verify(objects, times(1)).add(captor.capture());
        ByteBuf encoded = captor.getValue();
        encoded.resetReaderIndex();
        assertThat(encoded.readableBytes(), is(1 + 4 + EXPECTED_MESSAGE.length()));
        assertThat(encoded.readByte(), is(ResponseTypeMarker.REASONER_INTERNAL_ERROR_EXCEPTION.getMarker()));
        assertThat(encoded.readInt(), is(EXPECTED_ID));
        assertThat(encoded.readableBytes(), is(EXPECTED_MESSAGE.length()));
    }
}
