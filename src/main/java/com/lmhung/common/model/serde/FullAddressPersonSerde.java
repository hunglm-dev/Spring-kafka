package com.lmhung.common.model.serde;

import com.lmhung.common.model.FullAddressPerson;
import org.apache.kafka.common.header.Headers;
import org.apache.kafka.common.serialization.Deserializer;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.common.serialization.Serializer;

import java.util.Map;

public class FullAddressPersonSerde extends Serdes.WrapperSerde<FullAddressPerson> {
    public FullAddressPersonSerde() {
        super(new FullAddressPersonSerializer(), new FullAddressPersonDeserializer());
    }

    public static class FullAddressPersonSerializer implements Serializer<FullAddressPerson> {
        @Override
        public byte[] serialize(String s, FullAddressPerson fullAddressPerson) {
            return new byte[0];
        }

        @Override
        public void configure(Map<String, ?> configs, boolean isKey) {
            Serializer.super.configure(configs, isKey);
        }

        @Override
        public byte[] serialize(String topic, Headers headers, FullAddressPerson data) {
            return Serializer.super.serialize(topic, headers, data);
        }

        @Override
        public void close() {
            Serializer.super.close();
        }
    }

    public static class FullAddressPersonDeserializer implements Deserializer<FullAddressPerson> {
        @Override
        public FullAddressPerson deserialize(String s, byte[] bytes) {
            return null;
        }

        @Override
        public void configure(Map<String, ?> configs, boolean isKey) {
            Deserializer.super.configure(configs, isKey);
        }

        @Override
        public FullAddressPerson deserialize(String topic, Headers headers, byte[] data) {
            return Deserializer.super.deserialize(topic, headers, data);
        }

        @Override
        public void close() {
            Deserializer.super.close();
        }
    }


}
