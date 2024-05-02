package com.springcloudstreams.kafkastreams.enhancer;

import com.springcloudstreams.kafkastreams.dto.Event;
import org.apache.kafka.streams.kstream.KStream;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.function.Function;

@Configuration
public class KafkaEnhancer {

    @Bean
    public Function<KStream<String, Event>,KStream<String,String>> enhancer()
    {
        return input->input
                .mapValues(value->value.name())
                .peek((k, v) -> System.out.println("Enhancer " + k + " " + v));
    }

}
