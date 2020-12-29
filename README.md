# Kafka Streams

## About

This application 
 1. Takes a comma delimited topic of userid,colour
 2. Filters out bad data i.e. keep only colour of green,red and blue
 3. Gets the running count of the favourite colour overall and output this to a topic

This is developed using java 8 and kafka stream 2.7.0

## How to run

1. Setup kafka cluster on your local machine. Refer ```Kafka Cluster Setup``` section for help.

2. Create input and output compacted topics
   ```
   bin/kafka-topics.sh --create --zookeeper localhost:2181 --replication-factor 1 --partitions 1 --topic favourite-colour-input --config cleanup.policy=compact
   bin/kafka-topics.sh --create --zookeeper localhost:2181 --replication-factor 1 --partitions 1 --topic favourite-colour-output --config cleanup.policy=compact
   ```
3. Start this application

4. Produce message with key as userid and value as colour from kafka console producer
   ```
   bin/kafka-console-producer.sh --broker-list localhost:9092 --topic favourite-colour-input --property parse.key=true --property key.separator=,
   i.e.
   bin/kafka-console-producer.sh --broker-list localhost:9092 --topic favourite-colour-input --property parse.key=true --property key.separator=,
   >stephane,blue
   >john,green
   >stephane,red
   >alice,red
   ```
5. Consume messages from output topic
   ```
   bin/kafka-console-consumer.sh --bootstrap-server localhost:9092 \
     --topic favourite-colour-output \
     --from-beginning \
     --formatter kafka.tools.DefaultMessageFormatter \
     --property print.key=true \
     --property print.value=true \
     --property key.deserializer=org.apache.kafka.common.serialization.StringDeserializer \
     --property value.deserializer=org.apache.kafka.common.serialization.LongDeserializer
   red	5
   red	5
   green	3
   red	4
   blue	2
   blue	3
   blue	2
   ```


## Kafka Cluster Setup

#### Follow below steps to set up 3 node cluster on single Mac machine

* Download Kafka from ```https://kafka.apache.org/downloads```
* Extract it somewhere by executing tar command on Terminal

  ```i.e. tar -xvf kafka_2.13-2.6.0.tgz```
* Go to that extracted Kafka folder
   
  ```i.e. cd kafka_2.13-2.6.0/```
* Start zookeeper
   
  ```bin/zookeeper-server-start.sh config/zookeeper.properties```
  
  This will bring up zookeeper on default port 2181 configured in ```config/zookeeper.properties``` file
* Start first broker/node

  ```bin/kafka-server-start.sh config/server.properties```  
  
  This will start broker with below default broker id, log directory and port configured in ```config/server.properties```
  ```   
  broker.id=0  
  log.dirs=/tmp/kafka-logs  
  port=9092
  ``` 
* Create a copy of ```config/server.properties``` file for second broker/node
   
  ```i.e. cp config/server.properties config/server1.properties```
* Change broker id, log directory and port in ```config/server1.properties``` file
   
  ```
  broker.id=1
  log.dirs=/tmp/kafka-logs-1
  port=9093
  ```
* Start second broker/node

  ```bin/kafka-server-start.sh config/server1.properties```    
* Create one more copy of ```config/server.properties``` file for third broker/node

  ```i.e. cp config/server.properties config/server2.properties```
* Change broker id, log directory and port in ```config/server2.properties``` file
   
  ```
  broker.id=2
  log.dirs=/tmp/kafka-logs-2
  port=9094
  ```   
* Start third broker/node

   ```bin/kafka-server-start.sh config/server2.properties```     
   
* Check what brokers are up and running

  ```bin/zookeeper-shell.sh localhost:2181 ls /brokers/ids```  
  
  will give you below output
  
  ```
  Connecting to localhost:2181
 
  WATCHER::
  
  WatchedEvent state:SyncConnected type:None path:null
  [0, 1, 2]
  ``` 