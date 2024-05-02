````
.\bin\windows\kafka-console-consumer.bat --bootstrap-server localhost:9092 --topic orders-buy 
--from-beginning -property "key.separator= :" --property "print.key=true" 
--key-deserializer=org.apache.kafka.common.serialization.LongDeserializer
````

0/p

3 :{"id":3,"customerId":3,"productId":100,"creationDate":"2024-02-15T12:17:12.384622","orderType":"BUY","amount":1030}
4 :{"id":4,"customerId":4,"productId":200,"creationDate":"2024-02-15T12:17:12.384622","orderType":"BUY","amount":1050}
5 :{"id":5,"customerId":5,"productId":200,"creationDate":"2024-02-15T12:17:12.384622","orderType":"BUY","amount":1000}
6 :{"id":6,"customerId":11,"productId":100,"creationDate":"2024-02-15T12:17:12.384622","orderType":"BUY","amount":1050}
1 :{"id":1,"customerId":1,"productId":100,"creationDate":"2024-02-15T12:18:56.6367786","orderType":"BUY","amount":1000}
2 :{"id":2,"customerId":2,"productId":200,"creationDate":"2024-02-15T12:18:56.6367786","orderType":"BUY","amount":1050}
3 :{"id":3,"customerId":3,"productId":100,"creationDate":"2024-02-15T12:18:56.6367786","orderType":"BUY","amount":1030}
4 :{"id":4,"customerId":4,"productId":200,"creationDate":"2024-02-15T12:18:56.6367786","orderType":"BUY","amount":1050}
5 :{"id":5,"customerId":5,"productId":200,"creationDate":"2024-02-15T12:18:56.6367786","orderType":"BUY","amount":1000}
6 :{"id":6,"customerId":11,"productId":100,"creationDate":"2024-02-15T12:18:56.6367786","orderType":"BUY","amount":1050}


o/p of order sell

7 :{"id":7,"customerId":6,"productId":200,"creationDate":"2024-02-15T12:17:12.384622","orderType":"SELL","amount":950}
8 :{"id":8,"customerId":7,"productId":100,"creationDate":"2024-02-15T12:17:12.384622","orderType":"SELL","amount":1000}
9 :{"id":9,"customerId":8,"productId":100,"creationDate":"2024-02-15T12:17:12.384622","orderType":"SELL","amount":1050}
10 :{"id":10,"customerId":9,"productId":300,"creationDate":"2024-02-15T12:17:12.384622","orderType":"SELL","amount":1000}
11 :{"id":11,"customerId":10,"productId":200,"creationDate":"2024-02-15T12:17:12.384622","orderType":"SELL","amount":1020}
7 :{"id":7,"customerId":6,"productId":200,"creationDate":"2024-02-15T12:18:56.6367786","orderType":"SELL","amount":950}
8 :{"id":8,"customerId":7,"productId":100,"creationDate":"2024-02-15T12:18:56.6367786","orderType":"SELL","amount":1000}
9 :{"id":9,"customerId":8,"productId":100,"creationDate":"2024-02-15T12:18:56.6367786","orderType":"SELL","amount":1050}
10 :{"id":10,"customerId":9,"productId":300,"creationDate":"2024-02-15T12:18:56.6367786","orderType":"SELL","amount":1000}
11 :{"id":11,"customerId":10,"productId":200,"creationDate":"2024-02-15T12:18:56.6367786","orderType":"SELL","amount":1020}