package com.modsen.software.passenger.kafka.consumer;

import com.modsen.software.passenger.kafka.configuration.KafkaTopics;
import com.modsen.software.passenger.kafka.event.BaseRideEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.logging.Logger;

@Service
@RequiredArgsConstructor
public class RideConsumer {

    @KafkaListener(topics = KafkaTopics.RIDES_TOPIC, groupId = "passengerConsumerGroup")
    public void listenRides(BaseRideEvent rideEvent) {
        Logger.getGlobal().info(rideEvent.toString());
    }
}
