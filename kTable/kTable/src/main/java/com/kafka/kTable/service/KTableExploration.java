package com.kafka.kTable.service;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.streams.kstream.KTable;
import org.apache.kafka.streams.kstream.Printed;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.function.Consumer;
import java.util.function.Supplier;


@Configuration
@Slf4j
public class KTableExploration {

    @Bean
    public Supplier<Message<String>> producer()
    {
        List<String> words = Arrays.asList("apple", "banana", "cat", "dog", "bird", "car", "ant", "bat", "can", "ball", "cabbage", "box", "bee", "arm");
        return()-> {
            String event=words.get(new Random().nextInt(words.size()));

            return MessageBuilder.withPayload(event)
                    .setHeader(KafkaHeaders.KEY, String.valueOf(event.charAt(0)))
                    .build();
        };
    }
    @Bean
    public Consumer<KTable<String, String>> process() {
        return input -> input.toStream()
                .peek((key, value) -> log.info("key: {}, value: {}", key, value))
                .print(Printed.<String, String>toSysOut().withLabel("wordsTableStream"));
    }
}
