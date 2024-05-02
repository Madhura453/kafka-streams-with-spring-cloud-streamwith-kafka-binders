package com.springcloudstreams.kafkastreams.aggregator;

import com.springcloudstreams.kafkastreams.dto.Event;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.Materialized;
import org.apache.kafka.streams.kstream.Suppressed;
import org.apache.kafka.streams.kstream.TimeWindows;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;
import java.util.function.Function;

@Configuration
public class KafkaAggregator {

    @Bean
    public Function<KStream<String, Event>,KStream<String,String>> aggregate()
    {
        return input->input
                .groupByKey()
                .windowedBy(TimeWindows.ofSizeWithNoGrace(Duration.ofSeconds(10)))
                .aggregate(()->0l,
                        (key,value,aggregate)->aggregate+1,
                        Materialized.with(Serdes.String(),Serdes.Long()))
                //  above is KTable
                .suppress(Suppressed.untilWindowCloses(Suppressed.BufferConfig.unbounded()))
                .toStream()
                .map((w,v)->new KeyValue<>(w.key(),v.toString()))
                //   above is Kstream<Windowd<String,Long>
               .peek((key, value) -> System.out.println("Aggregate->" + key + " " + value));
    }
}
