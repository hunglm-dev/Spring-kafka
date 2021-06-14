package com.lmhung.kafka.model.serde;

import com.lmhung.kafka.model.Person;
import org.apache.kafka.common.header.Headers;
import org.apache.kafka.common.serialization.Deserializer;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.common.serialization.Serializer;

import java.util.Map;

public class PersonSerde extends Serdes.WrapperSerde<Person> {

    public PersonSerde() {
        super(new PersonSerializer(), new PersonDeserializer());
    }

    public static class PersonSerializer implements Serializer<Person>{
        @Override
        public void configure(Map<String, ?> configs, boolean isKey) {
            Serializer.super.configure(configs, isKey);
        }

        @Override
        public byte[] serialize(String s, Person person) {
            return new byte[0];
        }

        @Override
        public byte[] serialize(String topic, Headers headers, Person data) {
            return Serializer.super.serialize(topic, headers, data);
        }

        @Override
        public void close() {
            Serializer.super.close();
        }
    }


    public static class PersonDeserializer implements Deserializer<Person>{
        @Override
        public Person deserialize(String s, byte[] bytes) {
            return null;
        }

        @Override
        public void configure(Map<String, ?> configs, boolean isKey) {
            Deserializer.super.configure(configs, isKey);
        }

        @Override
        public Person deserialize(String topic, Headers headers, byte[] data) {
            return Deserializer.super.deserialize(topic, headers, data);
        }

        @Override
        public void close() {
            Deserializer.super.close();
        }
    }
}
