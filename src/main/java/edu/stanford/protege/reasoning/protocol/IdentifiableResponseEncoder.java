package edu.stanford.protege.reasoning.protocol;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;

import java.util.List;

/**
 * @author Matthew Horridge, Stanford University, Bio-Medical Informatics Research Group, Date: 29/08/2014
 */
public class IdentifiableResponseEncoder extends MessageToMessageEncoder<IdentifiableResponse> {

    private ReasoningServerCodecRegistry registry;

    public IdentifiableResponseEncoder(ReasoningServerCodecRegistry registry) {
        this.registry = registry;
    }

    @Override
    protected void encode(ChannelHandlerContext ctx, IdentifiableResponse msg, List<Object> out) throws Exception {
        ReasoningServerCodec codec = registry.getCodec(msg.getResponse());
        byte [] bytes = codec.encodeResponse(msg.getResponse());
        ByteBuf byteBuf = Unpooled.buffer(9 + bytes.length);
        byteBuf.writeByte(0);
        byteBuf.writeInt(msg.getId());
        byteBuf.writeInt(codec.getFrameMarker());
        byteBuf.writeBytes(bytes);
        out.add(byteBuf);
    }
}
