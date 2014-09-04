package edu.stanford.protege.reasoning.protocol;

import edu.stanford.protege.reasoning.Action;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;

import java.util.List;

/**
 * @author Matthew Horridge, Stanford University, Bio-Medical Informatics Research Group, Date: 29/08/2014
 */
public class IdentifiableActionDecoder extends MessageToMessageDecoder<ByteBuf> {

    private ReasoningServerCodecRegistry registry;

    public IdentifiableActionDecoder(ReasoningServerCodecRegistry registry) {
        this.registry = registry;
    }

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf msg, List<Object> out) throws Exception {
        if(msg.getByte(0) == 0) {
            msg.readByte();
            int id = msg.readInt();
            int marker = msg.readInt();
            byte [] bytes = new byte[msg.readableBytes()];
            msg.readBytes(bytes);
            ReasoningServerCodec codec = registry.getCodec(marker);
            Action action = codec.decodeAction(bytes);
            out.add(new IdentifiableAction(id, action));
        }
    }
}
