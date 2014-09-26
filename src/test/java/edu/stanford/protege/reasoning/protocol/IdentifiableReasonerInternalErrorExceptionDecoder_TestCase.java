package edu.stanford.protege.reasoning.protocol;

import edu.stanford.protege.reasoning.ReasonerInternalErrorException;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.util.CharsetUtil;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;

/**
 * @author Matthew Horridge, Stanford University, Bio-Medical Informatics Research Group, Date: 26/09/2014
 */
@RunWith(MockitoJUnitRunner.class)
public class IdentifiableReasonerInternalErrorExceptionDecoder_TestCase {


    private static final int EXPECTED_ID = Integer.MAX_VALUE;

    private static final String EXPECTED_MESSAGE = "Test Message";

    @Mock
    private List<Object> objects;

    @Mock
    private IdentifiableReasonerInternalErrorException exception;

    @Mock
    private ReasonerInternalErrorException cause;

    private IdentifiableReasonerInternalErrorExceptionDecoder decoder;

    private ByteBuf msg;

    @Before
    public void setUp() throws Exception {
        decoder = new IdentifiableReasonerInternalErrorExceptionDecoder();
        when(exception.getId()).thenReturn(EXPECTED_ID);
        when(exception.getException()).thenReturn(cause);
        when(cause.getMessage()).thenReturn(EXPECTED_MESSAGE);
        msg = Unpooled.buffer(1 + 4 + EXPECTED_MESSAGE.length());

        msg.writeByte(ResponseTypeMarker.REASONER_INTERNAL_ERROR_EXCEPTION.getMarker());
        msg.writeInt(EXPECTED_ID);
        msg.writeBytes(EXPECTED_MESSAGE.getBytes(CharsetUtil.UTF_8));
    }

    @Test
    public void shouldDecodeCorrectly() throws Exception {
        decoder.decode(mock(ChannelHandlerContext.class), msg, objects);
        ArgumentCaptor<IdentifiableReasonerInternalErrorException> captor = ArgumentCaptor.forClass(IdentifiableReasonerInternalErrorException.class);
        verify(objects, times(1)).add(captor.capture());
        IdentifiableReasonerInternalErrorException decoded = captor.getValue();
        assertThat(decoded.getId(), is(EXPECTED_ID));
        assertThat(decoded.getException().getMessage(), is(EXPECTED_MESSAGE));
    }

    @Test
    public void shouldNotDecode() throws Exception {
        msg.setByte(0, 0);
        decoder.decode(mock(ChannelHandlerContext.class), msg, objects);
        verify(objects, times(1)).add(msg);
        assertThat(msg.readableBytes(), is(1 + 4 + EXPECTED_MESSAGE.length()));
    }

}
