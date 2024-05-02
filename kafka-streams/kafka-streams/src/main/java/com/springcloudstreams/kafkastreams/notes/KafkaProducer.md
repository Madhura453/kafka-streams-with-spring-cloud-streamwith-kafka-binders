````
spring:
  cloud:
    function:
      definition: producer;enhancer;aggregate;join
````
- producer;enhancer;aggregate;join method names

````
 public Supplier<Message<Event>> producer()
````
-  ```producer-out-0:```
- producer = method name
- out= we are send out message
- in producer only 0 th index will be there

producer send messages
````
TECH : {"name":"madhura","department":"TECH"}
BUSINESS : {"name":"madhura","department":"BUSINESS"}
FINANCE : {"name":"madhura","department":"FINANCE"}
PRODUCT : {"name":"madhura","department":"PRODUCT"}
FINANCE : {"name":"madhura","department":"FINANCE"}
PRODUCT : {"name":"madhura","department":"PRODUCT"}
TECH : {"name":"madhura","department":"TECH"}
BUSINESS : {"name":"madhura","department":"BUSINESS"}
TECH : {"name":"madhura","department":"TECH"}
PRODUCT : {"name":"madhura","department":"PRODUCT"}
FINANCE : {"name":"madhura","department":"FINANCE"}
TECH : {"name":"madhura","department":"TECH"}
BUSINESS : {"name":"madhura","department":"BUSINESS"}
BUSINESS : {"name":"madhura","department":"BUSINESS"}
TECH : {"name":"madhura","department":"TECH"}
BUSINESS : {"name":"madhura","department":"BUSINESS"}
FINANCE : {"name":"madhura","department":"FINANCE"}
````