package com.kafkastreamcloudstreamerrorhandling.customErrorHandler;

import com.kafkastreamcloudstreamerrorhandling.model.OrderInputMsg;
import com.kafkastreamcloudstreamerrorhandling.model.Tuple;
import com.kafkastreamcloudstreamerrorhandling.service.OrderProcessorService;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.Predicate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;
import java.util.function.Function;

@Configuration
@Slf4j
public class CustomErrorHandler {

    @Bean
    public Function<KStream<String, OrderInputMsg>, KStream<String, OrderInputMsg>[]> orderBranchingProcessor() {
        Predicate<String, Tuple<Throwable, OrderInputMsg>> isSuccessful = (key, value) -> value.getOptionalT().isEmpty();
        Predicate<String, Tuple<Throwable, OrderInputMsg>> isFailure = (key, value) -> value.getOptionalT().isPresent();
        return stringOrderInputMsgKStream -> {
            final Map<String, KStream<String, Tuple<Throwable, OrderInputMsg>>> stringKStreamMap = stringOrderInputMsgKStream
                    .peek((key, value) -> log.info("Branch Order input msg received with key: {} and payload: {}", key, value))
                    .map(this::getTransformedMessage)
                    .split()
                    .branch(isSuccessful)
                    .branch(isFailure)
                    .noDefaultBranch();
            return stringKStreamMap
                    .values()
                    .stream()
                    .map(this::getStringOrderInputMsgKStream)
                    .toArray(KStream[]::new);
        };
    }

    public KeyValue<String, Tuple<Throwable, OrderInputMsg>> getTransformedMessage(String key, OrderInputMsg value) {
        try {
            final OrderInputMsg msg = OrderProcessorService.processOrderMsg(value);
            Tuple<Throwable, OrderInputMsg> inputMsgTuple = new Tuple<>(null, msg);
            return new KeyValue<>(key, inputMsgTuple);
        } catch (Exception e) {
            Tuple<Throwable, OrderInputMsg> inputMsgTuple = new Tuple<>(e, value);
            return new KeyValue<>(key, inputMsgTuple);
        }
    }

    private KStream<String, OrderInputMsg> getStringOrderInputMsgKStream(KStream<String, Tuple<Throwable, OrderInputMsg>> stringTupleKStream) {
        return stringTupleKStream
                .mapValues((key, value) ->
                        value.getOptionalU()
                                .orElseGet(OrderInputMsg::new)
                );
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
