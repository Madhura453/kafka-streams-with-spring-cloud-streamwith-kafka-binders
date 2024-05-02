package com.springcloudstreams.kafkastreams.join;

import com.springcloudstreams.kafkastreams.converters.JoinedValue;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.kstream.JoinWindows;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.StreamJoined;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.function.BiFunction;

@Configuration
public class KafkaJoin {

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
}
