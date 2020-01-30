package org.eulerframework.demo.netty.protobuf;

import com.google.protobuf.MessageLite;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.protobuf.ProtobufDecoder;
import io.netty.handler.codec.protobuf.ProtobufEncoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32FrameDecoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32LengthFieldPrepender;

public class ProtoBufInitializer<T extends MessageLite> extends ChannelInitializer<Channel> {
    private final T prototype;
    private final SimpleChannelInboundHandler<T> objectHandler;

    public ProtoBufInitializer(T prototype, SimpleChannelInboundHandler<T> objectHandler) {
        this.prototype = prototype;
        this.objectHandler = objectHandler;
    }

    @Override
    protected void initChannel(Channel ch) throws Exception {
        ch.pipeline().addLast(new ProtobufVarint32FrameDecoder())
                .addLast(new ProtobufVarint32LengthFieldPrepender())
                .addLast(new ProtobufDecoder(this.prototype))
                .addLast(new ProtobufEncoder())
                .addLast(this.objectHandler);
    }
}
