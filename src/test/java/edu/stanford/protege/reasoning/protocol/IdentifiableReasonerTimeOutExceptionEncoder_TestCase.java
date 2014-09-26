package edu.stanford.protege.reasoning.protocol;

import io.netty.buffer.ByteBuf;
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
public class IdentifiableReasonerTimeOutExceptionEncoder_TestCase {


    private static final int EXPECTED_ID = Integer.MAX_VALUE;

    private IdentifiableReasonerTimeOutExceptionEncoder encoder;

    @Mock
    private List<Object> objects;

    @Mock
    private IdentifiableReasonerTimeOutException exception;

    @Before
    public void setUp() throws Exception {
        encoder = new IdentifiableReasonerTimeOutExceptionEncoder();
        when(exception.getId()).thenReturn(EXPECTED_ID);
    }

    @Test
    public void shouldEncodeCorrectly() throws Exception {
        encoder.encode(mock(ChannelHandlerContext.class), exception, objects);
        ArgumentCaptor<ByteBuf> captor = ArgumentCaptor.forClass(ByteBuf.class);
        verify(objects, times(1)).add(captor.capture());
        ByteBuf encoded = captor.getValue();
        encoded.resetReaderIndex();
        assertThat(encoded.readableBytes(), is(5));
        assertThat(encoded.readByte(), is(ResponseTypeMarker.REASONER_TIME_OUT_EXCEPTION.getMarker()));
        assertThat(encoded.readInt(), is(EXPECTED_ID));
        assertThat(encoded.readableBytes(), is(0));
    }
}
