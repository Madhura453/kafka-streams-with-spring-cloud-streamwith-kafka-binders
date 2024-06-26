package com.kafkastreamcloudstreamerrorhandling.errorHandler;

import com.kafkastreamcloudstreamerrorhandling.model.OrderInputMsg;
import com.kafkastreamcloudstreamerrorhandling.model.Tuple;
import com.kafkastreamcloudstreamerrorhandling.service.OrderProcessorService;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.Predicate;
import org.apache.kafka.streams.kstream.Transformer;
import org.apache.kafka.streams.processor.ProcessorContext;
import org.springframework.cloud.stream.annotation.StreamRetryTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.retry.RetryPolicy;
import org.springframework.retry.backoff.FixedBackOffPolicy;
import org.springframework.retry.policy.SimpleRetryPolicy;
import org.springframework.retry.support.RetryTemplate;

import java.util.Map;
import java.util.function.Function;

@Configuration
@Slf4j
public class ErrorHandler {
    @Bean
    @StreamRetryTemplate
    RetryTemplate myRetryTemplate() {
        RetryTemplate retryTemplate = new RetryTemplate();
        RetryPolicy retryPolicy = new SimpleRetryPolicy(3);
        FixedBackOffPolicy backOffPolicy = new FixedBackOffPolicy();
        backOffPolicy.setBackOffPeriod(2);
        retryTemplate.setRetryPolicy(retryPolicy);
        retryTemplate.setBackOffPolicy(backOffPolicy);
        return retryTemplate;
    }

    @Bean
    public Function<KStream<String, OrderInputMsg>, KStream<String, OrderInputMsg>[]> orderBranchingProcessor() {
        Predicate<String, Tuple<Throwable, OrderInputMsg>> isSuccessful = (key, value) -> value.getOptionalT().isEmpty();
        Predicate<String, Tuple<Throwable, OrderInputMsg>> isFailure = (key, value) -> value.getOptionalT().isPresent();
        return stringOrderInputMsgKStream -> {
            final Map<String, KStream<String, Tuple<Throwable, OrderInputMsg>>> stringKStreamMap =
                    stringOrderInputMsgKStream
                            .peek((key, value) -> log.info("Branch Order input msg received with key: {} and payload: {}", key, value))
                            .map(this::getTransformedMessage)
                            .split()
                            .branch(isSuccessful)
                            .branch(isFailure)
                            .noDefaultBranch();
            return stringKStreamMap.values()
                    .stream()
                    .map(stringTupleKStream -> stringTupleKStream
                            .mapValues((key, value) -> value.getOptionalU()
                                    .orElseGet(OrderInputMsg::new)))
                    .toArray(KStream[]::new);
        };
    }


    private KeyValue<String, Tuple<Throwable, OrderInputMsg>> getTransformedMessage(String key, OrderInputMsg value) {
        try {
            final OrderInputMsg msg = OrderProcessorService.processOrderMsg(value);
            final Tuple<Throwable, OrderInputMsg> inputMsgTuple = new Tuple<>(null, value);
            return new KeyValue<>(key, inputMsgTuple);
        } catch (Exception e) {
            final Tuple<Throwable, OrderInputMsg> inputMsgTuple = new Tuple<>(e, value);
            return new KeyValue<>(key, inputMsgTuple);
        }
    }

    @Bean
    public Function<KStream<String, OrderInputMsg>, KStream<String, OrderInputMsg>> orderProcessor() {
        return stringOrderInputMsgKStream -> stringOrderInputMsgKStream
                .peek((key, value) -> log.info("Order input msg received with key: {} and payload: {}", key, value))
                .transform(() -> new Transformer<String, OrderInputMsg, KeyValue<String, OrderInputMsg>>() {
                    ProcessorContext context;

                    @Override
                    public void init(ProcessorContext processorContext) {
                        this.context = context;
                    }

                    @Override
                    public KeyValue<String, OrderInputMsg> transform(String key, OrderInputMsg value) {
                        try {
                            final OrderInputMsg msg = OrderProcessorService.processOrderMsg(value);
                            return new KeyValue<>(key, msg);
                        } catch (Exception e) {
                            System.err.println("===================ERROR: " + e.getMessage());
                            myRetryTemplate().execute(retryContext -> {
                                        final int retryCount = retryContext.getRetryCount();
                                        log.warn("Retrying the message key: {}", key);
                                        log.warn("Current Retry Count: {}", retryCount);
                                        return new KeyValue(key, OrderProcessorService.processOrderMsg(value));
                                    },
                                    context2 -> {
                                        context.commit();
                                        return new KeyValue<>(key, null);
                                    });
                        }
                        context.commit();
                        return new KeyValue<>(key, null);
                    }

                    @Override
                    public void close() {

                    }
                })
                .peek((key, value) -> log.info("ORDER CREDIT CARD INFO MASKED FOR KEY: {}, VALUE:{}", key, value))
                .filter((key, value) -> value != null);
    }

    @Bean
    public Function<KStream<String, String>, KStream<String, String>[]> upperCaseBranchingProcessor() {
        return stringStringKStream -> {
            final Map<String, KStream<String, String>> first = stringStringKStream
                    .peek((key, value) -> log.info("TEXT MSG READ."))
                    .mapValues(value -> value.toUpperCase())
                    .split()
                    .branch((key, value) -> value.contains("FIRST"))
                    .branch((key, value) -> value.contains("SECOND"))
                    .noDefaultBranch();
            return first.values()
                    .stream()
                    .map(stringStringKStream1 -> stringStringKStream1
                            .mapValues((key, value) -> value.toLowerCase()))
                    .toArray(KStream[]::new);
        };
    }
}
