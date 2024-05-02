orders-buy=o/p

.\bin\windows\kafka-console-consumer.bat --bootstrap-server localhost:9092 --topic orders-buy --from-beginning -property "key.separator= :" --property "print.key=true" --key-deserializer=org.apache.kafka.common.serialization.LongDeserializer
[2024-02-16 15:01:30,368] WARN [Consumer clientId=consumer-console-consumer-29810-1, groupId=console-consumer-29810] Error while fetching metadata with correlation id 2 : {orders-buy=LEADER_NOT_AVAILABLE} (org.apache.kafka.clients.NetworkClient)
1 :{"id":1,"customerId":1,"productId":1,"productCount":100,"creationDate":"2024-02-16T15:02:39.5083705","type":"BUY","price":1000}
2 :{"id":2,"customerId":2,"productId":1,"productCount":200,"creationDate":"2024-02-16T15:02:39.5083705","type":"BUY","price":1050}
3 :{"id":3,"customerId":3,"productId":1,"productCount":100,"creationDate":"2024-02-16T15:02:39.5083705","type":"BUY","price":1030}
4 :{"id":4,"customerId":4,"productId":1,"productCount":200,"creationDate":"2024-02-16T15:02:39.5083705","type":"BUY","price":1050}
5 :{"id":5,"customerId":5,"productId":1,"productCount":200,"creationDate":"2024-02-16T15:02:39.5083705","type":"BUY","price":1000}
6 :{"id":6,"customerId":11,"productId":1,"productCount":100,"creationDate":"2024-02-16T15:02:39.5083705","type":"BUY","price":1050}

orders-sell=o/p

.\bin\windows\kafka-console-consumer.bat --bootstrap-server localhost:9092 --topic orders-sell --from-beginning -property "key.separator= :" --property "print.key=true" --key-deserializer=org.apache.kafka.common.serialization.LongDeserializer
[2024-02-16 15:01:14,111] WARN [Consumer clientId=consumer-console-consumer-43252-1, groupId=console-consumer-43252] Error while fetching metadata with correlation id 2 : {orders-sell=LEADER_NOT_AVAILABLE} (org.apache.kafka.clients.NetworkClient)
[2024-02-16 15:01:14,221] WARN [Consumer clientId=consumer-console-consumer-43252-1, groupId=console-consumer-43252] Error while fetching metadata with correlation id 4 : {orders-sell=LEADER_NOT_AVAILABLE} (org.apache.kafka.clients.NetworkClient)
7 :{"id":7,"customerId":6,"productId":1,"productCount":200,"creationDate":"2024-02-16T15:02:39.5083705","type":"SELL","price":950}
8 :{"id":8,"customerId":7,"productId":1,"productCount":100,"creationDate":"2024-02-16T15:02:39.5083705","type":"SELL","price":1000}
9 :{"id":9,"customerId":8,"productId":1,"productCount":100,"creationDate":"2024-02-16T15:02:39.5083705","type":"SELL","price":1050}
10 :{"id":10,"customerId":9,"productId":1,"productCount":300,"creationDate":"2024-02-16T15:02:39.5083705","type":"SELL","price":1000}
11 :{"id":11,"customerId":10,"productId":1,"productCount":200,"creationDate":"2024-02-16T15:02:39.5083705","type":"SELL","price":1020}

transactions=o/p

.\bin\windows\kafka-console-consumer.bat --bootstrap-server localhost:9092 --topic transactions --from-beginning -property "key.separator= :" --property "print.key=true" --key-deserializer=org.apache.kafka.common.serialization.LongDeserializer
[2024-02-16 15:01:57,243] WARN [Consumer clientId=consumer-console-consumer-3553-1, groupId=console-consumer-3553] Error while fetching metadata with correlation id 2 : {transactions=LEADER_NOT_AVAILABLE} (org.apache.kafka.clients.NetworkClient)
1 :{"id":1,"buyOrderId":1,"sellOrderId":7,"quantity":100,"price":975,"creationTime":[2024,2,16,15,2,42,818069700],"status":"NEW"}
2 :{"id":2,"buyOrderId":2,"sellOrderId":8,"quantity":100,"price":1025,"creationTime":[2024,2,16,15,2,43,761558300],"status":"NEW"}
3 :{"id":3,"buyOrderId":4,"sellOrderId":9,"quantity":100,"price":1050,"creationTime":[2024,2,16,15,2,45,768324600],"status":"NEW"}
4 :{"id":4,"buyOrderId":3,"sellOrderId":10,"quantity":100,"price":1015,"creationTime":[2024,2,16,15,2,45,792692800],"status":"NEW"}
5 :{"id":5,"buyOrderId":6,"sellOrderId":11,"quantity":100,"price":1035,"creationTime":[2024,2,16,15,2,47,843862000],"status":"NEW"}



The transaction failed for this records orderBuy  occurences = 25

translation key: 5 

25+5 = 30.

so that means totally 30 times joining will be there= 30 times product id will match


# totalTransactions testing

2024-02-16T17:42:55.294+05:30  INFO 10260 --- [stock-service] [-StreamThread-1] c.k.stockservice.service.StockService    : The transactionTotal was key : NEW and value is : TransactionTotal(count=5, productCount=500, prize=510000)

TransactionTotal(count=5, productCount=500, prize=510000)

TotalTranslation no. Of records= 5 so count is =5
total quantity in above translation was "quantity":100*5=500
totalPrize was in above translation was ""
int prize = (transaction.getPrice()*transaction.getQuantity());
transactionTotal.setPrize(transactionTotal.getPrize()+prize);
1st = 975*100= 97500= 0+97500=97500
2nd= 1025*100= 102500 = 97500+102500= 200000
3rd= 1050*100= 105000 = 200000+105000=305000
4th= 1015*100 = 101500 = 305000+101500= 406500
5th= 1035*100 = 103500 = 406500+103500 = 510000


2024-02-18T10:44:50.351+05:30  INFO 18032 --- [stock-service] [-StreamThread-1] c.k.stockservice.service.StockService    : The transactionTotal was key : NEW and value is : TransactionTotal(count=5, productCount=500, prize=510000)
2024-02-18T10:44:50.428+05:30  INFO 18032 --- [stock-service] [-StreamThread-1] c.k.stockservice.service.StockService    : Total per product key:1, value:TransactionTotal(count=5, productCount=500, prize=510000)
2024-02-18T10:44:50.458+05:30  INFO 18032 --- [stock-service] [-StreamThread-1] c.k.stockservice.service.StockService    : Total per product last 30 seconds key:[1@1708233240000/1708233270000], value:TransactionTotal(count=5, productCount=500, prize=510000)
1@1708233240000/1708233270000] = windowed key

// Because There was only one product


2024-02-18T10:44:15.811+05:30  INFO 18032 --- [stock-service] [-StreamThread-1] c.k.stockservice.service.StockService    : merge order saved : 1,Order{id=1, customerId=1, productId=1, productCount=100, realizedCount=0, creationDate=2024-02-18T10:44:12.576706200, type=BUY, amount=1000}
2024-02-18T10:44:15.923+05:30  INFO 18032 --- [stock-service] [-StreamThread-1] c.k.stockservice.service.StockService    : merge order saved : 7,Order{id=7, customerId=6, productId=1, productCount=200, realizedCount=0, creationDate=2024-02-18T10:44:12.577800100, type=SELL, amount=950}
2024-02-18T10:44:16.016+05:30  INFO 18032 --- [stock-service] [-StreamThread-1] c.k.stockservice.service.StockService    : translation key: 1 and value: Transaction{id=1, buyOrderId=1, sellOrderId=7, amount=100, price=975, creationTime=2024-02-18T10:44:16.015872, status='NEW'}
2024-02-18T10:44:16.115+05:30  INFO 18032 --- [stock-service] [-StreamThread-1] c.k.stockservice.service.StockService    : aggregate transation:        ,value:Transaction{id=1, buyOrderId=1, sellOrderId=7, amount=100, price=975, creationTime=2024-02-18T10:44:16.015872, status='NEW'}
2024-02-18T10:44:16.763+05:30  INFO 18032 --- [stock-service] [-StreamThread-1] c.k.stockservice.service.StockService    : merge order saved : 2,Order{id=2, customerId=2, productId=1, productCount=200, realizedCount=0, creationDate=2024-02-18T10:44:12.576706200, type=BUY, amount=1050}
2024-02-18T10:44:16.782+05:30  INFO 18032 --- [stock-service] [-StreamThread-1] c.k.stockservice.service.StockService    : merge order saved : 8,Order{id=8, customerId=7, productId=1, productCount=100, realizedCount=0, creationDate=2024-02-18T10:44:12.577800100, type=SELL, amount=1000}
2024-02-18T10:44:16.895+05:30  INFO 18032 --- [stock-service] [-StreamThread-1] c.k.stockservice.service.StockService    : The transaction failed for this records orderBuy:Order{id=2, customerId=2, productId=1, productCount=200, realizedCount=0, creationDate=2024-02-18T10:44:12.576706200, type=BUY, amount=1050},orderSell:Order{id=7, customerId=6, productId=1, productCount=200, realizedCount=0, creationDate=2024-02-18T10:44:12.577800100, type=SELL, amount=950},
2024-02-18T10:44:16.903+05:30  INFO 18032 --- [stock-service] [-StreamThread-1] c.k.stockservice.service.StockService    : The transaction failed for this records orderBuy:Order{id=1, customerId=1, productId=1, productCount=100, realizedCount=0, creationDate=2024-02-18T10:44:12.576706200, type=BUY, amount=1000},orderSell:Order{id=8, customerId=7, productId=1, productCount=100, realizedCount=0, creationDate=2024-02-18T10:44:12.577800100, type=SELL, amount=1000},
2024-02-18T10:44:16.913+05:30  INFO 18032 --- [stock-service] [-StreamThread-1] c.k.stockservice.service.StockService    : translation key: 2 and value: Transaction{id=2, buyOrderId=2, sellOrderId=8, amount=100, price=1025, creationTime=2024-02-18T10:44:16.913127700, status='NEW'}
2024-02-18T10:44:17.007+05:30  INFO 18032 --- [stock-service] [-StreamThread-1] c.k.stockservice.service.StockService    : aggregate transation:        ,value:Transaction{id=2, buyOrderId=2, sellOrderId=8, amount=100, price=1025, creationTime=2024-02-18T10:44:16.913127700, status='NEW'}
2024-02-18T10:44:17.767+05:30  INFO 18032 --- [stock-service] [-StreamThread-1] c.k.stockservice.service.StockService    : merge order saved : 3,Order{id=3, customerId=3, productId=1, productCount=100, realizedCount=0, creationDate=2024-02-18T10:44:12.576706200, type=BUY, amount=1030}
2024-02-18T10:44:17.790+05:30  INFO 18032 --- [stock-service] [-StreamThread-1] c.k.stockservice.service.StockService    : merge order saved : 9,Order{id=9, customerId=8, productId=1, productCount=100, realizedCount=0, creationDate=2024-02-18T10:44:12.577800100, type=SELL, amount=1050}
2024-02-18T10:44:17.902+05:30  INFO 18032 --- [stock-service] [-StreamThread-1] c.k.stockservice.service.StockService    : The transaction failed for this records orderBuy:Order{id=3, customerId=3, productId=1, productCount=100, realizedCount=0, creationDate=2024-02-18T10:44:12.576706200, type=BUY, amount=1030},orderSell:Order{id=7, customerId=6, productId=1, productCount=200, realizedCount=0, creationDate=2024-02-18T10:44:12.577800100, type=SELL, amount=950},
2024-02-18T10:44:17.909+05:30  INFO 18032 --- [stock-service] [-StreamThread-1] c.k.stockservice.service.StockService    : The transaction failed for this records orderBuy:Order{id=3, customerId=3, productId=1, productCount=100, realizedCount=0, creationDate=2024-02-18T10:44:12.576706200, type=BUY, amount=1030},orderSell:Order{id=8, customerId=7, productId=1, productCount=100, realizedCount=0, creationDate=2024-02-18T10:44:12.577800100, type=SELL, amount=1000},
2024-02-18T10:44:17.910+05:30  INFO 18032 --- [stock-service] [-StreamThread-1] c.k.stockservice.service.StockService    : The transaction failed for this records orderBuy:Order{id=1, customerId=1, productId=1, productCount=100, realizedCount=0, creationDate=2024-02-18T10:44:12.576706200, type=BUY, amount=1000},orderSell:Order{id=9, customerId=8, productId=1, productCount=100, realizedCount=0, creationDate=2024-02-18T10:44:12.577800100, type=SELL, amount=1050},
2024-02-18T10:44:17.915+05:30  INFO 18032 --- [stock-service] [-StreamThread-1] c.k.stockservice.service.StockService    : The transaction failed for this records orderBuy:Order{id=2, customerId=2, productId=1, productCount=200, realizedCount=0, creationDate=2024-02-18T10:44:12.576706200, type=BUY, amount=1050},orderSell:Order{id=9, customerId=8, productId=1, productCount=100, realizedCount=0, creationDate=2024-02-18T10:44:12.577800100, type=SELL, amount=1050},
2024-02-18T10:44:17.916+05:30  INFO 18032 --- [stock-service] [-StreamThread-1] c.k.stockservice.service.StockService    : The transaction failed for this records orderBuy:Order{id=3, customerId=3, productId=1, productCount=100, realizedCount=0, creationDate=2024-02-18T10:44:12.576706200, type=BUY, amount=1030},orderSell:Order{id=9, customerId=8, productId=1, productCount=100, realizedCount=0, creationDate=2024-02-18T10:44:12.577800100, type=SELL, amount=1050},
2024-02-18T10:44:18.778+05:30  INFO 18032 --- [stock-service] [-StreamThread-1] c.k.stockservice.service.StockService    : merge order saved : 4,Order{id=4, customerId=4, productId=1, productCount=200, realizedCount=0, creationDate=2024-02-18T10:44:12.577800100, type=BUY, amount=1050}
2024-02-18T10:44:18.808+05:30  INFO 18032 --- [stock-service] [-StreamThread-1] c.k.stockservice.service.StockService    : merge order saved : 10,Order{id=10, customerId=9, productId=1, productCount=300, realizedCount=0, creationDate=2024-02-18T10:44:12.577800100, type=SELL, amount=1000}
2024-02-18T10:44:18.914+05:30  INFO 18032 --- [stock-service] [-StreamThread-1] c.k.stockservice.service.StockService    : The transaction failed for this records orderBuy:Order{id=4, customerId=4, productId=1, productCount=200, realizedCount=0, creationDate=2024-02-18T10:44:12.577800100, type=BUY, amount=1050},orderSell:Order{id=7, customerId=6, productId=1, productCount=200, realizedCount=0, creationDate=2024-02-18T10:44:12.577800100, type=SELL, amount=950},
2024-02-18T10:44:18.921+05:30  INFO 18032 --- [stock-service] [-StreamThread-1] c.k.stockservice.service.StockService    : The transaction failed for this records orderBuy:Order{id=4, customerId=4, productId=1, productCount=200, realizedCount=0, creationDate=2024-02-18T10:44:12.577800100, type=BUY, amount=1050},orderSell:Order{id=8, customerId=7, productId=1, productCount=100, realizedCount=0, creationDate=2024-02-18T10:44:12.577800100, type=SELL, amount=1000},
2024-02-18T10:44:18.943+05:30  INFO 18032 --- [stock-service] [-StreamThread-1] c.k.stockservice.service.StockService    : translation key: 3 and value: Transaction{id=3, buyOrderId=4, sellOrderId=9, amount=100, price=1050, creationTime=2024-02-18T10:44:18.943555800, status='NEW'}
2024-02-18T10:44:18.950+05:30  INFO 18032 --- [stock-service] [-StreamThread-1] c.k.stockservice.service.StockService    : The transaction failed for this records orderBuy:Order{id=1, customerId=1, productId=1, productCount=100, realizedCount=0, creationDate=2024-02-18T10:44:12.576706200, type=BUY, amount=1000},orderSell:Order{id=10, customerId=9, productId=1, productCount=300, realizedCount=0, creationDate=2024-02-18T10:44:12.577800100, type=SELL, amount=1000},
2024-02-18T10:44:18.955+05:30  INFO 18032 --- [stock-service] [-StreamThread-1] c.k.stockservice.service.StockService    : The transaction failed for this records orderBuy:Order{id=2, customerId=2, productId=1, productCount=200, realizedCount=0, creationDate=2024-02-18T10:44:12.576706200, type=BUY, amount=1050},orderSell:Order{id=10, customerId=9, productId=1, productCount=300, realizedCount=0, creationDate=2024-02-18T10:44:12.577800100, type=SELL, amount=1000},
2024-02-18T10:44:18.964+05:30  INFO 18032 --- [stock-service] [-StreamThread-1] c.k.stockservice.service.StockService    : translation key: 4 and value: Transaction{id=4, buyOrderId=3, sellOrderId=10, amount=100, price=1015, creationTime=2024-02-18T10:44:18.964566200, status='NEW'}
2024-02-18T10:44:18.970+05:30  INFO 18032 --- [stock-service] [-StreamThread-1] c.k.stockservice.service.StockService    : The transaction failed for this records orderBuy:Order{id=4, customerId=4, productId=1, productCount=200, realizedCount=0, creationDate=2024-02-18T10:44:12.577800100, type=BUY, amount=1050},orderSell:Order{id=10, customerId=9, productId=1, productCount=300, realizedCount=0, creationDate=2024-02-18T10:44:12.577800100, type=SELL, amount=1000},
2024-02-18T10:44:19.064+05:30  INFO 18032 --- [stock-service] [-StreamThread-1] c.k.stockservice.service.StockService    : aggregate transation:        ,value:Transaction{id=3, buyOrderId=4, sellOrderId=9, amount=100, price=1050, creationTime=2024-02-18T10:44:18.943555800, status='NEW'}
2024-02-18T10:44:19.065+05:30  INFO 18032 --- [stock-service] [-StreamThread-1] c.k.stockservice.service.StockService    : aggregate transation:        ,value:Transaction{id=4, buyOrderId=3, sellOrderId=10, amount=100, price=1015, creationTime=2024-02-18T10:44:18.964566200, status='NEW'}
2024-02-18T10:44:19.791+05:30  INFO 18032 --- [stock-service] [-StreamThread-1] c.k.stockservice.service.StockService    : merge order saved : 5,Order{id=5, customerId=5, productId=1, productCount=200, realizedCount=0, creationDate=2024-02-18T10:44:12.577800100, type=BUY, amount=1000}
2024-02-18T10:44:19.812+05:30  INFO 18032 --- [stock-service] [-StreamThread-1] c.k.stockservice.service.StockService    : merge order saved : 11,Order{id=11, customerId=10, productId=1, productCount=200, realizedCount=0, creationDate=2024-02-18T10:44:12.577800100, type=SELL, amount=1020}
2024-02-18T10:44:19.910+05:30  INFO 18032 --- [stock-service] [-StreamThread-1] c.k.stockservice.service.StockService    : The transaction failed for this records orderBuy:Order{id=5, customerId=5, productId=1, productCount=200, realizedCount=0, creationDate=2024-02-18T10:44:12.577800100, type=BUY, amount=1000},orderSell:Order{id=7, customerId=6, productId=1, productCount=200, realizedCount=0, creationDate=2024-02-18T10:44:12.577800100, type=SELL, amount=950},
2024-02-18T10:44:19.916+05:30  INFO 18032 --- [stock-service] [-StreamThread-1] c.k.stockservice.service.StockService    : The transaction failed for this records orderBuy:Order{id=5, customerId=5, productId=1, productCount=200, realizedCount=0, creationDate=2024-02-18T10:44:12.577800100, type=BUY, amount=1000},orderSell:Order{id=8, customerId=7, productId=1, productCount=100, realizedCount=0, creationDate=2024-02-18T10:44:12.577800100, type=SELL, amount=1000},
2024-02-18T10:44:19.916+05:30  INFO 18032 --- [stock-service] [-StreamThread-1] c.k.stockservice.service.StockService    : The transaction failed for this records orderBuy:Order{id=5, customerId=5, productId=1, productCount=200, realizedCount=0, creationDate=2024-02-18T10:44:12.577800100, type=BUY, amount=1000},orderSell:Order{id=9, customerId=8, productId=1, productCount=100, realizedCount=0, creationDate=2024-02-18T10:44:12.577800100, type=SELL, amount=1050},
2024-02-18T10:44:19.921+05:30  INFO 18032 --- [stock-service] [-StreamThread-1] c.k.stockservice.service.StockService    : The transaction failed for this records orderBuy:Order{id=5, customerId=5, productId=1, productCount=200, realizedCount=0, creationDate=2024-02-18T10:44:12.577800100, type=BUY, amount=1000},orderSell:Order{id=10, customerId=9, productId=1, productCount=300, realizedCount=0, creationDate=2024-02-18T10:44:12.577800100, type=SELL, amount=1000},
2024-02-18T10:44:19.922+05:30  INFO 18032 --- [stock-service] [-StreamThread-1] c.k.stockservice.service.StockService    : The transaction failed for this records orderBuy:Order{id=1, customerId=1, productId=1, productCount=100, realizedCount=0, creationDate=2024-02-18T10:44:12.576706200, type=BUY, amount=1000},orderSell:Order{id=11, customerId=10, productId=1, productCount=200, realizedCount=0, creationDate=2024-02-18T10:44:12.577800100, type=SELL, amount=1020},
2024-02-18T10:44:19.926+05:30  INFO 18032 --- [stock-service] [-StreamThread-1] c.k.stockservice.service.StockService    : The transaction failed for this records orderBuy:Order{id=2, customerId=2, productId=1, productCount=200, realizedCount=0, creationDate=2024-02-18T10:44:12.576706200, type=BUY, amount=1050},orderSell:Order{id=11, customerId=10, productId=1, productCount=200, realizedCount=0, creationDate=2024-02-18T10:44:12.577800100, type=SELL, amount=1020},
2024-02-18T10:44:19.931+05:30  INFO 18032 --- [stock-service] [-StreamThread-1] c.k.stockservice.service.StockService    : The transaction failed for this records orderBuy:Order{id=3, customerId=3, productId=1, productCount=100, realizedCount=0, creationDate=2024-02-18T10:44:12.576706200, type=BUY, amount=1030},orderSell:Order{id=11, customerId=10, productId=1, productCount=200, realizedCount=0, creationDate=2024-02-18T10:44:12.577800100, type=SELL, amount=1020},
2024-02-18T10:44:19.935+05:30  INFO 18032 --- [stock-service] [-StreamThread-1] c.k.stockservice.service.StockService    : The transaction failed for this records orderBuy:Order{id=4, customerId=4, productId=1, productCount=200, realizedCount=0, creationDate=2024-02-18T10:44:12.577800100, type=BUY, amount=1050},orderSell:Order{id=11, customerId=10, productId=1, productCount=200, realizedCount=0, creationDate=2024-02-18T10:44:12.577800100, type=SELL, amount=1020},
2024-02-18T10:44:19.936+05:30  INFO 18032 --- [stock-service] [-StreamThread-1] c.k.stockservice.service.StockService    : The transaction failed for this records orderBuy:Order{id=5, customerId=5, productId=1, productCount=200, realizedCount=0, creationDate=2024-02-18T10:44:12.577800100, type=BUY, amount=1000},orderSell:Order{id=11, customerId=10, productId=1, productCount=200, realizedCount=0, creationDate=2024-02-18T10:44:12.577800100, type=SELL, amount=1020},
2024-02-18T10:44:20.289+05:30  INFO 18032 --- [stock-service] [-StreamThread-1] c.k.stockservice.service.StockService    : The transactionTotal was key : NEW and value is : TransactionTotal(count=4, productCount=400, prize=406500)
2024-02-18T10:44:20.350+05:30  INFO 18032 --- [stock-service] [-StreamThread-1] c.k.stockservice.service.StockService    : Total per product key:1, value:TransactionTotal(count=4, productCount=400, prize=406500)
2024-02-18T10:44:20.416+05:30  INFO 18032 --- [stock-service] [-StreamThread-1] o.a.k.s.s.i.RocksDBTimestampedStore      : Opening store latest-transactions-per-product-store.1708233240000 in regular mode
2024-02-18T10:44:20.418+05:30  INFO 18032 --- [stock-service] [-StreamThread-1] c.k.stockservice.service.StockService    : Total per product last 30 seconds key:[1@1708233240000/1708233270000], value:TransactionTotal(count=4, productCount=400, prize=406500)
2024-02-18T10:44:20.806+05:30  INFO 18032 --- [stock-service] [-StreamThread-1] c.k.stockservice.service.StockService    : merge order saved : 6,Order{id=6, customerId=11, productId=1, productCount=100, realizedCount=0, creationDate=2024-02-18T10:44:12.577800100, type=BUY, amount=1050}
2024-02-18T10:44:20.927+05:30  INFO 18032 --- [stock-service] [-StreamThread-1] c.k.stockservice.service.StockService    : The transaction failed for this records orderBuy:Order{id=6, customerId=11, productId=1, productCount=100, realizedCount=0, creationDate=2024-02-18T10:44:12.577800100, type=BUY, amount=1050},orderSell:Order{id=7, customerId=6, productId=1, productCount=200, realizedCount=0, creationDate=2024-02-18T10:44:12.577800100, type=SELL, amount=950},
2024-02-18T10:44:20.950+05:30  INFO 18032 --- [stock-service] [-StreamThread-1] c.k.stockservice.service.StockService    : The transaction failed for this records orderBuy:Order{id=6, customerId=11, productId=1, productCount=100, realizedCount=0, creationDate=2024-02-18T10:44:12.577800100, type=BUY, amount=1050},orderSell:Order{id=8, customerId=7, productId=1, productCount=100, realizedCount=0, creationDate=2024-02-18T10:44:12.577800100, type=SELL, amount=1000},
2024-02-18T10:44:20.958+05:30  INFO 18032 --- [stock-service] [-StreamThread-1] c.k.stockservice.service.StockService    : The transaction failed for this records orderBuy:Order{id=6, customerId=11, productId=1, productCount=100, realizedCount=0, creationDate=2024-02-18T10:44:12.577800100, type=BUY, amount=1050},orderSell:Order{id=9, customerId=8, productId=1, productCount=100, realizedCount=0, creationDate=2024-02-18T10:44:12.577800100, type=SELL, amount=1050},
2024-02-18T10:44:20.966+05:30  INFO 18032 --- [stock-service] [-StreamThread-1] c.k.stockservice.service.StockService    : The transaction failed for this records orderBuy:Order{id=6, customerId=11, productId=1, productCount=100, realizedCount=0, creationDate=2024-02-18T10:44:12.577800100, type=BUY, amount=1050},orderSell:Order{id=10, customerId=9, productId=1, productCount=300, realizedCount=0, creationDate=2024-02-18T10:44:12.577800100, type=SELL, amount=1000},
2024-02-18T10:44:21.007+05:30  INFO 18032 --- [stock-service] [-StreamThread-1] c.k.stockservice.service.StockService    : translation key: 5 and value: Transaction{id=5, buyOrderId=6, sellOrderId=11, amount=100, price=1035, creationTime=2024-02-18T10:44:21.007633, status='NEW'}
2024-02-18T10:44:21.115+05:30  INFO 18032 --- [stock-service] [-StreamThread-1] c.k.stockservice.service.StockService    : aggregate transation:        ,value:Transaction{id=5, buyOrderId=6, sellOrderId=11, amount=100, price=1035, creationTime=2024-02-18T10:44:21.007633, status='NEW'}
2024-02-18T10:44:45.908+05:30  INFO 18032 --- [stock-service] [fcea6515b-admin] org.apache.kafka.clients.NetworkClient   : [AdminClient clientId=totalTransactionsPerProductId1-8e7f989b-9e03-4877-b24d-d95fcea6515b-admin] Node -1 disconnected.
2024-02-18T10:44:46.124+05:30  INFO 18032 --- [stock-service] [7f6e57701-admin] org.apache.kafka.clients.NetworkClient   : [AdminClient clientId=latestPerProductId1-f61c2fc8-e24c-4a72-a713-af07f6e57701-admin] Node -1 disconnected.
2024-02-18T10:44:46.173+05:30  INFO 18032 --- [stock-service] [c9aae8ee9-admin] org.apache.kafka.clients.NetworkClient   : [AdminClient clientId=totalTransactionsId1-d7f4fa7c-e266-4b19-9e11-c0fc9aae8ee9-admin] Node -1 disconnected.
2024-02-18T10:44:46.189+05:30  INFO 18032 --- [stock-service] [991154f29-admin] org.apache.kafka.clients.NetworkClient   : [AdminClient clientId=transactionsId1-c79eb1a3-baf2-4ff1-b277-3c0991154f29-admin] Node -1 disconnected.
2024-02-18T10:44:50.351+05:30  INFO 18032 --- [stock-service] [-StreamThread-1] c.k.stockservice.service.StockService    : The transactionTotal was key : NEW and value is : TransactionTotal(count=5, productCount=500, prize=510000)
2024-02-18T10:44:50.428+05:30  INFO 18032 --- [stock-service] [-StreamThread-1] c.k.stockservice.service.StockService    : Total per product key:1, value:TransactionTotal(count=5, productCount=500, prize=510000)
2024-02-18T10:44:50.458+05:30  INFO 18032 --- [stock-service] [-StreamThread-1] c.k.stockservice.service.StockService    : Total per product last 30 seconds key:[1@1708233240000/1708233270000], value:TransactionTotal(count=5, productCount=500, prize=510000)
2024-02-18T10:45:50.266+05:30  INFO 18032 --- [stock-service] [-StreamThread-1] o.a.k.s.p.internals.StreamThread         : stream-thread [consumeOrdersId1-4d99c014-5219-4e9e-9b7d-63683dc7899f-StreamThread-1] Processed 11 total records, ran 0 punctuators, and committed 2 total tasks since the last update
2024-02-18T10:45:50.313+05:30  INFO 18032 --- [stock-service] [-StreamThread-1] o.a.k.s.p.internals.StreamThread         : stream-thread [totalTransactionsPerProductId1-8e7f989b-9e03-4877-b24d-d95fcea6515b-StreamThread-1] Processed 25 total records, ran 0 punctuators, and committed 7 total tasks since the last update
2024-02-18T10:45:50.344+05:30  INFO 18032 --- [stock-service] [-StreamThread-1] o.a.k.s.p.internals.StreamThread         : stream-thread [latestPerProductId1-f61c2fc8-e24c-4a72-a713-af07f6e57701-StreamThread-1] Processed 25 total records, ran 0 punctuators, and committed 7 total tasks since the last update
2024-02-18T10:45:50.344+05:30  INFO 18032 --- [stock-service] [-StreamThread-1] o.a.k.s.p.internals.StreamThread         : stream-thread [transactionsId1-c79eb1a3-baf2-4ff1-b277-3c0991154f29-StreamThread-1] Processed 22 total records, ran 0 punctuators, and committed 5 total tasks since the last update
2024-02-18T10:45:50.359+05:30  INFO 18032 --- [stock-service] [-StreamThread-1] o.a.k.s.p.internals.StreamThread         : stream-thread [totalTransactionsId1-d7f4fa7c-e266-4b19-9e11-c0fc9aae8ee9-StreamThread-1] Processed 10 total records, ran 0 punctuators, and committed 4 total tasks since the last update
2024-02-18T10:45:50.547+05:30  INFO 18032 --- [stock-service] [83dc7899f-admin] org.apache.kafka.clients.NetworkClient   : [AdminClient clientId=consumeOrdersId1-4d99c014-5219-4e9e-9b7d-63683dc7899f-admin] Node -1 disconnected.
2024-02-18T10:47:50.278+05:30  INFO 18032 --- [stock-service] [-StreamThread-1] o.a.k.s.p.internals.StreamThread         : stream-thread [consumeOrdersId1-4d99c014-5219-4e9e-9b7d-63683dc7899f-StreamThread-1] Processed 0 total records, ran 0 punctuators, and committed 0 total tasks since the last update
2024-02-18T10:47:50.355+05:30  INFO 18032 --- [stock-service] [-StreamThread-1] o.a.k.s.p.internals.StreamThread         : stream-thread [latestPerProductId1-f61c2fc8-e24c-4a72-a713-af07f6e57701-StreamThread-1] Processed 0 total records, ran 0 punctuators, and committed 0 total tasks since the last update
2024-02-18T10:47:50.371+05:30  INFO 18032 --- [stock-service] [-StreamThread-1] o.a.k.s.p.internals.StreamThread         : stream-thread [transactionsId1-c79eb1a3-baf2-4ff1-b277-3c0991154f29-StreamThread-1] Processed 0 total records, ran 0 punctuators, and committed 0 total tasks since the last update
2024-02-18T10:47:50.401+05:30  INFO 18032 --- [stock-service] [-StreamThread-1] o.a.k.s.p.internals.StreamThread         : stream-thread [totalTransactionsId1-d7f4fa7c-e266-4b19-9e11-c0fc9aae8ee9-StreamThread-1] Processed 0 total records, ran 0 punctuators, and committed 0 total tasks since the last update
2024-02-18T10:47:50.401+05:30  INFO 18032 --- [stock-service] [-StreamThread-1] o.a.k.s.p.internals.StreamThread         : stream-thread [totalTransactionsPerProductId1-8e7f989b-9e03-4877-b24d-d95fcea6515b-StreamThread-1] Processed 0 total records, ran 0 punctuators, and committed 0 total tasks since the last update
2024-02-18T10:48:46.066+05:30  INFO 18032 --- [stock-service] [-StreamThread-1] org.apache.kafka.clients.NetworkClient   : [Consumer clientId=consumeOrdersId1-4d99c014-5219-4e9e-9b7d-63683dc7899f-StreamThread-1-consumer, groupId=consumeOrdersId1] Node -1 disconnected.
2024-02-18T10:48:46.144+05:30  INFO 18032 --- [stock-service] [-StreamThread-1] org.apache.kafka.clients.NetworkClient   : [Consumer clientId=totalTransactionsPerProductId1-8e7f989b-9e03-4877-b24d-d95fcea6515b-StreamThread-1-consumer, groupId=totalTransactionsPerProductId1] Node -1 disconnected.
2024-02-18T10:48:46.222+05:30  INFO 18032 --- [stock-service] [-StreamThread-1] org.apache.kafka.clients.NetworkClient   : [Consume