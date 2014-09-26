package edu.stanford.protege.reasoning.protocol;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;
import io.netty.util.CharsetUtil;

import java.util.List;

/**
 * @author Matthew Horridge, Stanford University, Bio-Medical Informatics Research Group, Date: 03/09/2014
 */
public class ReasoningServerInternalErrorEncoder extends MessageToMessageEncoder<ReasoningServerInternalError> {

    @Override
    protected void encode(ChannelHandlerContext ctx, ReasoningServerInternalError msg, List<Object> out) throws Exception {
        byte[] errorMessageBytes = msg.getMessage().getBytes(CharsetUtil.UTF_8);
        ByteBuf buffer = Unpooled.buffer(5 + errorMessageBytes.length);
        buffer.writeByte(1);
        buffer.writeInt(msg.getId());
        buffer.writeBytes(errorMessageBytes);
        out.add(buffer);
    }
}
