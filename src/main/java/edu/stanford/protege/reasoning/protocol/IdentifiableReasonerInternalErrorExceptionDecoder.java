package edu.stanford.protege.reasoning.protocol;

import edu.stanford.protege.reasoning.ReasonerInternalErrorException;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;
import io.netty.util.CharsetUtil;

import java.util.List;

/**
 * @author Matthew Horridge, Stanford University, Bio-Medical Informatics Research Group, Date: 03/09/2014
 */
public class IdentifiableReasonerInternalErrorExceptionDecoder extends MessageToMessageDecoder<ByteBuf> {

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf msg, List<Object> out) throws Exception {
        if(msg.readableBytes() > 0 && msg.getByte(0) == ResponseTypeMarker.REASONER_INTERNAL_ERROR_EXCEPTION.getMarker()) {
            msg.readByte();
            int id = msg.readInt();
            String errorMessage = msg.toString(msg.readerIndex(), msg.readableBytes(), CharsetUtil.UTF_8);
            out.add(new IdentifiableReasonerInternalErrorException(id, new ReasonerInternalErrorException(errorMessage)));
        }
        else {
            msg.retain();
            out.add(msg);
        }
    }
}
