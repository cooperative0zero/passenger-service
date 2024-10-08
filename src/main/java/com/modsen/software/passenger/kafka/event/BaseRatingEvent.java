package com.modsen.software.passenger.kafka.event;

import lombok.Data;

@Data
public abstract class BaseRatingEvent {
    protected String type;
}
