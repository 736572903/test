package netty.basesocket;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SocketServer {

    /**
     * 传统，接收消息就关闭------------->加上while(true)，会阻塞，同步--------------->提高并发使用线程
     * 使用线程池提高并发能力
     * 1.不需要频繁销毁和新建线程
     * 2.防止线程过多引起CPU飙高
     * <p>
     * 但是：
     * 连接客户端的数量过多，线程池核心线程池满了队列阻塞
     * (1)至少两处阻塞点(等待客户端连接，等待客户端发送信息)
     * (2)单线程下只能有一个客户端连接
     * (3)使用线程池可以连接多个客户端，但是十分消耗性能。
     * 每进来一个新客户端的请求，就需要分配一个线程，当客户端数量非常多而且一直保持长链接的话，资源消耗非常巨大
     * 如果要解决，请使用NIO
     * <p>
     * 它存在两个阻塞点：“等待客户端连接”，“等待客户端发送信息”
     *
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {


        ExecutorService executor = Executors.newFixedThreadPool(10);

        int port = 8888;

        ServerSocket serverSocket = new ServerSocket(port);

        System.out.println("服务端启动成功☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆");

        while (true) {

            Socket socket = serverSocket.accept();

            System.out.println("出现一个新的客户端连接进来------------");

            executor.execute(new HandlerThread(socket));

        }

    }

}
