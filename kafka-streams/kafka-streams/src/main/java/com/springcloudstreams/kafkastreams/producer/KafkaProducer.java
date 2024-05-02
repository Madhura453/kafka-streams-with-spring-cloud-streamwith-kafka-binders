package com.springcloudstreams.kafkastreams.producer;

import com.springcloudstreams.kafkastreams.dto.Department;
import com.springcloudstreams.kafkastreams.dto.Event;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.Message;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.support.MessageBuilder;

import java.util.Random;
import java.util.function.Supplier;

@Configuration
public class KafkaProducer {

    @Bean
    public Supplier<Message<Event>> producer()
    {
       return()-> {
           Department[] departments=Department.values();
           Department department=departments[new Random().nextInt(departments.length)];
           Event event=new Event("madhura",department);
           return MessageBuilder.withPayload(event)
                   .setHeader(KafkaHeaders.KEY, department.name())
                   .build();
       };
    }
}
