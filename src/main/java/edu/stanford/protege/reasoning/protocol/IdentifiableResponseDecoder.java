package edu.stanford.protege.reasoning.protocol;

import edu.stanford.protege.reasoning.Response;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;

import java.util.List;

/**
 * @author Matthew Horridge, Stanford University, Bio-Medical Informatics Research Group, Date: 29/08/2014
 */
public class IdentifiableResponseDecoder extends MessageToMessageDecoder<ByteBuf> {

    private ReasoningServerCodecRegistry registry;

    public IdentifiableResponseDecoder(ReasoningServerCodecRegistry registry) {
        this.registry = registry;
    }

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf msg, List<Object> out) throws Exception {
        int id = msg.readInt();
        int marker = msg.readInt();
        ReasoningServerCodec codec = registry.getCodec(marker);
        byte [] bytes = new byte[msg.readableBytes()];
        msg.readBytes(bytes);
        Response response = codec.decodeResponse(bytes);
        out.add(new IdentifiableResponse(id, response));
    }
}
