 package netty.msgpack;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.msgpack.MessagePack;
import org.msgpack.template.Templates;

/**
 * MessagePack的特点如下：
 * 1.编解码高效，性能高
 * 2.序列化之后的码流小
 * 3.提供对多语言的支持，很多很多
 */
public class TestMsgpack {

    public static void main(String[] args) throws IOException {
        
        List<String> list = new ArrayList<String>();
        list.add("msgpack");
        list.add("kumofs");
        list.add("viver");
        
        MessagePack msgPack = new MessagePack();
        
        byte[] raw = msgPack.write(list);
        
        List<String> dst = msgPack.read(raw, Templates.tList(Templates.TString));
        
        System.out.println(dst.get(0));
        
    }

}
