 package netty.basesocket;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class SocketClient {

    public static void main(String[] args) {
        
        String host = "127.0.0.1";
        
        int port = 8888;
        
        try {
            Socket socket = new Socket(host, port);
            
            OutputStream outputStream = socket.getOutputStream();
            
            while (true) {
                System.out.println("Client端请输入：");
                Scanner scanner = new Scanner(System.in);
                String str = scanner.next();
                //阻塞的，直到所有字节写进输出流
                outputStream.write(str.getBytes("UTF-8"));
                
//                ObjectOutputStream os  = new ObjectOutputStream(socket.getOutputStream());  
//                List<String> list = new ArrayList<String>();
//                os.writeObject(null);
//                os.flush();
                
//                InputStream inputStream = socket.getInputStream();
//                byte[] bytes = new byte[1024]; 
//                int read = 0;
//                while(read != -1){
//                    read = inputStream.read(bytes);
//                    System.out.println("从服务端获取消息："+new String(bytes, 0, read, "UTF-8"));
//                }
            }
            
        } catch (UnknownHostException e) {
             e.printStackTrace();
        } catch (IOException e) {
             e.printStackTrace();
        }

    }

}
