- But hold on, just replacing the current thread may not solve your purpose. You may want to define a retry strategy before committing the offset back to Kafka and may not want to publish those records on the output topic. So how do we do that? The solution for that lies in defining the RetryTemplate and using the low-level processor API to “suggest” a commit. Remember by default auto-commit offset is set to false for KStreams and the offset commit is determined by the offset commit interval (You can read more about it in this Confluent Article).

So if StreamUncaughtExceptionHandler is not sufficient for me then what else I can do?

One possible thing which we could do is retry the message 3 times with an exponential back-off period and if the retries are exhausted, I would like to “request” a commit and then move forward with other messages on the topic.

Another possible solution is that whenever an error is encountered we route those messages to a separate topic and route the rest of the messages to the actual output topics.

Let us try to build those two solutions one by one.

- retry Message with Exponential Back-off
  Code for this is present in the “main-retry-branch”.

The key here is to move away from the High level KStream DSL and work with the low-level Processor API and define a custom Retry Template. The method which we will use here will “transform”(similar functionality can be achieved via the “process” method).

Our test case remains the same only the stream processor code will be changed.