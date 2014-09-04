package edu.stanford.protege.reasoning.protocol;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;

import java.util.List;

/**
 * @author Matthew Horridge, Stanford University, Bio-Medical Informatics Research Group, Date: 29/08/2014
 */
public class IdentifiableActionEncoder extends MessageToMessageEncoder<IdentifiableAction> {

    private ReasoningServerCodecRegistry registry;

    public IdentifiableActionEncoder(ReasoningServerCodecRegistry registry) {
        this.registry = registry;
    }

    @Override
    protected void encode(ChannelHandlerContext ctx, IdentifiableAction msg, List<Object> out) throws Exception {
        ReasoningServerCodec codec = registry.getCodec(msg.getAction());
        int id = msg.getId();
        int marker = codec.getFrameMarker();
        byte [] bytes = codec.encodeAction(msg.getAction());
        ByteBuf byteBuf = Unpooled.buffer(9 + bytes.length);
        byteBuf.writeByte(0);
        byteBuf.writeInt(id);
        byteBuf.writeInt(marker);
        byteBuf.writeBytes(bytes);
        out.add(byteBuf);
    }
}
