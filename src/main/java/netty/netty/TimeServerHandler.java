package netty.netty;

import java.util.Date;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

/**
 * 用于对网络事件进行读写操作
 */
public class TimeServerHandler extends ChannelHandlerAdapter {

    private int counter;

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {

//        ByteBuf buf = (ByteBuf)msg;
//        byte[] req = new byte[(buf.readableBytes())];
//        
//        buf.readBytes(req);

//      String body = new String(req, "UTF-8");
//      System.out.println("The time server receive order : "+ body);


//        String body = new String(req, "UTF-8").substring(0, req.length - System.getProperty("line.separator").length());

        //使用了字符编码就不需要上面的了
        String body = (String) msg;

        System.out.println("The time server receive order : " + body + " ; the counter is : " + ++counter);


        String currentTime = "QUERY TIME ORDER".equals(body) ? new Date().toString() : "BAD ORDER";

        //如果用到换行符，必须用这个
        currentTime = currentTime + System.getProperty("line.separator");

        ByteBuf resp = Unpooled.copiedBuffer(currentTime.getBytes());

        //将待发送的消息发送到缓冲区
        ctx.writeAndFlush(resp);
//        ctx.writeAndFlush(msg);

    }

//    @Override
//    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
//        /**
//         * 将缓冲区里的消息全部写到socketchannel中
//         */
//        ctx.flush();
//    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
    }

}
