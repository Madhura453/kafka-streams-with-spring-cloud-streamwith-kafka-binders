package com.springcloud.kafkabinderbasic.notes;

public class Notes {
    /*

    For using spring cloud there is no need of any configuration once we created beans for
    producer, function, consumer it will start connecting to default messaging kafka broker
    processor will fetch data from one topic perform its logic and then send new/modified data to other topic

      producerBinding-out-0:
          destination: processor-topic

          producer don't have in because it generates only data.
only configuration we need if you want to change topic names
    Function<String,String> will consume input and provide output

            consumerBinding-in-0:
          destination: consumer-topic

          above two lines telling to kafka binding don't create topic with the name  consumerBinding-in-0
           create the topic with this name  consumer-topic

           consumer don't have out. it consumes the message
     */


}
