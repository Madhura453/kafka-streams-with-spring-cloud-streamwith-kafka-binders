package com.springcloudstreams.kafkastreams.converters;

import com.springcloudstreams.kafkastreams.dto.Event;
import org.springframework.kafka.support.serializer.JsonSerde;

public class EventSerDes extends JsonSerde<Event> {
}
