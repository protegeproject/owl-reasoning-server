package edu.stanford.protege.reasoning.protocol;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;

import java.util.List;

/**
 * @author Matthew Horridge, Stanford University, Bio-Medical Informatics Research Group, Date: 26/09/2014
 */
public class IdentifiableReasonerTimeOutExceptionDecoder extends MessageToMessageDecoder<ByteBuf> {

    @Override
    protected void decode(
            ChannelHandlerContext ctx, ByteBuf msg, List<Object> out) throws Exception {
        if(isReasonerTimeOutException(msg)) {
            msg.readByte();
            int id = msg.readInt();
            out.add(new IdentifiableReasonerTimeOutException(id));
        }
        else {
            msg.retain();
            out.add(msg);
        }
    }

    private boolean isReasonerTimeOutException(ByteBuf msg) {
        return msg.readableBytes() > 0 && msg.getByte(0) == ResponseTypeMarker.REASONER_TIME_OUT_EXCEPTION.getMarker();
    }
}
