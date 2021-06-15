package com.lmhung.stream;

import com.lmhung.config.ConsumerConfiguration;
import common.model.FullAddressPerson;
import common.model.serde.FullAddressPersonSerde;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.kstream.KStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.util.Properties;
import java.util.concurrent.CountDownLatch;

@Configuration
@Slf4j
public class PersonFullStream {

    @Autowired
    ConsumerConfiguration config;

    @PostConstruct
    public void initStream() {
        log.info("Initialize Consumer Stream with config: {}", config);
        this.startKafkaStreamsSynchronously(kafkaStreams());
    }

    private Properties streamConfigProperties() {
        final var props = new Properties();
        props.put(StreamsConfig.APPLICATION_ID_CONFIG, config.getAppId());
        props.put(StreamsConfig.CLIENT_ID_CONFIG, config.getClientId());
        props.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, config.getBootstrapServers());
        props.put(StreamsConfig.NUM_STREAM_THREADS_CONFIG, config.getNumOfStreamThread());
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, config.getAutoOfset());
        props.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass());
        props.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, FullAddressPersonSerde.class);
        return props;
    }

    private KafkaStreams kafkaStreams() {
        final var builder = new StreamsBuilder();
        final KStream<String, FullAddressPerson> kafkaStreams = builder.stream(config.getConsumTopic());
        kafkaStreams.foreach((key, value) -> {
            log.info("Receive Final Person: {}", value);
        });
        return new KafkaStreams(builder.build(), streamConfigProperties());
    }

    private void startKafkaStreamsSynchronously(final KafkaStreams streams) {
        final CountDownLatch latch = new CountDownLatch(1);
        streams.setStateListener((newState, oldState) -> {
            if (oldState == KafkaStreams.State.REBALANCING && newState == KafkaStreams.State.RUNNING) {
                latch.countDown();
            }
        });
        streams.start();
        try {
            latch.await();
        } catch (final InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

}
