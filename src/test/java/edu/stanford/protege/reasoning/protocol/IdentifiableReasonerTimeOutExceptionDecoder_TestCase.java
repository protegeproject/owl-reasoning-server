package edu.stanford.protege.reasoning.protocol;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
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
public class IdentifiableReasonerTimeOutExceptionDecoder_TestCase {

    private static final int EXPECTED_ID = Integer.MAX_VALUE;

    @Mock
    private List<Object> objects;

    @Mock
    private IdentifiableReasonerTimeOutException exception;

    private IdentifiableReasonerTimeOutExceptionDecoder decoder;

    private ByteBuf msg;

    @Before
    public void setUp() throws Exception {
        decoder = new IdentifiableReasonerTimeOutExceptionDecoder();
        when(exception.getId()).thenReturn(EXPECTED_ID);
        msg = Unpooled.buffer(5);
        msg.writeByte(ResponseTypeMarker.REASONER_TIME_OUT_EXCEPTION.getMarker());
        msg.writeInt(EXPECTED_ID);
    }

    @Test
    public void shouldDecodeCorrectly() throws Exception {
        decoder.decode(mock(ChannelHandlerContext.class), msg, objects);
        ArgumentCaptor<IdentifiableReasonerTimeOutException> captor = ArgumentCaptor.forClass(IdentifiableReasonerTimeOutException.class);
        verify(objects, times(1)).add(captor.capture());
        IdentifiableReasonerTimeOutException decoded = captor.getValue();
        assertThat(decoded.getId(), is(EXPECTED_ID));
        assertThat(msg.readableBytes(), is(0));
    }

    @Test
    public void shouldNotDecode() throws Exception {
        msg.setByte(0, 0);
        decoder.decode(mock(ChannelHandlerContext.class), msg, objects);
        verify(objects, times(1)).add(msg);
        assertThat(msg.readableBytes(), is(5));
    }

}
