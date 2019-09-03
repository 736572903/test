 package netty.netty;


import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LineBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;

public class TimeClient {
     
     public void connect(int port, String host) throws Exception{
         
         //配置客户端NIO线程组
         EventLoopGroup group = new NioEventLoopGroup();
         
         try {
             
            Bootstrap b = new Bootstrap();

            b.group(group)
                .channel(NioSocketChannel.class)
                /**
                 *  TCP_NODELAY就是用于启用或关闭Nagle算法。
                 *  如果要求高实时性，有数据发送时就马上发送，就将该选项设置为true关闭Nagle算法；
                 *  如果要减少发送次数减少网络交互，就设置为false等累积一定大小后再发送。默认为false。
                 */
                .option(ChannelOption.TCP_NODELAY, true)
                .handler(new ChannelInitializer<SocketChannel>() {

                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
                        ch.pipeline().addLast(new LineBasedFrameDecoder(1024));
                        ch.pipeline().addLast(new StringDecoder());
                        ch.pipeline().addLast(new TimeClientHandler());
                        
                }
                });
             
            //发起异步连接操作
            ChannelFuture f = b.connect(host, port).sync();
            
            //等待客户端链路关闭
            f.channel().closeFuture().sync();
            
        } finally {
            //释放NIO线程组
            group.shutdownGracefully();
        }
         
     }
     
     public static void main(String[] args) throws Exception {
        
         TimeClient client = new TimeClient();
         client.connect(8888, "127.0.0.1");
    }

}
