package edu.stanford.protege.reasoning.protocol;

import com.google.common.collect.Maps;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.SettableFuture;
import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;
import edu.stanford.protege.reasoning.ReasoningService;
import edu.stanford.protege.reasoning.Response;
import edu.stanford.protege.reasoning.Action;
import edu.stanford.protege.reasoning.action.ActionHandler;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;

import java.net.InetSocketAddress;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author Matthew Horridge, Stanford University, Bio-Medical Informatics Research Group, Date: 29/08/2014
 */
public class ReasoningClient implements ReasoningService {

    private ReasoningServerCodecRegistry codecRegistry;

    private final EventLoopGroup eventLoopGroup;

    private ChannelFuture channelFuture;

    private Map<Integer, SettableFuture<? extends Response>> id2FutureMap = Maps.newConcurrentMap();

    private final AtomicInteger atomicInteger = new AtomicInteger();

    private ReasoningClientHandler.Id2FutureMapper id2FutureMapper;

    private final InetSocketAddress inetSocketAddress;


    @Inject
    public ReasoningClient(@Assisted InetSocketAddress inetSocketAddress, ReasoningServerCodecRegistry codecRegistry) {
        this.codecRegistry = codecRegistry;
        this.inetSocketAddress = inetSocketAddress;
        this.eventLoopGroup = new NioEventLoopGroup();
        id2FutureMapper = new ReasoningClientHandler.Id2FutureMapper() {
            @Override
            @SuppressWarnings("unchecked")
            public <R extends Response> SettableFuture<R> consumeFuture(Integer id) {
                return (SettableFuture<R>) id2FutureMap.remove(id);
            }
        };
    }

    public synchronized void connect() {
        try {
            final Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(eventLoopGroup)
                    .channel(NioSocketChannel.class)
                    .remoteAddress(inetSocketAddress)
                    .option(ChannelOption.SO_KEEPALIVE, true)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            ChannelPipeline pipeline = socketChannel.pipeline();
                            pipeline.addLast(new LengthFieldPrepender(4));
                            pipeline.addLast(new LengthFieldBasedFrameDecoder(Integer.MAX_VALUE, 0, 4, 0, 4));
                            pipeline.addLast(new ReasoningServerInternalErrorDecoder());
                            pipeline.addLast(new IdentifiableActionEncoder(codecRegistry));
                            pipeline.addLast(new IdentifiableResponseDecoder(codecRegistry));
                            pipeline.addLast(new ReasoningClientInternalErrorHandler(id2FutureMapper));
                            pipeline.addLast(new ReasoningClientHandler(id2FutureMapper));
                        }
                    });
            final ChannelFuture future = bootstrap.connect();
            future.sync();
            channelFuture = future;
            final ChannelFuture closeFuture = channelFuture.channel().closeFuture();
            closeFuture.addListener(new ChannelFutureListener() {
                @Override
                public void operationComplete(ChannelFuture future) throws Exception {
                    channelFuture = null;
                }
            });
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private synchronized void connectIfNecessary() {
        if(channelFuture == null) {
            connect();
        }
    }

    public void disconnect() throws InterruptedException {
        if(channelFuture == null) {
            return;
        }
        channelFuture.channel().close().sync();
    }

    @Override
    public <A extends Action<R, H>, R extends Response, H extends ActionHandler> ListenableFuture<R> execute(A action) {
        connectIfNecessary();
        Integer id = atomicInteger.getAndIncrement();
            SettableFuture<R> future = SettableFuture.create();
            id2FutureMap.put(id, future);
            channelFuture.channel().writeAndFlush(new IdentifiableAction(id, action));
            return future;
    }



    @Override
    public void shutDown() {
        try {
            disconnect();
            eventLoopGroup.shutdownGracefully();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
