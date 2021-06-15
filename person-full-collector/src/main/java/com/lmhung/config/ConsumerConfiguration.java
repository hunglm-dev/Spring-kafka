package com.lmhung.config;

import lombok.Data;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "app.consumer")
@Data
@ToString
public class ConsumerConfiguration {
    private String appId;
    private String bootstrapServers;
    private int numOfStreamThread;
    private String autoOfset;
    private String clientId;
    private String consumTopic;

}
