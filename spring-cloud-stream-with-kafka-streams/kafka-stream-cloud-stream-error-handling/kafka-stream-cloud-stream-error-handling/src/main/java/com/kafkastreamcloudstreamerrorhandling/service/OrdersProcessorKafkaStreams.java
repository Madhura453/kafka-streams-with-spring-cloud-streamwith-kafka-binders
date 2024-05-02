//package com.kafkastreamcloudstreamerrorhandling.service;
//
//import com.kafkastreamcloudstreamerrorhandling.model.OrderInputMsg;
//import lombok.extern.slf4j.Slf4j;
//import org.apache.kafka.streams.kstream.KStream;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//import java.util.function.Function;
//
//@Configuration
//@Slf4j
//public class OrdersProcessorKafkaStreams {
//
//    @Bean
//    public Function<KStream<String, OrderInputMsg>,KStream<String, OrderInputMsg>> orderProcessor(){
//       return stringOrderInputMsgKStream ->
//               stringOrderInputMsgKStream
//                       .peek((key, value) -> log.info("Order input msg received with key: {} and payload: {}", key, value))
//                       .mapValues(OrderProcessorService::processOrderMsg);
//    }
//
//    @Bean
//    public Function<KStream<String, String>,KStream<String, String>> upperCaseProcessor(){
//      return stringStringKStream -> stringStringKStream
//              .peek((key, value) -> log.info("input msg received with key: {} and payload: {}", key, value))
//              .mapValues(value->value.toUpperCase());
//    }
//}
//// Uncommit this line when try to execute RetryMessageWithExponentialBackOff