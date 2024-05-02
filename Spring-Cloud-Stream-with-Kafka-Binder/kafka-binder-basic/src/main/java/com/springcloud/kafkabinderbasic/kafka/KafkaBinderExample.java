package com.springcloud.kafkabinderbasic.kafka;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;
@Configuration
public class KafkaBinderExample {

    private static final Logger LOGGER= LoggerFactory.getLogger(KafkaBinderExample.class);


    //Producer will send data to topic

    @Bean
    public Supplier<String> producerBinding()
    {
        return() ->{

        try {
            Thread.sleep(1);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
       return "Hey, java project i need at any cost";
    };

    }

    // Processor will fetch data from one topic perform its logic and then send new/modified data to other topic.

    /*
    if there is no topics and no configuration(in application.yml file)
     spring cloud default creates 2 topics one for input topic
    2nd topic for output
    processorBinding-in-0 =input topic
    processorBinding-out-0 = output topic  . 0=is index
    processorBinding= method name
    in= input topic
     */
    @Bean
    public Function<String,String> processorBinding()
    {

        return s->s+"::"+System.currentTimeMillis();
    }

    /*
        Consumer will fetch data from topic

        it will create topic with name
        Tf there is no(application.yml file) it will create new topic with name consumerBinding-in-0:
        consumerBinding = methodName
        in = input topic
        0 = 0thIndex
     */
    @Bean
    public Consumer<String> consumerBinding()
    {

        return s->LOGGER.info("Data consumed :: "+s.toUpperCase());
    }
}
