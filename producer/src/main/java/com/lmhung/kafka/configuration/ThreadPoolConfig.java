package com.lmhung.kafka.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

@Configuration
public class ThreadPoolConfig {
    @Bean(name = "provider-threadPool")
    public ThreadPoolTaskExecutor providerThreadPool(){
        var executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(5);
        executor.setMaxPoolSize(10);
        executor.setThreadNamePrefix("kafka-producer-");
        return executor;
    }

    @Bean(name = "providerScheduler")
    public ThreadPoolTaskScheduler providerScheduler(){
        var scheduler = new ThreadPoolTaskScheduler();
        scheduler.setPoolSize(2);
        scheduler.setThreadNamePrefix("producer-scheduler-");
        return scheduler;
    }

}
