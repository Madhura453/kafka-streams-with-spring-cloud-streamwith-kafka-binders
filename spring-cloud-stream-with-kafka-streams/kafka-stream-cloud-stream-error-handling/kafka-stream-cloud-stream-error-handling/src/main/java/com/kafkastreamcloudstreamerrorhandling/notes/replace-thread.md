- org.apache.kafka.streams.errors.StreamsException: Exception caught in process. taskId=0_0, processor=KSTREAM-SOURCE-0000000000, topic=raw-order-topic, partition=0, offset=0, stacktrace=com.kafkastreamcloudstreamerrorhandling.exception.InvalidCreditCardException: Invalid Credit Card Details provided.
  at com.kafkastreamcloudstreamerrorhandling.service.OrderProcessorService.maskCreditCardInformation(OrderProcessorService.java:20)
  at com.kafkastreamcloudstreamerrorhandling.service.OrderProcessorService.processOrderMsg(OrderProcessorService.java:9)
  at org