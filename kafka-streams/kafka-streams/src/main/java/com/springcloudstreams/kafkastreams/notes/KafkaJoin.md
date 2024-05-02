- join was the multiplication
- if we get 2 events from first topic and 3 events from second topic then the multiplication
will be 6 events as part of join operation.

````
@Bean
    public BiFunction<KStream<String,String>,KStream<String,String>,KStream<String, JoinedValue>> join()
    {
      return (input1,input2)->input1.join(input2,
              (value1,value2)->new JoinedValue(value1,value2),
              JoinWindows.ofTimeDifferenceWithNoGrace(Duration.of(10, ChronoUnit.SECONDS)),
              StreamJoined.with(Serdes.String(),Serdes.String(),Serdes.String())
        )
              .peek((key,value)->System.out.println("joined=>"+key+" "+value));
    }
````
- input1 join with input2 then from both inputs  (value1,value2) combined to
new JoinedValue(value1,value2).
- After join operation it needs put into kafka topic. Further that we need

StreamJoined.with(Serdes.String(),Serdes.String(),Serdes.String())

````
        join-in-0:
          destination: enhance-topic
        join-in-1:
          destination: aggregate-topic
        join-out-0:
          destination: join-output-topic
````
- Input from enhance-topic and second topic aggregate-topic combined into destination topic
  join-output-topic

Testing
````
Partition:0 : Offset:515 : FINANCE : {"value1":"madhura","value2":"3"}
Partition:0 : Offset:516 : FINANCE : {"value1":"madhura","value2":"3"}
Partition:0 : Offset:517 : FINANCE : {"value1":"madhura","value2":"3"}
Partition:0 : Offset:518 : FINANCE : {"value1":"madhura","value2":"3"}
Partition:0 : Offset:519 : TECH : {"value1":"madhura","value2":"3"}
Partition:0 : Offset:520 : TECH : {"value1":"madhura","value2":"3"}
Partition:0 : Offset:521 : TECH : {"value1":"madhura","value2":"3"}
Partition:0 : Offset:522 : TECH : {"value1":"madhura","value2":"3"}
Partition:0 : Offset:523 : TECH : {"value1":"madhura","value2":"3"}
Partition:0 : Offset:524 : TECH : {"value1":"madhura","value2":"3"}
Partition:0 : Offset:525 : TECH : {"value1":"madhura","value2":"3"}
````
If the default StringSerde we are using then we don't need to specify in application.yml file

````
join-in-0:
#              consumer:
#                keySerde: org.apache.kafka.common.serialization.Serdes$StringSerde
#                valueSerde: org.apache.kafka.common.serialization.Serdes$StringSerde
````

