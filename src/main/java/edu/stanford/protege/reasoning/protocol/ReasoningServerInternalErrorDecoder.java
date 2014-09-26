package edu.stanford.protege.reasoning.protocol;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;
import io.netty.util.CharsetUtil;

import java.util.List;

/**
 * @author Matthew Horridge, Stanford University, Bio-Medical Informatics Research Group, Date: 03/09/2014
 */
public class ReasoningServerInternalErrorDecoder extends MessageToMessageDecoder<ByteBuf> {

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf msg, List<Object> out) throws Exception {
        if(msg.readableBytes() > 0 && msg.getByte(0) == 1) {
            msg.readByte();
            int id = msg.readInt();
            String errorMessage = msg.toString(msg.readerIndex(), msg.readableBytes(), CharsetUtil.UTF_8);
            out.add(new ReasoningServerInternalError(id, errorMessage));
        }
        else {
            msg.retain();
            out.add(msg);
        }
    }
}
