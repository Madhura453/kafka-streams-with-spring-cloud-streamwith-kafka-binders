-  If there are two sources, we have to use BiConsumer
-  There are two input topics, so we need to map their names. Also, if we have more than one functional bean 
  we need to set applicationId related to the particular function. 

````
 WindowBytesStoreSupplier storeSupplier = Stores.persistentWindowStore(
                "latest-transactions-per-product-store", Duration.ofSeconds(30), Duration.ofSeconds(30), 
                false);
````
- Meaning was 
- Certainly! Let's break down the meaning of each parameter in the Stores.persistentWindowStore method:

- "latest-transactions-per-product-store": This is the name you've given to the window store. 
- It's a unique identifier within your Kafka Streams application, used to reference this specific store.

Duration.ofSeconds(30): This parameter defines the size of the time windows in the windowed store. 
In this case, each window will cover a duration of 30 seconds.

- Duration.ofSeconds(30): This parameter specifies the retention period for each window in the store. 
- It means that data within each window will be retained for 30 seconds before being discarded.

- false: This parameter determines whether duplicate keys are allowed within each window. 
 Setting it to false means that duplicate keys will not be retained. If set to true, 
- duplicate keys would be stored within the same window.

- In summary, this line of code creates a persistent window store named 
"latest-transactions-per-product-store" with windows spanning 30 seconds each, 
retaining data for 30 seconds, and not allowing duplicate keys within each window. 
This store can be used in Kafka Streams applications for tasks such as aggregating and processing 
 data within specified time windows.