package kafka;

import java.util.Properties;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;

import kafka.consumer.Consumer;

public class KafkaConfig {

    public static String topic_test = "test";

    private static Configuration config = null;
    /**
     * 配置文件名称
     */
    private static String configfile = "kafka.properties";

//	public static KafkaProducer<String, String> producer = null;

    static {
        try {
            config = new PropertiesConfiguration(configfile);

//			producer = new KafkaProducer<String, String>(getProducerConfig());
        } catch (ConfigurationException e) {
            e.printStackTrace();
        }
    }

    public static String getString(String key) {
        String value = config.getString(key).replace(";", ",");
        return value;
    }

    //获取生产者配置
    public static Properties getProducerConfig() {

        Properties props = new Properties();

        // 服务器ip:端口号，集群用逗号分隔
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, getString("kafka.metadata.broker.list"));
        // key序列化指定类
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, getString("kafka.key.serializer"));
        // value序列化指定类
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, getString("kafka.value.serializer"));
        // #0:表示producer生产的消息，被topic的partition的leader接收，就返回成功，不管是否存储到kafka
        // #1:表示producer生产的消息，被topic的partition的leader接收且存储到其服务器的replica备份上，就返回成功，但是如果此时leader挂了，则消息丢失，因为其他的服务器replica备份无此消息
        // #all:表示producer生产的消息，被leader接收且存储在leader服务器的replica上，并且该partition的其他replica也存储完成，则返回成功
        props.put(ProducerConfig.ACKS_CONFIG, getString("kafka.request.required.acks"));

        props.put(ProducerConfig.BATCH_SIZE_CONFIG, getString("kafka.batch.size"));
        props.put(ProducerConfig.LINGER_MS_CONFIG, getString("kafka.lingger.ms"));
        return props;
    }

    //获取消费者配置
    public static Properties getConsumerConfig() {

        Properties props = new Properties();

        props.put("zookeeper.connect", "192.168.0.117:2181,192.168.0.118:2181,192.168.0.119:2181");

        // group 代表一个消费组
        props.put("group.id", getString("kafka.group.id"));

        // zk连接超时
        props.put("zookeeper.session.timeout.ms", "4000");
        props.put("zookeeper.sync.time.ms", "200");
        props.put("auto.commit.enable", "false");//是否开启自动提交
        props.put("auto.commit.interval.ms", "1000");
        //取之前的
//		props.put("auto.offset.reset", "smallest");
        //取最新的
        props.put("auto.offset.reset", "largest");
        // 序列化类
        props.put("serializer.class", "kafka.serializer.StringEncoder");


        return props;
    }

}