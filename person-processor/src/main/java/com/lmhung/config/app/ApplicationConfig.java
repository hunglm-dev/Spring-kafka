package com.lmhung.config.app;

import lombok.Data;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@ToString
@ConfigurationProperties(prefix = "app.consumer")
@Configuration
public class ApplicationConfig {
    private String appId;
    private String clientId;
    private String bootstrapServers;
    private int numStreamThread;
    private String autoOfsetReset;
    private String provideTopic;
    private String destinationTopic;
}
