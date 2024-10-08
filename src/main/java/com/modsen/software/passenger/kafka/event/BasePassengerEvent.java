package com.modsen.software.passenger.kafka.event;

import lombok.Data;

@Data
public abstract class BasePassengerEvent {
    protected Long passengerId;
    protected String type;
}
