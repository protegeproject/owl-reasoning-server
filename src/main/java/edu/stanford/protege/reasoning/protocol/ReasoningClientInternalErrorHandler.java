package edu.stanford.protege.reasoning.protocol;

import com.google.common.util.concurrent.SettableFuture;
import edu.stanford.protege.reasoning.InternalReasonerErrorException;
import edu.stanford.protege.reasoning.Response;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @author Matthew Horridge, Stanford University, Bio-Medical Informatics Research Group, Date: 03/09/2014
 */
public class ReasoningClientInternalErrorHandler extends SimpleChannelInboundHandler<ReasoningServerInternalError> {

    private ReasoningClientHandler.Id2FutureMapper id2FutureMapper;

    public ReasoningClientInternalErrorHandler(ReasoningClientHandler.Id2FutureMapper id2FutureMapper) {
        this.id2FutureMapper = id2FutureMapper;
    }

    @Override
    protected void messageReceived(ChannelHandlerContext ctx, ReasoningServerInternalError msg) throws Exception {
        SettableFuture<Response> future = id2FutureMapper.consumeFuture(msg.getId());
        future.setException(new InternalReasonerErrorException(msg.getMessage()));
    }
}
