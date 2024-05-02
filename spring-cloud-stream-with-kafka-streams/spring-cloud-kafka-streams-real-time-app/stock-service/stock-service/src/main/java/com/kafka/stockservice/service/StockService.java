package com.kafka.stockservice.service;

import com.kafka.stockservice.logic.OrderLogic;
import com.kafka.stockservice.model.Order;
import com.kafka.stockservice.model.Transaction;
import com.kafka.stockservice.model.TransactionTotal;
import com.kafka.stockservice.model.TransactionTotalWithProduct;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.common.utils.Bytes;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.kstream.Aggregator;
import org.apache.kafka.streams.kstream.Grouped;
import org.apache.kafka.streams.kstream.Initializer;
import org.apache.kafka.streams.kstream.JoinWindows;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.Materialized;
import org.apache.kafka.streams.kstream.StreamJoined;
import org.apache.kafka.streams.kstream.TimeWindows;
import org.apache.kafka.streams.kstream.ValueJoiner;
import org.apache.kafka.streams.state.KeyValueBytesStoreSupplier;
import org.apache.kafka.streams.state.Stores;
import org.apache.kafka.streams.state.WindowBytesStoreSupplier;
import org.apache.kafka.streams.state.WindowStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.support.serializer.JsonSerde;
import org.springframework.util.ObjectUtils;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Consumer;

@Configuration
@Slf4j
public class StockService {

    @Autowired
    OrderLogic orderLogic;

    private Long transactionId = 0l;

    @Bean
    public BiConsumer<KStream<Long, Order>, KStream<Long, Order>> consumeOrders() {
        return (orderBuy, orderSell) -> orderBuy
                .merge(orderSell)
                .peek((key, value) -> {
                    log.info("merge order saved : {},{}", key, value);
                    orderLogic.orderSave(value);
                });
    }

    @Bean
    public BiFunction<KStream<Long, Order>, KStream<Long, Order>, KStream<Long, Transaction>> transactions() {
        // For joining kstream-Kstream we required join-window. Because Kstream is infinity stream
        JoinWindows joinWindows = JoinWindows.ofTimeDifferenceWithNoGrace(Duration.ofSeconds(10));

        // first one key of both streams and second is value and third one is value
        StreamJoined<Integer, Order, Order> joinParams = StreamJoined.with(Serdes.Integer(),
                new JsonSerde<>(Order.class), new JsonSerde<>(Order.class));

        // this. execute and value joiner both are same. first is first stream value, second is second stream value
        // join will give Transaction
        ValueJoiner<Order, Order, Transaction> valueJoiner = (buyOrder, sellOrder) -> execute(buyOrder, sellOrder);


        return (orderBuy, orderSell) ->
                orderBuy.selectKey((aLong, buyOrder) -> buyOrder.getProductId())
                        .join(orderSell.selectKey((aLong, sellOrder) -> sellOrder.getProductId()),
                                this::execute,
                                joinWindows, joinParams)
                        .filterNot((productId, transaction) -> ObjectUtils.isEmpty(transaction))
                        .map((productId, transaction) -> new KeyValue<>(transaction.getId(), transaction))
                        .peek((translationId, transaction) ->
                                log.info("translation key: {} and value: {}", translationId, transaction));
    }


    @Bean
    public Consumer<KStream<String, Transaction>> totalTransactions() {
        KeyValueBytesStoreSupplier storeSupplier = Stores
                .persistentKeyValueStore("all-transactions-store");

        Initializer<TransactionTotal> transactionTotalInitializer = TransactionTotal::new;

        // transaction.getStatus(), is key  ,transaction is value and aggregator result will be transactionTotal
        // group by key getStatus()
        Aggregator<String, Transaction, TransactionTotal> aggregator = (key, transaction, transactionTotal) ->
        {
            transactionTotal.setCount(transactionTotal.getCount() + 1);
            transactionTotal.setProductCount(transactionTotal.getProductCount() + transaction.getQuantity());
            int prize = (transaction.getPrice() * transaction.getQuantity());
            transactionTotal.setPrize(transactionTotal.getPrize() + prize);
            return transactionTotal;
        };

        // key is private String status; grouping is based on that
        // Grouped.with(Serdes.String(),new JsonSerde<>(Transaction.class)
        // key is Serdes.String()= status Transaction is value
        // grouping the Transactions by the private String status
        return orderTransactions -> orderTransactions
                .peek((s, transaction) -> log.info("aggregate transation: {},value:{}", s, transaction))
                .groupBy((key, transaction) -> transaction.getStatus(),
                        Grouped.with(Serdes.String(), new JsonSerde<>(Transaction.class)))
                .aggregate(transactionTotalInitializer,
                        aggregator,
                        Materialized.<String, TransactionTotal>as(storeSupplier)
                                .withKeySerde(Serdes.String())
                                .withValueSerde(new JsonSerde<>(TransactionTotal.class)))
                .toStream()
                .peek((key, transactionTotal) -> log.info("The transactionTotal was key : {} and value is : {}"
                        , key, transactionTotal));

    }

    @Bean
    public BiConsumer<KStream<Long, Transaction>, KStream<Long, Order>> totalTransactionsPerProduct() {

        /* 1)Transaction does not contain productId. So to get productId we are performing join operation
          with orders .
          2)Join operation o/p(look into valueJoiner ) TransactionTotalWithProduct we will get productID with transaction
          3)So due to TransactionTotalWithProduct we will have both productId and Transaction
          So aggregation wil happen based on productID
          4) before Aggregation we need group by results. So group by productId
          group by results store in internalTopic. From this internalTopic we will do aggregation.
          5) Aggregation results will store materialized store transactions-per-product-store
       // TO do   6) join based on order id only (order-sell) because we can use order-buy also.
       // To do    But any one is enough. orderBuy-ordersell both join translations .
          .*/

        // join values

        JoinWindows joinWindows = JoinWindows.ofTimeDifferenceWithNoGrace(Duration.ofSeconds(10));

        // first value is transation and second is order and join result will be TransactionTotalWithProduct
        ValueJoiner<Transaction, Order, TransactionTotalWithProduct> valueJoiner = (transaction, order) ->
                new TransactionTotalWithProduct(transaction, order.getProductId());

        StreamJoined<Long, Transaction, Order> joinParams =
                StreamJoined.with(Serdes.Long(), new JsonSerde<>(Transaction.class), new JsonSerde<>(Order.class));


        // group by values
        KeyValueBytesStoreSupplier storeSupplier =
                Stores.persistentKeyValueStore("transactions-per-product-store");

        Initializer<TransactionTotal> initializer = TransactionTotal::new;

        // group by key is Integer transactionTotalWithProduct.getProductId() and value is transactionTotalWithProduct
        // aggregate result will be TransactionTotal
        Aggregator<Integer, TransactionTotalWithProduct, TransactionTotal> aggregator =
                (productId, transactionTotalWithProduct, transactionTotal) ->
                {
                    transactionTotal.setCount(transactionTotal.getCount() + 1);
                    transactionTotal.setProductCount(transactionTotal.getProductCount() +
                            transactionTotalWithProduct.getTransaction().getQuantity());
                    int prize = (transactionTotalWithProduct.getTransaction().getPrice() * transactionTotalWithProduct
                            .getTransaction().getQuantity());
                    transactionTotal.setPrize(transactionTotal.getPrize() + prize);
                    return transactionTotal;
                };

        // Grouped.with(Serdes.Integer() = is productId
        return (transactions, orders) ->
                transactions.selectKey((translationId, transaction) -> transaction.getSellOrderId())
                        .join(orders.selectKey((orderId, order) -> order.getId()),
                                valueJoiner, joinWindows, joinParams)
                        .groupBy((orderId, transactionTotalWithProduct) -> transactionTotalWithProduct.getProductId(),
                                Grouped.with(Serdes.Integer(), new JsonSerde<>(TransactionTotalWithProduct.class)))
                        .aggregate(initializer, aggregator,
                                Materialized.<Integer, TransactionTotal>as(storeSupplier)
                                        .withKeySerde(Serdes.Integer())
                                        .withValueSerde(new JsonSerde<>(TransactionTotal.class)))
                        .toStream()
                        .peek((productId, transactionTotal) -> log.info("Total per product key:{}, value:{}",
                                productId, transactionTotal));

    }

    @Bean
    public BiConsumer<KStream<Long, Transaction>, KStream<Long, Order>> latestPerProduct() {

        // join values

        JoinWindows joinWindows = JoinWindows.ofTimeDifferenceWithNoGrace(Duration.ofSeconds(10));

        // first value is transation and second is order and join result will be TransactionTotalWithProduct
        ValueJoiner<Transaction, Order, TransactionTotalWithProduct> valueJoiner = (transaction, order) ->
                new TransactionTotalWithProduct(transaction, order.getProductId());

        StreamJoined<Long, Transaction, Order> joinParams =
                StreamJoined.with(Serdes.Long(), new JsonSerde<>(Transaction.class), new JsonSerde<>(Order.class));


        // group by values
        WindowBytesStoreSupplier storeSupplier = Stores.persistentWindowStore(
                "latest-transactions-per-product-store", Duration.ofSeconds(30),Duration.ofSeconds(30), false);

        Initializer<TransactionTotal> initializer = TransactionTotal::new;

        // group by key is Integer transactionTotalWithProduct.getProductId() and value is transactionTotalWithProduct
        // aggregate result will be TransactionTotal

        Aggregator<Integer, TransactionTotalWithProduct, TransactionTotal> aggregator =
                (productId, transactionTotalWithProduct, transactionTotal) ->
                {
                    transactionTotal.setCount(transactionTotal.getCount() + 1);
                    transactionTotal.setProductCount(transactionTotal.getProductCount() +
                            transactionTotalWithProduct.getTransaction().getQuantity());
                    int prize = (transactionTotalWithProduct.getTransaction().getPrice() * transactionTotalWithProduct
                            .getTransaction().getQuantity());
                    transactionTotal.setPrize(transactionTotal.getPrize() + prize);
                    return transactionTotal;
                };

        // windowing. Here we are using Thumbing window
        // Group the records in 30 seconds time window. In 30 seconds windows how many records will come group those
        // all events
        TimeWindows timeWindows = TimeWindows.ofSizeWithNoGrace(Duration.ofSeconds(30));


        return (transactions, orders) ->
                transactions.selectKey((translationId, transaction) -> transaction.getSellOrderId())
                        .join(orders.selectKey((orderId, order) -> order.getId()),
                                valueJoiner, joinWindows, joinParams)
                        .groupBy((orderId, transactionTotalWithProduct) -> transactionTotalWithProduct.getProductId(),
                                Grouped.with(Serdes.Integer(), new JsonSerde<>(TransactionTotalWithProduct.class)))
                        .windowedBy(timeWindows)
                        .aggregate(initializer, aggregator,
                                Materialized.<Integer,TransactionTotal>
                                                as(storeSupplier)
                                        .withKeySerde(Serdes.Integer())
                                        .withValueSerde(new JsonSerde<>(TransactionTotal.class)))
                        .toStream()
                        .peek((productId, transactionTotal) -> log.info("Total per product last 30 seconds key:{}, value:{}",
                                productId, transactionTotal));

    }

    private Transaction execute(Order orderBuy, Order orderSell) {
        if (orderBuy.getPrice() >= orderSell.getPrice()) {
            int count = Math.min(orderBuy.getProductCount(), orderSell.getProductCount());
            boolean allowed = orderLogic.performUpdate(orderBuy.getId(), orderSell.getId(), count);
            if (allowed) {
                //  The final transaction price is an average of sell and buy order price.
                return new Transaction(++transactionId, orderBuy.getId(), orderSell.getId(),
                        Math.min(orderBuy.getProductCount(), orderSell.getProductCount()),
                        (orderBuy.getPrice() + orderSell.getPrice()) / 2,
                        LocalDateTime.now(),
                        "NEW");
            }
        }
        log.info("The transaction failed for this records orderBuy:{},orderSell:{},", orderBuy, orderSell);
        return null;
    }
}
