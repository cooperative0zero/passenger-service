package com.modsen.software.passenger.kafka.util;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.modsen.software.passenger.kafka.event.BaseRatingEvent;
import com.modsen.software.passenger.kafka.event.DriverRatingRecalculated;
import com.modsen.software.passenger.kafka.event.PassengerRatingRecalculated;
import org.apache.kafka.common.serialization.Deserializer;

import java.util.Map;

public class RatingDeserializer implements Deserializer<BaseRatingEvent> {
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public BaseRatingEvent deserialize(String topic, byte[] data) {
        try {
            Map<String, Object> map = objectMapper.readValue(data, new TypeReference<>() {});
            String type = (String) map.get("type");

            if ("PassengerRatingRecalculated".contentEquals(type)) {
                return objectMapper.readValue(data, PassengerRatingRecalculated.class);
            } else if ("DriverRatingRecalculated".contentEquals(type)) {
                return objectMapper.readValue(data, DriverRatingRecalculated.class);
            }

            throw new IllegalArgumentException("Unknown type: " + type);
        } catch (Exception e) {
            throw new RuntimeException("Error deserializing ", e);
        }
    }
}
