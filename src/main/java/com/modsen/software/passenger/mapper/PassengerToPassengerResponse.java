package com.modsen.software.passenger.mapper;

import com.modsen.software.passenger.dto.PassengerResponse;
import com.modsen.software.passenger.entity.Passenger;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class PassengerToPassengerResponse implements Converter <Passenger, PassengerResponse>{

    @Override
    public PassengerResponse convert(Passenger source) {
        return PassengerResponse.builder()
                .id(source.getId())
                .email(source.getEmail())
                .phone(source.getPhone())
                .fullName(source.getFullName())
                .isDeleted(source.getIsDeleted())
                .build();
    }
}
