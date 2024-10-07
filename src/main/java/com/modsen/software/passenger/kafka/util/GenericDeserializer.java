package com.modsen.software.passenger.kafka.util;

import com.modsen.software.passenger.kafka.configuration.KafkaTopics;
import org.apache.kafka.common.header.Headers;
import org.springframework.kafka.support.serializer.JsonDeserializer;

public class GenericDeserializer extends JsonDeserializer<Object> {

    public GenericDeserializer() {}

    @Override
    public Object deserialize(String topic, Headers headers, byte[] data)
    {
        switch (topic)
        {
            case KafkaTopics.RIDES_TOPIC:
                try (RideDeserializer driverDeserializer = new RideDeserializer()) {
                    return driverDeserializer.deserialize(topic, data);
                }
            case KafkaTopics.RATING_TOPIC:
                try (RatingDeserializer ratingDeserializer = new RatingDeserializer()) {
                    return ratingDeserializer.deserialize(topic, data);
                }

        }
        return super.deserialize(topic, data);
    }
}
