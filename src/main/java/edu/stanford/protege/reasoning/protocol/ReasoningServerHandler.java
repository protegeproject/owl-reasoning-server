package edu.stanford.protege.reasoning.protocol;

import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import edu.stanford.protege.reasoning.ReasonerInternalErrorException;
import edu.stanford.protege.reasoning.ReasonerTimeOutException;
import edu.stanford.protege.reasoning.ReasoningService;
import edu.stanford.protege.reasoning.Response;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @author Matthew Horridge, Stanford University, Bio-Medical Informatics Research Group, Date: 29/08/2014
 */
public class ReasoningServerHandler extends SimpleChannelInboundHandler<IdentifiableAction> {

    private ReasoningService reasoningService;

    public ReasoningServerHandler(ReasoningService reasoningService) {
        this.reasoningService = reasoningService;
    }

    @SuppressWarnings("unchecked")
    @Override
    protected void messageReceived(final ChannelHandlerContext ctx, final IdentifiableAction msg) throws Exception {
        ListenableFuture<Response> response = reasoningService.execute(msg.getAction());

        Futures.addCallback(response, new FutureCallback<Response>() {
            @Override
            public void onSuccess(Response result) {
                ctx.writeAndFlush(new IdentifiableResponse(msg.getId(), result));
            }

            @Override
            public void onFailure(Throwable t) {
                System.err.println("An error occurred: " + t.getMessage());
                if(t instanceof ReasonerInternalErrorException) {
                    ctx.writeAndFlush(new IdentifiableReasonerInternalErrorException(
                            msg.getId(),
                            (ReasonerInternalErrorException) t));
                }
                else if(t instanceof ReasonerTimeOutException) {
                    ctx.writeAndFlush(new IdentifiableReasonerTimeOutException(msg.getId()));
                }
                else {
                    throw new RuntimeException("An unexpected exception (i.e. not a ReasonerInternalErrorException" +
                                                       " or a ReasonerTimeOutException occurred.", t);
                }
            }
        });
    }

}
