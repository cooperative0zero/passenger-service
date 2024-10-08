package com.modsen.software.passenger.kafka.consumer;

import com.modsen.software.passenger.kafka.configuration.KafkaTopics;
import com.modsen.software.passenger.kafka.event.BaseRatingEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RatingConsumer {

    @KafkaListener(topics = KafkaTopics.RATING_TOPIC, groupId = "passengerConsumerGroup")
    public void listenDrivers(BaseRatingEvent driverEvent) {
        System.out.println(driverEvent);
    }
}
