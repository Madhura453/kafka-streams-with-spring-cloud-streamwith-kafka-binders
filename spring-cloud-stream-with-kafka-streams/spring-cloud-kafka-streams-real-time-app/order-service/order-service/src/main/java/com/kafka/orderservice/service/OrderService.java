package com.kafka.orderservice.service;

import com.kafka.orderservice.model.Order;
import com.kafka.orderservice.model.OrderType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Random;
import java.util.function.Supplier;

@Configuration
@Slf4j
public class OrderService {

    private Long orderId = 0l;

    private final Random random = new Random();

    private final Map<Integer, Integer> prices = Map.of(
            1, 1000,
            2, 2000,
            3, 5000,
            4, 1500,
            5, 2500,
            6, 1000,
            7, 2000,
            8, 5000,
            9, 1500,
            10, 2500);

    LinkedList<Order> buyOrders = new LinkedList<>(List.of(
            new Order(++orderId, 1, 1, 100, LocalDateTime.now(), OrderType.BUY, 1000),
            new Order(++orderId, 2, 1, 200, LocalDateTime.now(), OrderType.BUY, 1050),
            new Order(++orderId, 3, 1, 100, LocalDateTime.now(), OrderType.BUY, 1030),
            new Order(++orderId, 4, 1, 200, LocalDateTime.now(), OrderType.BUY, 1050),
            new Order(++orderId, 5, 1, 200, LocalDateTime.now(), OrderType.BUY, 1000),
            new Order(++orderId, 11, 1, 100, LocalDateTime.now(), OrderType.BUY, 1050)
    ));

    LinkedList<Order> sellOrders = new LinkedList<>(List.of(
            new Order(++orderId, 6, 1, 200, LocalDateTime.now(), OrderType.SELL, 950),
            new Order(++orderId, 7, 1, 100, LocalDateTime.now(), OrderType.SELL, 1000),
            new Order(++orderId, 8, 1, 100, LocalDateTime.now(), OrderType.SELL, 1050),
            new Order(++orderId, 9, 1, 300, LocalDateTime.now(), OrderType.SELL, 1000),
            new Order(++orderId, 10, 1, 200, LocalDateTime.now(), OrderType.SELL, 1020)
    ));
    @Bean
    public Supplier<Message<Order>> orderBuySupplier() {
     return ()->{
         if(buyOrders.peek()!=null)
         {
             Message<Order> order = MessageBuilder
                     .withPayload(buyOrders.peek())
                     .setHeader(KafkaHeaders.KEY,Objects.requireNonNull(buyOrders.poll()).getId())
                     .build();
             log.info("buy Order: {},Key: {}", order.getPayload(),order.getHeaders());
                     return order;
         }
         return null;
     };
    }

    @Bean
    public Supplier<Message<Order>> orderSellSupplier() {
       return ()->
       {
           if(sellOrders.peek()!=null)
           {
               Message<Order> order = MessageBuilder
                       .withPayload(sellOrders.peek())
                       .setHeader(KafkaHeaders.KEY,Objects.requireNonNull(sellOrders.poll()).getId())
                       .build();
               log.info("sell Order: {},Key: {}", order.getPayload(),order.getHeaders());
               return order;
           }
           return null;
       };
    }

    @Bean
    public Supplier<Message<Order>> diffrentvaluesorderBuySupplier() {
        return () -> {
            Integer productId = random.nextInt(1, 6);
            int price = prices.get(productId) + random.nextInt(-100,100);
            Order o = new Order(
                    ++orderId,
                    random.nextInt(1, 11),
                    productId,
                    100,
                    LocalDateTime.now(),
                    OrderType.BUY,
                    price);
            log.info("Different buy Order: {}", o);
            return MessageBuilder
                    .withPayload(o)
                    .setHeader(KafkaHeaders.KEY, orderId)
                    .build();
        };
    }

    @Bean
    public Supplier<Message<Order>>  diffrentvaluesorderSellSupplier() {
        return () -> {
            Integer productId = random.nextInt(1, 6);
            int price = prices.get(productId) + random.nextInt(-100,100);
            Order o = new Order(
                    ++orderId,
                    random.nextInt(1, 11),
                    productId,
                    100,
                    LocalDateTime.now(),
                    OrderType.SELL,
                    price);
            log.info("different sell Order: {}", o);
            return MessageBuilder
                    .withPayload(o)
                    .setHeader(KafkaHeaders.KEY, orderId)
                    .build();
        };
    }
}
