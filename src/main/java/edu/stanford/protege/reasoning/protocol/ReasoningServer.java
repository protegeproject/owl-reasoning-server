package edu.stanford.protege.reasoning.protocol;

import com.google.inject.Inject;
import edu.stanford.protege.reasoning.ReasoningService;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;

import java.net.InetSocketAddress;

/**
 * @author Matthew Horridge, Stanford University, Bio-Medical Informatics Research Group, Date: 27/08/2014
 */
public class ReasoningServer {

    private final ReasoningService reasoningService;

    private final ReasoningServerCodecRegistry codecRegistry;

    private final EventLoopGroup eventLoopGroup;

    @Inject
    public ReasoningServer(ReasoningService reasoningService, ReasoningServerCodecRegistry codecRegistry) {
        this.reasoningService = reasoningService;
        this.codecRegistry = codecRegistry;
        this.eventLoopGroup = new NioEventLoopGroup();
    }

    public synchronized void start(InetSocketAddress socketAddress) throws Exception {
        ServerBootstrap serverBootstrap = new ServerBootstrap();
        serverBootstrap
                .localAddress(socketAddress)
                .group(eventLoopGroup)
                .channel(NioServerSocketChannel.class)
                .option(ChannelOption.SO_KEEPALIVE, true)
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
                        ChannelPipeline pipeline = ch.pipeline();
//                        pipeline.addLast(new LoggingHandler(LogLevel.INFO));
                        pipeline.addLast(new LengthFieldBasedFrameDecoder(Integer.MAX_VALUE, 0, 4, 0, 4));
                        pipeline.addLast(new LengthFieldPrepender(4));
                        pipeline.addLast(new ReasoningServerErrorEncoder());
                        pipeline.addLast(new IdentifiableActionDecoder(codecRegistry));
                        pipeline.addLast(new IdentifiableResponseEncoder(codecRegistry));
                        pipeline.addLast(new ReasoningServerHandler(reasoningService));
                    }
                });

        ChannelFuture future = serverBootstrap.bind().sync();
        future.addListener(new GenericFutureListener<Future<Void>>() {
            @Override
            public void operationComplete(Future<Void> future) throws Exception {
                System.out.println("Reasoning server running");
            }
        });
    }

    public synchronized void stop() throws Exception {
        eventLoopGroup.shutdownGracefully().sync();
    }
}
