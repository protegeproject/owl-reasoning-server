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
public class IdentifiableReasonerInternalErrorExceptionEncoder extends MessageToMessageEncoder<IdentifiableReasonerInternalErrorException> {

    @Override
    protected void encode(ChannelHandlerContext ctx, IdentifiableReasonerInternalErrorException msg, List<Object> out) throws Exception {
        byte[] errorMessageBytes = msg.getException().getMessage().getBytes(CharsetUtil.UTF_8);
        ByteBuf buffer = Unpooled.buffer(5 + errorMessageBytes.length);
        buffer.writeByte(ResponseTypeMarker.REASONER_INTERNAL_ERROR_EXCEPTION.getMarker());
        buffer.writeInt(msg.getId());
        buffer.writeBytes(errorMessageBytes);
        out.add(buffer);
    }
}
