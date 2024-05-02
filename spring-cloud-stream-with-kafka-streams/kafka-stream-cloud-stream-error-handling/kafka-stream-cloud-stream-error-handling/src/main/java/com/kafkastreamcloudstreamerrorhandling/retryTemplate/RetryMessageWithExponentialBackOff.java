package com.kafkastreamcloudstreamerrorhandling.retryTemplate;

import com.kafkastreamcloudstreamerrorhandling.model.OrderInputMsg;
import com.kafkastreamcloudstreamerrorhandling.service.OrderProcessorService;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.Transformer;
import org.apache.kafka.streams.kstream.ValueMapper;
import org.apache.kafka.streams.processor.ProcessorContext;
import org.springframework.cloud.stream.annotation.StreamRetryTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.retry.RetryPolicy;
import org.springframework.retry.backoff.FixedBackOffPolicy;
import org.springframework.retry.policy.SimpleRetryPolicy;
import org.springframework.retry.support.RetryTemplate;

import java.util.function.Function;

@Configuration
@Slf4j
public class RetryMessageWithExponentialBackOff {

    @Bean
    @StreamRetryTemplate
    RetryTemplate myRetryTemplate() {
        RetryTemplate retryTemplate = new RetryTemplate();
        RetryPolicy retryPolicy = new SimpleRetryPolicy();
        FixedBackOffPolicy backOffPolicy = new FixedBackOffPolicy();
        backOffPolicy.setBackOffPeriod(2);
        retryTemplate.setBackOffPolicy(backOffPolicy);
        retryTemplate.setRetryPolicy(retryPolicy);
        return retryTemplate;
    }

    @Bean
    public Function<KStream<String, OrderInputMsg>, KStream<String, OrderInputMsg>> orderProcessor() {
        return stringOrderInputMsgKStream -> stringOrderInputMsgKStream
                .peek((key, value) -> log.info("Order input msg received with key: {} and payload: {}", key, value))
                .transform(() -> new Transformer<String, OrderInputMsg, KeyValue<String, OrderInputMsg>>() {
                    ProcessorContext context;

                    @Override
                    public void init(ProcessorContext processorContext) {
                        this.context = processorContext;
                    }

                    @Override
                    public KeyValue<String, OrderInputMsg> transform(String key, OrderInputMsg value) {
                        return myRetryTemplate()
                                .execute(retryContext -> {
                                            final int retryCount = retryContext.getRetryCount();
                                            if (retryCount > 0) {
                                                log.warn("Retrying the message key: {}", key);
                                                log.warn("Current Retry Count: {}", retryCount);
                                            }
                                           return new KeyValue<>(key, OrderProcessorService.processOrderMsg(value));
                                        },
                                        retryContext2 -> {
                                             context.commit();
                                          return new KeyValue<>(key,null);
                                        });
                    }

                    @Override
                    public void close() {

                    }
                })
                .peek((key, value) -> log.info("ORDER CREDIT CARD INFO MASKED FOR KEY: {}, VALUE:{}", key, value))
                .filter((key,value)->value!=null);
    }

    @Bean
    public Function<KStream<String, String>, KStream<String, String>> upperCaseProcessor() {
        return stringStringKStream -> stringStringKStream
                .mapValues((ValueMapper<String, String>) String::toUpperCase);
    }
}
// Un commit this line when execute OrdersProcessorKafkaStreams