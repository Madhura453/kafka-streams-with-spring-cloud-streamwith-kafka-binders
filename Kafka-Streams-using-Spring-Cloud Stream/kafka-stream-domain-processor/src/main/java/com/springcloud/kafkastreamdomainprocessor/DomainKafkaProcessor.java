package com.springcloud.kafkastreamdomainprocessor;

import com.eventdrivenkafka.basedomains.dto.Domain;
import org.apache.kafka.streams.kstream.KStream;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.function.Function;

@Configuration
public class DomainKafkaProcessor {
    @Bean
    public Function<KStream<String,Domain>,KStream<String,Domain>> processorBinding()
    {
        return KStream->KStream.filter((key,domain)->
        {
            if (domain.isDead()) {
                System.out.println("Inactive Domain: " + domain.getDomain());
            } else {
                System.out.println("Active Domain: " + domain.getDomain());
            }
            return !domain.isDead();
        });
    }
}
