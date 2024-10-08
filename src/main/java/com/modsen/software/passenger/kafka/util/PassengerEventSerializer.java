package com.modsen.software.passenger.kafka.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.modsen.software.passenger.kafka.event.BasePassengerEvent;
import org.apache.kafka.common.serialization.Serializer;

public class PassengerEventSerializer implements Serializer<BasePassengerEvent> {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public byte[] serialize(String s, BasePassengerEvent basePassengerEvent) {
        try {
            return objectMapper.writeValueAsBytes(basePassengerEvent);
        } catch (Exception e) {
            throw new RuntimeException("Error serializing MyCustomObject", e);
        }
    }
}
