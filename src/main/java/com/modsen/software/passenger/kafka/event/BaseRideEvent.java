package com.modsen.software.passenger.kafka.event;

import lombok.Data;

@Data
public abstract class BaseRideEvent {
    protected Long rideId;
    protected String type;
}
