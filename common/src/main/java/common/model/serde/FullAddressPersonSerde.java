package common.model.serde;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import common.model.FullAddressPerson;
import common.model.Person;
import org.apache.kafka.common.errors.SerializationException;
import org.apache.kafka.common.header.Headers;
import org.apache.kafka.common.serialization.Deserializer;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.common.serialization.Serializer;

import java.io.IOException;
import java.util.Map;


public class FullAddressPersonSerde extends Serdes.WrapperSerde<FullAddressPerson> {
    public FullAddressPersonSerde() {
        super(new FullAddressPersonSerializer(), new FullAddressPersonDeserializer());
    }

    public static class FullAddressPersonSerializer implements Serializer<FullAddressPerson> {
        private final ObjectMapper objectMapper = new ObjectMapper();

        @Override
        public byte[] serialize(String s, FullAddressPerson fullAddressPerson) {
            try {
                if (fullAddressPerson == null)
                    return new byte[0];
                else
                    return objectMapper.writeValueAsBytes(fullAddressPerson);
            } catch (JsonProcessingException e) {
                throw new SerializationException("Error when serializing string to byte[] due to JsonProcessingException!");
            }
        }

        @Override
        public byte[] serialize(String topic, Headers headers, FullAddressPerson data) {
            return serialize(topic, data);
        }

    }

    public static class FullAddressPersonDeserializer implements Deserializer<FullAddressPerson> {
        private final ObjectMapper objectMapper = new ObjectMapper();

        @Override
        public FullAddressPerson deserialize(String s, byte[] bytes) {
            try {
                if (bytes == null)
                    return null;
                else
                    return objectMapper.readValue(bytes, FullAddressPerson.class);
            } catch (IOException e) {
                e.printStackTrace();
                throw new SerializationException("Error when deserializing byte[] to string due to IO Exception");
            }
        }

        @Override
        public FullAddressPerson deserialize(String topic, Headers headers, byte[] data) {
            return deserialize(topic, data);
        }

    }


}
