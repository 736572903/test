 package netty.netty;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

public class TimeClientHandler extends ChannelHandlerAdapter {
    
//    private final ByteBuf firstMessage;
    
    private int counter;
    
    private byte[] req;
    
    public TimeClientHandler(){
//        byte[] req = "QUERY TIME ORDER".getBytes();
//        firstMessage = Unpooled.buffer(req.length);
//        firstMessage.writeBytes(req);
        req = ("QUERY TIME ORDER"+ System.getProperty("line.separator")).getBytes();
    }
    
    /**
     * 当客户端和服务端TCP链路建立成功之后，netty的nio线程会调用channelActive方法，
     * 发送查询时间的指令给服务端，调用channelHandlerContext的writeAndFlush方法将请求参数发送给服务端
     * 
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
//        ctx.writeAndFlush(firstMessage);
        /**
         * 会发生粘包
         */
        ByteBuf message = null;
        for(int i=0; i<100; i++){
            message = Unpooled.buffer(req.length);
            message.writeBytes(req);
            ctx.writeAndFlush(message);
        }
        
    }
    
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg){
//        ByteBuf buf = (ByteBuf)msg;
//        byte[] req = new byte[buf.readableBytes()];
//        buf.readBytes(req);
//        String body = new String(req, "UTF-8");
//        System.out.println("Now is : "+body);
        System.out.println("客户端收到信息--------");
        String body = (String)msg;
        
        System.out.println("Now is : "+body+"; the counter is : " + ++counter);
        
        
        
    }
    
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }
    
}
