package netty.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

// netty时间服务器 服务端
public class TimeServer {
    
    public void bind(int port) throws Exception{
        
        //配置服务端的NIO线程组
        /**
         * NioEventLoopGroup是个线程组，它包含了一组NIO线程，专门用于网络事件的处理
         * 实际上它们就是Reactor线程组（参考selector）
         * 这里创建两个的原因是
         *  一个用于服务端接收客户端的连接，另一个用于进行SocketChannel的网络读写
         * 
         */
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        
        try {
            /**
             * ServerBootstrap 是netty用于启动 NIO服务端的辅助启动类
             * 目的是降低服务端的开发复杂度
             */
            ServerBootstrap b = new ServerBootstrap();
            b.group(bossGroup, workerGroup)
                //NioServerSocketChannel的功能对应于NIO里的ServerSocketChannel
                .channel(NioServerSocketChannel.class)
                /**
                 * BACKLOG用于构造服务端套接字ServerSocket对象，
                 * 标识当服务器请求处理线程全满时，用于临时存放已完成三次握手的请求的队列的最大长度。
                 * 如果未设置或所设置的值小于1，Java将使用默认值50
                 */
                .option(ChannelOption.SO_BACKLOG, 1024)
                //绑定处理类，主要用于处理网络I/O事件
                .childHandler(new ChildChannelHandler());

            // 绑定端口，同步等待成功
            /**
             * 调用bind方法绑定监听端口，调用阻塞方法sync等待绑定操作完成
             * 完成之后netty会返回一个channelFuture，主要用于异步操作的通知回调
             */
            
            ChannelFuture f = b.bind(port).sync();
            
            //等待服务器监听端口关闭
            f.channel().closeFuture().sync();
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
        
    }
    
    public static void main(String[] args) throws Exception {
        new TimeServer().bind(8888);
    }
    
}
