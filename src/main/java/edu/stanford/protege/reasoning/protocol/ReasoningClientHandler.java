package edu.stanford.protege.reasoning.protocol;

import com.google.common.util.concurrent.SettableFuture;
import edu.stanford.protege.reasoning.Response;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @author Matthew Horridge, Stanford University, Bio-Medical Informatics Research Group, Date: 29/08/2014
 */
public class ReasoningClientHandler extends SimpleChannelInboundHandler<IdentifiableResponse> {

    private Id2FutureMapper id2FutureMapper;

    public ReasoningClientHandler(Id2FutureMapper id2FutureMapper) {
        this.id2FutureMapper = id2FutureMapper;
    }

    @Override
    protected void messageReceived(ChannelHandlerContext ctx, IdentifiableResponse msg) throws Exception {
        SettableFuture<Response> future = getFuture(msg.getId());
        future.set(msg.getResponse());
    }

    @SuppressWarnings("unchecked")
    private <R extends Response> SettableFuture<R> getFuture(Integer id) {
        SettableFuture<? extends Response> future = id2FutureMapper.consumeFuture(id);
        if(future == null) {
            throw new IllegalStateException("Future does not exist for response");
        }
        return (SettableFuture<R>) future;
    }



    public static interface Id2FutureMapper {
        <R extends Response> SettableFuture<R> consumeFuture(Integer id);
    }
}
