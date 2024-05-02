````
Function<KStream<String, Event>,KStream<String,String>>
````

- converting event value to string

````
        enhancer-in-0:
          destination: producer-topic
        enhancer-out-0:
          destination: enhance-topic
````
- enhancer input is producer-topic. and enhancer-out-0 is enhance-topic 

````
spring:
  cloud:
    function:
      definition: producer;enhancer;aggregate;join
````
- It will register that particular beans producer;enhancer;aggregate;join 

o/p

````
BUSINESS : madhura
BUSINESS : madhura
BUSINESS : madhura
BUSINESS : madhura
PRODUCT : madhura
PRODUCT : madhura
BUSINESS : madhura
BUSINESS : madhura
PRODUCT : madhura
PRODUCT : madhura
FINANCE : madhura
PRODUCT : madhura
TECH : madhura
BUSINESS : madhura
TECH : madhura
FINANCE : madhura
PRODUCT : madhura
FINANCE : madhura
FINANCE : madhura
PRODUCT : madhura
````