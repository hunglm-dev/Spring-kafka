package com.lmhung.kafka.configuration;

import lombok.Data;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

@ConfigurationProperties(prefix = "app.provider")
@Configuration
@Data
@Slf4j
@ToString
public class KafkaConfig {
    private String clientId;
    private String bootstrapServers;
    private String providerTopic;
    private int topicPartition;
    private short topicReplica;

    @PostConstruct
    public void init(){
       log.info("Init provider configuation: {}", this);
    }
}
