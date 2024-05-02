package com.springcloud.kafkastreamdomainproducer.kafka;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import com.eventdrivenkafka.basedomains.dto.DomainList;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class MessageReceiveFromDomain {

    public Mono<DomainList> getDomainList()
    {
        String name="facebook";
        return WebClient.create()
                .get()
                .uri("https://api.domainsdb.info/v1/domains/search?domain=facebook&zone=com")
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(DomainList.class);
    }
}
