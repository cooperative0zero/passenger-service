package com.modsen.software.passenger.mapper;

import com.modsen.software.passenger.dto.PassengerRequest;
import com.modsen.software.passenger.entity.Passenger;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class PassengerRequestToPassenger implements Converter<PassengerRequest, Passenger> {

    @Override
    public Passenger convert(PassengerRequest source) {
        return Passenger.builder()
                .id(source.getId())
                .phone(source.getPhone())
                .email(source.getEmail())
                .fullName(source.getFullName())
                .isDeleted(source.getIsDeleted())
                .build();
    }
}
