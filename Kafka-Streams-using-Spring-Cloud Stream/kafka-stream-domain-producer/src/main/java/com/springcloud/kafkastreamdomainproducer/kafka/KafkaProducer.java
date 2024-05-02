package com.springcloud.kafkastreamdomainproducer.kafka;

import com.eventdrivenkafka.basedomains.dto.Domain;
import com.eventdrivenkafka.basedomains.dto.DomainList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.function.Supplier;

@Configuration
public class KafkaProducer {

    @Autowired
    MessageReceiveFromDomain messageReceiveFromDomain;
    @Bean
    public Supplier<Flux<Domain>> producerBinding()
    {
        Mono<DomainList> domainListMono=messageReceiveFromDomain.getDomainList();
//        domainListMono.subscribe(domainList -> {
//            domainList.getDomains()
//                    .forEach(domain -> {
//                        //kafkaTemplate.send(KAFKA_TOPIC, domain);
//                        System.out.println("Domain message" + domain.getDomain());
//                    });
//        });
        return() -> domainListMono.flatMapMany(domainList -> Flux.fromIterable(domainList.getDomains()));
    }
}
