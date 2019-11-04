package netty.netty;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.FixedLengthFrameDecoder;
import io.netty.handler.codec.LineBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;

public class ChildChannelHandler extends ChannelInitializer<Channel> {

    @Override
    protected void initChannel(Channel ch) throws Exception {

        /**
         * 解决传统nio的粘包和拆包问题
         * 工作原理是它依次遍历ByteBuf中的可读字节，判断看是否有"\n"或者"\r\n"，
         * 如果有,就以此位置为结束位置。它是以换行符为结束标志的解码器，同时支持配置单行的最大长度
         * 如果连续读到最大长度后仍然没有发现换行符，就会抛出异常
         */
        ch.pipeline().addLast(new LineBasedFrameDecoder(1024));

        /**
         * 定制分隔符
         */
//        ByteBuf delimiter = Unpooled.copiedBuffer("$_".getBytes());
//        ch.pipeline().addLast(new DelimiterBasedFrameDecoder(1024, delimiter));

        /**
         * 每次读取定长
         */
        ch.pipeline().addLast(new FixedLengthFrameDecoder(20));

        //字符编码 UTF-8
        ch.pipeline().addLast(new StringDecoder());

        ch.pipeline().addLast(new TimeServerHandler());

    }

}
