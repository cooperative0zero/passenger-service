package com.modsen.software.passenger.kafka.event;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class SelectionDriverEvent extends BaseRideEvent{
    public SelectionDriverEvent(Long rideId) {
        this.rideId = rideId;
        this.type = this.getClass().getSimpleName();
    }
}
