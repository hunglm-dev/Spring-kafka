package com.lmhung.kafka.configuration;

import com.lmhung.common.model.Person;
import com.lmhung.common.model.serde.PersonSerde;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Properties;

@Configuration
public class KafkaProducerConfig {
    @Autowired
    KafkaConfig config;

    @Bean
    public KafkaProducer<String, Person> kafkaProducer() {
        return new KafkaProducer<>(streamConfiguationProperties());
    }

    private Properties streamConfiguationProperties() {
        final var props = new Properties();
        props.put(ProducerConfig.CLIENT_ID_CONFIG, config.getClientId());
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, config.getBootstrapServers());
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, PersonSerde.PersonSerializer.class);
        return props;
    }


    @Bean
    public NewTopic providerTopic() {
        return new NewTopic(config.getProviderTopic(), config.getTopicPartition(), config.getTopicReplica());
    }

}
