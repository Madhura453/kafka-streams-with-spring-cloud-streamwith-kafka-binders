````
 @Bean
    public Function<KStream<String, Event>,KStream<String,String>> aggregate()
    {
        return input->input
                .groupByKey()
                .windowedBy(TimeWindows.ofSizeWithNoGrace(Duration.ofSeconds(10)))
                .aggregate(()->0l,
                        (key,value,aggregate)->aggregate+1,
                        Materialized.with(Serdes.String(),Serdes.Long()))
                .suppress(Suppressed.untilWindowCloses(Suppressed.BufferConfig.unbounded()))
                .toStream()
                .map((w,v)->new KeyValue<>(w.key(),v.toString()));
    }
````

- WithIn 10 seconds we are aggregating the results
````
        aggregate-in-0:
          destination: producer-topic
        aggregate-out-0:
          destination: aggregate-topic
````
- received events from producer-topic and aggregate result and aggregated result send to
  output topic is aggregate-topic
- aggregated based on key.
- initial aggregated value is 0l
- In time second window aggregate the count based on key
## suppress(Suppressed.untilWindowCloses(Suppressed.BufferConfig.unbounded()))
- if we  put suppress we can the aggregation not keep on emitting. 
- if we don't put we are keep on emitting not only suppression. It will not give correct o/p. It will 
- keep on send the information as an when it collects.
- we need to aggregate it get it to in 10 seconds time window. After 10 seconds only will emit the final 
result. Not intermittent result of aggregation.


````
      binder:
        configuration:
          commit.interval.ms: 2000
````
- This will start emitting the results as soon as aggregation complete. Otherwise it 
- will all batch all particular events send it once.


Testing
````
Partition:0 : Offset:133 : TECH : 2
Partition:0 : Offset:134 : BUSINESS : 1
Partition:0 : Offset:135 : FINANCE : 3
Partition:0 : Offset:136 : TECH : 6
Partition:0 : Offset:137 : BUSINESS : 3
Partition:0 : Offset:138 : FINANCE : 3
Partition:0 : Offset:139 : PRODUCT : 1
Partition:0 : Offset:140 : TECH : 2
Partition:0 : Offset:141 : BUSINESS : 5
Partition:0 : Offset:142 : FINANCE : 1
Partition:0 : Offset:143 : PRODUCT : 3
Partition:0 : Offset:144 : TECH : 1
Partition:0 : Offset:145 : BUSINESS : 4
````
- In above 10 times time window we can aggregate the result.
if we need custom serializer then we need to specify
````
            join-out-0:
               producer:
                 keyserde: org.apache.kafka.common.serialization.Serdes$StringSerde
                 valueSerde: com.springcloudstreams.kafkastreams.converters.JoinedValueSerDes
````
