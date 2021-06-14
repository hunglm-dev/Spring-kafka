package com.lmhung.common.model.serde;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lmhung.common.model.Person;
import org.apache.kafka.common.errors.SerializationException;
import org.apache.kafka.common.header.Headers;
import org.apache.kafka.common.serialization.Deserializer;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.common.serialization.Serializer;

import java.io.IOException;

public class PersonSerde extends Serdes.WrapperSerde<Person> {

    public PersonSerde() {
        super(new PersonSerializer(), new PersonDeserializer());
    }

    public static class PersonSerializer implements Serializer<Person> {
        private final ObjectMapper objectMapper = new ObjectMapper();

        @Override
        public byte[] serialize(String s, Person person) {
            try {
                if (person == null)
                    return new byte[0];
                else
                    return objectMapper.writeValueAsBytes(person);
            } catch (JsonProcessingException e) {
                throw new SerializationException("Error when serializing string to byte[] due to JsonProcessingException!");
            }
        }

        @Override
        public byte[] serialize(String topic, Headers headers, Person data) {
            return serialize(topic, data);
        }
    }
    public static class PersonDeserializer implements Deserializer<Person> {
        private final ObjectMapper objectMapper = new ObjectMapper();
        @Override
        public Person deserialize(String s, byte[] bytes) {
            try {
                if (bytes == null)
                    return null;
                else
                    return objectMapper.readValue(bytes, Person.class);
            } catch (IOException e) {
                e.printStackTrace();
                throw new SerializationException("Error when deserializing byte[] to string due to IO Exception");
            }
        }

        @Override
        public Person deserialize(String topic, Headers headers, byte[] data) {
            return deserialize(topic, data);
        }
    }
}
