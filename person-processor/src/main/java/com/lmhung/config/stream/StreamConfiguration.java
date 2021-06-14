package com.lmhung.config.stream;

import com.lmhung.common.model.Person;
import com.lmhung.common.model.serde.FullAddressPersonSerde;
import com.lmhung.common.model.serde.PersonSerde;
import com.lmhung.config.app.ApplicationConfig;
import com.lmhung.common.model.FullAddressPerson;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.Produced;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Properties;
import java.util.concurrent.CountDownLatch;

@Slf4j
@Service
public class StreamConfiguration {
    @Autowired
    ApplicationConfig config;

    @PostConstruct
    public void init(){
        this.startKafkaStreamsSynchronously(kafkaStreams());
    }

    private Properties streamConfigProperties() {
        final Properties props = new Properties();
        props.put(StreamsConfig.APPLICATION_ID_CONFIG, config.getAppId());
        props.put(StreamsConfig.CLIENT_ID_CONFIG, config.getClientId());
        props.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, config.getBootstrapServers());
        props.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass());
        props.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, PersonSerde.class);
        props.put(StreamsConfig.NUM_STREAM_THREADS_CONFIG, config.getNumStreamThread());
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, config.getAutoOfsetReset());
        return props;
    }

    private KafkaStreams kafkaStreams() {
        final var builder = new StreamsBuilder();
        final KStream<String, Person> kafkaStreams = builder.stream(config.getProvideTopic());
        kafkaStreams.mapValues(this::mapValue)
                .to(config.getDestinationTopic(), Produced.with(Serdes.String(), new FullAddressPersonSerde()));
        return new KafkaStreams(builder.build(), streamConfigProperties());
    }

    private void startKafkaStreamsSynchronously(final KafkaStreams streams) {
        final var latch = new CountDownLatch(1);
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
            throw new RuntimeException(e);
        }
    }

    private FullAddressPerson mapValue(Person person){
        var fullAddressPerson = new FullAddressPerson();
        fullAddressPerson.setPerson(person);
        return fullAddressPerson;
    }

}
