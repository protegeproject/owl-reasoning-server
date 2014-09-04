package edu.stanford.protege.reasoning.protocol;

import com.google.common.collect.Maps;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.SettableFuture;
import com.google.inject.Inject;
import edu.stanford.protege.reasoning.ReasoningService;
import edu.stanford.protege.reasoning.Response;
import edu.stanford.protege.reasoning.Action;
import edu.stanford.protege.reasoning.action.ActionHandler;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
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


    @Inject
    public ReasoningClient(ReasoningServerCodecRegistry codecRegistry) {
        this.codecRegistry = codecRegistry;
        this.eventLoopGroup = new NioEventLoopGroup();
        id2FutureMapper = new ReasoningClientHandler.Id2FutureMapper() {
            @Override
            @SuppressWarnings("unchecked")
            public <R extends Response> SettableFuture<R> consumeFuture(Integer id) {
                return (SettableFuture<R>) id2FutureMap.remove(id);
            }
        };
    }

    public void connect(InetSocketAddress inetSocketAddress) throws Exception {
        Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(eventLoopGroup)
                .channel(NioSocketChannel.class)
                .remoteAddress(inetSocketAddress)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel socketChannel) throws Exception {
                        ChannelPipeline pipeline = socketChannel.pipeline();
//                        pipeline.addLast(new LoggingHandler(LogLevel.INFO));
                        pipeline.addLast(new LengthFieldPrepender(4));
                        pipeline.addLast(new LengthFieldBasedFrameDecoder(Integer.MAX_VALUE, 0, 4, 0, 4));
                        pipeline.addLast(new ReasoningServerErrorDecoder());
                        pipeline.addLast(new IdentifiableActionEncoder(codecRegistry));
                        pipeline.addLast(new IdentifiableResponseDecoder(codecRegistry));
                        pipeline.addLast(new ReasoningClientErrorHandler(id2FutureMapper));
                        pipeline.addLast(new ReasoningClientHandler(id2FutureMapper));
                    }
                });
        channelFuture = bootstrap.connect();
        channelFuture.sync();
    }

    public void disconnect() throws Exception {
        channelFuture.channel().close().sync();
    }

    @Override
    public <A extends Action<R, H>, R extends Response, H extends ActionHandler> ListenableFuture<R> execute(A action) {
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
