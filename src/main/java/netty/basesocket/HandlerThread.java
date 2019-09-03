package netty.basesocket;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.net.Socket;

public class HandlerThread implements Runnable {
    
    private Socket socket;
    
    
    public HandlerThread(){
        
    }
    
    public HandlerThread(Socket socket){
        this.socket = socket;
    }

    public void run() {
        
        byte[] bytes = new byte[1024]; 
        
        try {
            InputStream inputStream = socket.getInputStream();
            
//            int len =0;
//            StringBuilder sb = new StringBuilder();
//            while ((len = inputStream.read(bytes)) != -1) {
//                // 注意指定编码格式，发送方和接收方一定要统一，建议使用UTF-8
//                sb.append(new String(bytes, 0, len, "UTF-8"));
//                System.out.println("从客户端获取消息："+sb.toString());
//            }
           
            
            int read = 0;
            while(read != -1){
                /**
                 * This method blocks until input data is
                 * available, end of file is detected, or an exception is thrown.
                 * 一直阻塞直到收到消息或者出现异常
                 */
                read = inputStream.read(bytes);
                System.out.println("从客户端获取消息："+new String(bytes, 0, read, "UTF-8"));
            }
            
//            ObjectInputStream is = new ObjectInputStream(new BufferedInputStream(socket.getInputStream()));  
//            try {
//                Object obj = is.readObject();
//                System.out.println("aa");
//            } catch (ClassNotFoundException e) {
//                 e.printStackTrace();
//            }  
            
            
            inputStream.close();
            
        } catch (IOException e) {
             e.printStackTrace();
        } finally {
            if(socket != null){
                try {
                    socket.close();
                } catch (IOException e) {
                     e.printStackTrace();
                }
            }
        }
        

    }

}
