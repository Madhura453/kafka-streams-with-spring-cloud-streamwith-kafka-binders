package com.springcloud.kafkastreamdomainconsumer;

import com.eventdrivenkafka.basedomains.dto.Domain;
import org.apache.kafka.streams.kstream.KStream;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.function.Consumer;

@Configuration
public class DomainKafkaConsumer {
    @Bean
    public Consumer<KStream<String, Domain>> consumerBinding()
    {
      return KStream->KStream.foreach((key,domain)->
        {
           System.out.println(String.format("Domain consumed[%s] Status[%s]",domain.getDomain(),
                   domain.isDead()));
        });
    }
}
