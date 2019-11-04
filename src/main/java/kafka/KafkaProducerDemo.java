package kafka;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;

import kafka.consumer.Consumer;
import kafka.consumer.ConsumerConfig;
import kafka.consumer.ConsumerIterator;
import kafka.consumer.KafkaStream;
import kafka.javaapi.consumer.ConsumerConnector;
import kafka.message.MessageAndMetadata;
import kafka.serializer.StringDecoder;
import kafka.utils.VerifiableProperties;

/*
  Kafka生产者Demo
 */
public class KafkaProducerDemo {

    public static void main(String[] args) {
//    	a();
        produce(KafkaConfig.topic_test, "hello, kafka555");

        consume(KafkaConfig.topic_test);
//    	c();
    }

    private static void consume(String topic_test) {

        Properties props = KafkaConfig.getConsumerConfig();

        ConsumerConfig config = new ConsumerConfig(props);
        System.out.println("是否自动提交：" + config.autoCommitEnable());

        ConsumerConnector consumer = Consumer.createJavaConsumerConnector(config);


        Map<String, Integer> topicCountMap = new HashMap<String, Integer>();
        topicCountMap.put(topic_test, new Integer(1));

        StringDecoder keyDecoder = new StringDecoder(new VerifiableProperties());
        StringDecoder valueDecoder = new StringDecoder(new VerifiableProperties());

        Map<String, List<KafkaStream<String, String>>> consumerMap = consumer.createMessageStreams(topicCountMap,
                keyDecoder, valueDecoder);

        KafkaStream<String, String> stream = consumerMap.get(topic_test).get(0);
        ConsumerIterator<String, String> it = stream.iterator();
        while (it.hasNext()) {
            MessageAndMetadata<String, String> mam = it.next();
            System.out.println("消息:" + mam.message() + ",偏移量：" + mam.offset());
//			consumer.commitOffsets();//生效！！！！！！！！！！！！！
        }

    }


    /**
     * Kafka消息保证生产的信息不丢失和重复消费问题
     * 1）使用同步模式的时候，有3种状态保证消息被安全生产，在配置为1（只保证写入leader成功）的话，如果刚好leader partition挂了，数据就会丢失。
     * 2）还有一种情况可能会丢失消息，就是使用异步模式的时候，当缓冲区满了，如果配置为0（还没有收到确认的情况下，缓冲池一满，就清空缓冲池里的消息），
     * 数据就会被立即丢弃掉。
     * <p>
     * 在数据生产时避免数据丢失的方法：
     * 只要能避免上述两种情况，那么就可以保证消息不会被丢失。
     * 1）就是说在同步模式的时候，确认机制设置为-1，也就是让消息写入leader和所有的副本。
     * 2）还有，在异步模式下，如果消息发出去了，但还没有收到确认的时候，缓冲池满了，在配置文件中设置成不限制阻塞超时的时间，也就说让生产端一直阻塞，这样也能保证数据不会丢失。
     * 在数据消费时，避免数据丢失的方法：如果使用了storm，要开启storm的ackfail机制；如果没有使用storm，确认数据被完成处理之后，再更新offset值。低级API中需要手动控制offset值。
     */
    private static void produce(String topic, String message) {

        Properties props = KafkaConfig.getProducerConfig();

        // 生产者对象
        KafkaProducer<String, String> producer = new KafkaProducer<String, String>(props);

        // 默认异步
//        Future<RecordMetadata> future = producer.send(new ProducerRecord<String, String>(topic, message));

//        try {
//        	//get同步sync阻塞，ack生效
//			System.out.println(future.get().offset());
//		} catch (InterruptedException e) {
//			e.printStackTrace();
//		} catch (ExecutionException e) {
//			e.printStackTrace();
//		}
        //异步通知如果失败可以存储，等定时任务去重新发送，保证异步发送的消息不丢失
        //异步非阻塞
        producer.send(new ProducerRecord<String, String>(topic, message), new Callback() {

            @Override
            public void onCompletion(RecordMetadata metadata, Exception exception) {
                System.out.println(metadata.offset());
            }
        });

        producer.close();
    }

//	private static void a() {
//
//		Properties props = new Properties();
//		props.put("bootstrap.servers", "192.168.0.119:9092,192.168.0.117:9092,192.168.0.118:9092");
//		props.put("acks", "all");
//		props.put("retries", 0);
//		props.put("batch.size", 16384);
//		props.put("linger.ms", 1);
//		props.put("buffer.memory", 33554432);
//		props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
//		props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
//		
//		Producer<String, String> producer = new KafkaProducer<>(props);
//		List<Object> list = new ArrayList<Object>();
//		for (int i = 0; i < 100; i++) {
//			System.out.println(i);
//			Future<RecordMetadata> future = producer.send(new ProducerRecord<String, String>("test", Integer.toString(i)));
//			if(future != null){
//				try {
//					RecordMetadata record = future.get();
//					System.out.println("主题："+record.topic()+",偏移量："+record.offset()+",所在分区："+record.partition());
//				} catch (InterruptedException e) {
//					e.printStackTrace();
//				} catch (ExecutionException e) {
//					e.printStackTrace();
//				}
//			}
//			
//		}
//		producer.close();
//	}


//	private static void c() {
//		
//		Properties props = new Properties();
//		// zookeeper 配置
//		props.put("zookeeper.connect", "192.168.0.117:2181,192.168.0.118:2181,192.168.0.119:2181");
// 
//		// group 代表一个消费组
//		props.put("group.id", "test-consumer-group");
// 
//		// zk连接超时
//		props.put("zookeeper.session.timeout.ms", "4000");
//		props.put("zookeeper.sync.time.ms", "200");
//		props.put("auto.commit.interval.ms", "1000");
//		props.put("auto.offset.reset", "smallest");
//		// 序列化类
//		props.put("serializer.class", "kafka.serializer.StringEncoder");
// 
//		ConsumerConfig config = new ConsumerConfig(props);
// 
//		ConsumerConnector consumer = Consumer.createJavaConsumerConnector(config);
// 
//		Map<String, Integer> topicCountMap = new HashMap<String, Integer>();
//		topicCountMap.put("test", new Integer(1));
// 
//		StringDecoder keyDecoder = new StringDecoder(new VerifiableProperties());
//		StringDecoder valueDecoder = new StringDecoder(new VerifiableProperties());
// 
//		Map<String, List<KafkaStream<String, String>>> consumerMap = consumer.createMessageStreams(topicCountMap,
//				keyDecoder, valueDecoder);
//		KafkaStream<String, String> stream = consumerMap.get("test").get(0);
//		ConsumerIterator<String, String> it = stream.iterator();
//		while (it.hasNext()){
//			System.out.println(it.next().message());
//		}
//	}

}
