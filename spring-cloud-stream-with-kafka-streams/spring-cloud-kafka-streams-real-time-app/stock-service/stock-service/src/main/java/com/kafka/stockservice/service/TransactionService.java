package com.kafka.stockservice.service;

import com.kafka.stockservice.model.TransactionTotal;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.map.MultiKeyMap;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.kstream.Windowed;
import org.apache.kafka.streams.state.KeyValueIterator;
import org.apache.kafka.streams.state.QueryableStoreTypes;
import org.apache.kafka.streams.state.ReadOnlyKeyValueStore;
import org.apache.kafka.streams.state.ReadOnlyWindowStore;
import org.springframework.cloud.stream.binder.kafka.streams.InteractiveQueryService;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

@Service
@Slf4j
public class TransactionService {

    private InteractiveQueryService interactiveQueryService;

    public TransactionService(InteractiveQueryService interactiveQueryService) {
        this.interactiveQueryService = interactiveQueryService;
    }

    public TransactionTotal getAllTransactionsSummary() {
        ReadOnlyKeyValueStore<String, TransactionTotal> keyValueStore =
                interactiveQueryService.getQueryableStore("all-transactions-store",
                        QueryableStoreTypes.keyValueStore());
        return keyValueStore.get("NEW");
        /*
        In totalTransactions
        The key was NEW. we grouped the records based on NEW only.
        groupBy((key, transaction) -> transaction.getStatus(),
         */
    }

    public TransactionTotal getSummaryByProductId(Integer productId) {
        ReadOnlyKeyValueStore<Integer, TransactionTotal> keyValueStore =
                interactiveQueryService.getQueryableStore("transactions-per-product-store",
                        QueryableStoreTypes.keyValueStore());
        return keyValueStore.get(productId);
    }

    public Map<Integer, TransactionTotal> getSummaryByAllProducts() {
        ReadOnlyKeyValueStore<Integer, TransactionTotal> keyValueStore =
                interactiveQueryService.getQueryableStore("transactions-per-product-store",
                        QueryableStoreTypes.keyValueStore());
        KeyValueIterator<Integer, TransactionTotal> keyValueIterator = keyValueStore.all();

        Spliterator<KeyValue<Integer, TransactionTotal>> spliterators =
                Spliterators.spliteratorUnknownSize(keyValueIterator, 0);

        Stream<KeyValue<Integer, TransactionTotal>> transactionTotalStream =
                StreamSupport.stream(spliterators, false);

        Map<Integer, TransactionTotal> transactionTotalAllProducts =
                transactionTotalStream.collect(Collectors.toMap(keyValue -> keyValue.key,
                        keyValue -> keyValue.value));

        return transactionTotalAllProducts;
    }

    public List<TransactionTotal> getLatestSummaryByProductId(Integer productId) {
        ReadOnlyWindowStore<Integer, TransactionTotal> windowKeyValueStore =
                interactiveQueryService.getQueryableStore("latest-transactions-per-product-store",
                        QueryableStoreTypes.windowStore());
        KeyValueIterator<Windowed<Integer>, TransactionTotal> windowKeyValueStoreIterator = windowKeyValueStore.all();
        Spliterator<KeyValue<Windowed<Integer>, TransactionTotal>> spliterator = Spliterators.spliteratorUnknownSize(windowKeyValueStoreIterator,
                0);
        return StreamSupport.stream(spliterator, false)
                .filter(windowedTransactionTotalKeyValue -> windowedTransactionTotalKeyValue.key.key().equals(productId))
                .map(keyValue -> keyValue.value).collect(Collectors.toList());
    }

    public Map<Windowed<Integer>, TransactionTotal> getLatestSummaryByAllProducts() {
        ReadOnlyWindowStore<Integer, TransactionTotal> windowKeyValueStore =
                interactiveQueryService.getQueryableStore("latest-transactions-per-product-store",
                        QueryableStoreTypes.windowStore());
        KeyValueIterator<Windowed<Integer>, TransactionTotal> windowKeyValueStoreIterator = windowKeyValueStore.all();
        Spliterator<KeyValue<Windowed<Integer>, TransactionTotal>> spliterator = Spliterators.spliteratorUnknownSize(windowKeyValueStoreIterator,
                0);
        Stream<KeyValue<Windowed<Integer>, TransactionTotal>> keyValueStream = StreamSupport.stream(spliterator, false);

        // Here because windows contains multiple values with same key
        // windows will create with same key
        MultiKeyMap transactionTotalMultiKeyMap =new MultiKeyMap<>();
        return keyValueStream
                .collect(Collectors.toMap(keyValue -> keyValue.key,keyValue -> keyValue.value));
    }
}
