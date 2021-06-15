package com.lmhung.kafka.task;

import com.lmhung.kafka.configuration.KafkaConfig;
import common.model.Person;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.stream.IntStream;

@Service
@Slf4j
public class ProviderTask {

    @Autowired
    @Qualifier("provider-threadPool")
    ThreadPoolTaskExecutor executor;
    @Autowired
    @Qualifier("providerScheduler")
    ThreadPoolTaskScheduler scheduler;
    @Autowired
    KafkaConfig kafkaConfig;
    @Autowired
    KafkaProducer<String, Person> producer;

    @PostConstruct
    public void initializeSchedule() {
        var cron = new CronTrigger("*/30 * * * * *");
        log.info("Initialize providerScheduler with cron {}", cron);
        var batchSize = 10;
        this.scheduler.schedule(
                () -> {
                    log.info("Send a punch of person...");
                    IntStream.range(0, batchSize)
                            .mapToObj(obj -> Person.randomValue())
                            .forEach(person -> this.executor.execute(this.submitToProducer(person)));
                }, cron
        );
    }

    private Runnable submitToProducer(Person person) {
        return () -> {
            log.info("Send person data: {}", person);
            this.producer.send(new ProducerRecord<>(kafkaConfig.getProviderTopic(), person));
            this.producer.flush();
        };
    }


}
