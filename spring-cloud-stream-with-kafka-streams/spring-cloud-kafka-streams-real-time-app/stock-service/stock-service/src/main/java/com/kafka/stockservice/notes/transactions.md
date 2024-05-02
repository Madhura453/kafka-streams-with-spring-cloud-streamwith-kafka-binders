````
 @Bean
    public BiFunction<KStream<Long, Order>, KStream<Long, Order>, KStream<Long, Transaction>> transactions() {
        JoinWindows joinWindows = JoinWindows.ofTimeDifferenceWithNoGrace(Duration.ofSeconds(10));
        // For joining kstream-Kstream we required join-window. Because Kstream is infinity stream
        StreamJoined<Integer, Order, Order> joinParams = StreamJoined.with(Serdes.Integer(),
                new JsonSerde<>(Order.class), new JsonSerde<>(Order.class));
        // first one key and second is value and third one is value

        //ValueJoiner<Order,Order,Transaction> valueJoiner = (buyOrder, sellOrder) -> execute(buyOrder,sellOrder);
        //

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
````
- selectKey((aLong, buyOrder) -> buyOrder.getProductId()) 
- Joining is based on ProductId
- in place of this::execute we can use valueJoiner
- checking if the transaction value is null or not
- map again changing the key value pair to new one= KStream<Long, Transaction>