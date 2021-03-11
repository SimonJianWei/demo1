package com.example.service;

import com.example.util.ApplicationContextUtil;
import com.example.util.FatherConsumerSetter;
import kafka.utils.ShutdownableThread;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 *
 * @Auther: chenjianwei
 * @Date: 2020/11/18/9:41
 * @Description: kafka消费者案例
 */
public class DemoKafkaConsumer  extends ShutdownableThread {

    String threadName = "";
    // 消费者对象
    private KafkaConsumer<String, String> consumer;
    // 从服务端获取的数据
    private ConsumerRecords<String, String> records = null;

    public DemoKafkaConsumer(String name, boolean isInterruptible) {
        super(name, isInterruptible);
        String kafka_appfault_topic = ApplicationContextUtil.applicationContext.getEnvironment().getProperty("kafka_Topic");
        String kafka_appfault_groupId = ApplicationContextUtil.applicationContext.getEnvironment().getProperty("kafka_GroupId");
        String kafka_appfault_server = ApplicationContextUtil.applicationContext.getEnvironment().getProperty("kafka_server");
        threadName = name;
        Map<String, Object> mapParams = new HashMap<String, Object>();
        mapParams.put("topic", kafka_appfault_topic);
        mapParams.put("group", kafka_appfault_groupId);
        mapParams.put("server", kafka_appfault_server);
        // 获取消费者
        consumer = FatherConsumerSetter.SetConsumer(mapParams);
    }


    @Override
    public void doWork() {
        try {
            records = consumer.poll(3000);
            for (ConsumerRecord<String, String> record : records){
                String message = record.value();

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
