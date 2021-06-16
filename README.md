# Example for Spring-boot and Apache Kafka

## Resources
1. Apache Kafka
> For more infomation about Apache Kafka,     
 please visit [Home Page](https://kafka.apache.org/) and download 

This project was used Apache Kafka version **2.8**

To start Kafka server, you must start the Zookeeper first:
```shell
${KAFKA_DIR}\bin\windows\zookeeper-server-start.bat .\config\zookeeper.properties
```
Then, start the kafka server:
```shell
${KAFKA_DIR}\bin\windows\kafka-server-start.bat .\config\server.properties
```
2. Java

Project using Java 11 and Spring boot framework with kafka dependecies:
```xml
<dependencies>
  <dependency>
      <groupId>org.apache.kafka</groupId>
      <artifactId>kafka-streams</artifactId>
  </dependency>
  <dependency>
      <groupId>org.springframework.kafka</groupId>
      <artifactId>spring-kafka</artifactId>
  </dependency>
</dependencies>
```

- **Producer**
   - Produce person infomation to the *kafka-topic* (specifically, here is *'person-provider*')
   - Can change the configuration in the [application.yml](producer/src/main/resources/application.yml)
   - Start using IDE or ``` mvn spring-boot:run```
    
- **Person-Procesor**
   - Simulator matching the person sent from **Producer** with adress
    and send to another kafkatopic (specifically, here is *'person-full'*)
   - Can change the configuration in [application.yml](person-processor/src/main/resources/application.yml)
- **Person-full-collector**
   - Collect full-information of the person sent from **Person-Processor**
   - Depending on the bussines you can use the received data for some purpose.
   - Also, change the the configuration in [application.yml](person-full-collector/src/main/resources/application.yml)
    
## License

Spring Kafka is released under the terms of the Apache Software License Version 2.0.

    





