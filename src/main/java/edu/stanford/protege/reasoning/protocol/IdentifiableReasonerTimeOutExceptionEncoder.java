package edu.stanford.protege.reasoning.protocol;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;

import java.util.List;

/**
 * @author Matthew Horridge, Stanford University, Bio-Medical Informatics Research Group, Date: 26/09/2014
 */
public class IdentifiableReasonerTimeOutExceptionEncoder extends MessageToMessageEncoder<IdentifiableReasonerTimeOutException> {

    @Override
    protected void encode(
            ChannelHandlerContext ctx, IdentifiableReasonerTimeOutException msg, List<Object> out) throws Exception {
        ByteBuf buffer = Unpooled.buffer(1);
        buffer.writeByte(ResponseTypeMarker.REASONER_TIME_OUT_EXCEPTION.getMarker());
        buffer.writeInt(msg.getId());
        out.add(buffer);
    }
}
