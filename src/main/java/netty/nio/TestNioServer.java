package netty.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

public class TestNioServer {

    //选择器，用于管理多个网络连接
    private static Selector selector;

    public static void main(String[] args) throws IOException {

        init();

        listen();

    }

    private static void init() throws IOException {
        //相当于饭店
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();

        //设置为非阻塞
        serverSocketChannel.configureBlocking(false);
        //绑定端口号
        serverSocketChannel.socket().bind(new InetSocketAddress(8888));

        //初始化选择器，服务员
        selector = Selector.open();

        //将服务员注册到饭店，该服务员对应这个饭店,
        //给这个饭店注册accept事件，当有事件到达以后，selector.select()会返回，否则一直阻塞,
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);

    }

    private static void listen() throws IOException {

        while (true) {
            //有事件来了，才会通过这个方法，有返回值，否则一直阻塞
            selector.select();

            //遍历选择器的事件，看看客人需要什么，服务员应该去做
            Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();

            while (iterator.hasNext()) {

                //取出一个具体的事件
                SelectionKey selectionKey = iterator.next();

                handle(selectionKey);

                iterator.remove();

            }

        }


    }

    private static void handle(SelectionKey selectionKey) throws IOException {

        //客户端请求连接，获取连接事件
        if (selectionKey.isAcceptable()) {
            handleAccept(selectionKey);
        } else if (selectionKey.isReadable()) {//获取可读事件
            handleRead(selectionKey);
        }

    }

    private static void handleAccept(SelectionKey selectionKey) throws IOException {

        ServerSocketChannel channel = (ServerSocketChannel) selectionKey.channel();

        //从某一个客户端接收请求连接信息，获取可接受连接的通道
        SocketChannel socketChannel = channel.accept();

        System.out.println("新的客户端连接");

        //设置非阻塞
        socketChannel.configureBlocking(false);

        //返回客户端信息
        socketChannel.write(ByteBuffer.wrap("服务端收到客户端信息".getBytes()));

        //在与客户端连接成功后，为了可以接收到客户端消息，赋予此连接读的权限
        socketChannel.register(selector, SelectionKey.OP_READ);

    }

    private static void handleRead(SelectionKey selectionKey) throws IOException {

        //得到发生可读的通道
        SocketChannel channel = (SocketChannel) selectionKey.channel();

        ByteBuffer dst = ByteBuffer.allocate(1024000);

        channel.read(dst);

        byte[] data = dst.array();

        System.out.println("客户端收到数据：" + new String(data));

        channel.write(ByteBuffer.wrap(("服务器最棒,收到信息：" + new String(data)).getBytes()));


    }

}
