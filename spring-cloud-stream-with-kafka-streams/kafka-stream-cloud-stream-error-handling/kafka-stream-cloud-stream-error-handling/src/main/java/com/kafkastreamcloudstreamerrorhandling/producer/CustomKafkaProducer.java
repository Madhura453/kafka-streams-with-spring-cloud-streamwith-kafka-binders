package com.kafkastreamcloudstreamerrorhandling.producer;

import com.kafkastreamcloudstreamerrorhandling.model.OrderInputMsg;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.util.Properties;
import java.util.UUID;

public class CustomKafkaProducer {

    public static void main(String[] args) {
        produceData();
        produceOrderInfo();
    }

    private static void produceData() {
        Properties props = getKafkaProducerProps();
        Producer<String, String> producer = new KafkaProducer<>(props);
        String topicName = "src-textMsg-topic";
        for (int i = 0; i < 10; i++)
            producer.send(new ProducerRecord<>(topicName, Integer.toString(i), Integer.toString(i)));
        System.out.println("Message Sent.");
        producer.close();
    }

    private static void produceOrderInfo() {
        Properties kafkaProducerProps = getKafkaProducerProps();
        kafkaProducerProps.put("value.serializer",
                "org.springframework.kafka.support.serializer.JsonSerializer");
        Producer<String, OrderInputMsg> producer = new KafkaProducer<>(kafkaProducerProps);
        String topicName = OrderProducer.RAW_ORDER_TOPIC;
        for (int i = 0; i < 10; i++) {
            OrderInputMsg orderInputMsg = getOrderInputMsg();
            if (i == 2)
                orderInputMsg.setCreditCardNumber("magic");
            producer.send(new ProducerRecord<>(topicName, UUID.randomUUID().toString(), orderInputMsg));
            System.out.println("MSG SENT.");
        }
        producer.close();
    }

    private static OrderInputMsg getOrderInputMsg() {
        OrderInputMsg orderInputMsg = new OrderInputMsg();
        orderInputMsg.setOrderId(UUID.randomUUID().toString());
        orderInputMsg.setOrderAmount(1000d);
        orderInputMsg.setCreditCardNumber("1111-2222-3333-5555");
        orderInputMsg.setItemName("PS5");
        orderInputMsg.setUserName("Najeeb");
        return orderInputMsg;
    }

    private static Properties getKafkaProducerProps() {
        Properties props = new Properties();
        props.put("bootstrap.servers", "localhost:9092");
        props.put("acks", "all");
        props.put("retries", 0);
        //Reduce the no of requests less than 0
        props.put("linger.ms", 1);
        props.put("key.serializer",
                "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer",
                "org.apache.kafka.common.serialization.StringSerializer");
        return props;
    }
}
