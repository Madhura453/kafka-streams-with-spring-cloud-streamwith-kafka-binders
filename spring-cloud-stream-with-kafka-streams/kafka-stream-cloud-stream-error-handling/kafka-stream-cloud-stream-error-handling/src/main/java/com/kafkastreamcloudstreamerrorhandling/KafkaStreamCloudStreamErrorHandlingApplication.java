package com.kafkastreamcloudstreamerrorhandling;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.core.KafkaTemplate;

@SpringBootApplication
public class KafkaStreamCloudStreamErrorHandlingApplication {

	@Autowired
	private static KafkaTemplate<String, String> stringStringKafkaTemplate;

	@Value("${test-input-topic}")
	private static String inputTopic;

	public static void main(String[] args) {
		SpringApplication.run(KafkaStreamCloudStreamErrorHandlingApplication.class, args);
		stringStringKafkaTemplate.send(inputTopic, "My First Message.");
	}
}
