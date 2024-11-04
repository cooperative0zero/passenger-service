package com.modsen.software.passenger.util;

import com.modsen.software.passenger.dto.PassengerRequest;
import com.modsen.software.passenger.dto.PassengerResponse;
import com.modsen.software.passenger.entity.Passenger;

public final class Constants {
    public static final Passenger passenger;
    public static final Passenger anotherPassenger;
    public static final PassengerRequest passengerRequest;
    public static final PassengerResponse passengerResponse;
    public static final PassengerResponse anotherPassengerResponse;

    static {
        passenger = Passenger.builder()
                .id(1L)
                .email("example@mail.com")
                .isDeleted(false)
                .phone("987654321")
                .fullName("First Middle Last")
                .rating(1f)
                .build();

        anotherPassenger = Passenger.builder()
                .id(2L)
                .email("example2@mail.com")
                .isDeleted(false)
                .phone("123456789")
                .fullName("First2 Middle2 Last2")
                .rating(2f)
                .build();

        passengerRequest = new PassengerRequest();
        passengerRequest.setId(1L);
        passengerRequest.setFullName("First Middle Last");
        passengerRequest.setEmail("example@mail.com");
        passengerRequest.setPhone("987654321");
        passengerRequest.setRating(1f);
        passengerRequest.setIsDeleted(false);

        passengerResponse = PassengerResponse.builder()
                .id(1L)
                .email("example@mail.com")
                .isDeleted(false)
                .phone("987654321")
                .fullName("First Middle Last")
                .rating(1f)
                .build();

        anotherPassengerResponse = PassengerResponse.builder()
                .id(2L)
                .email("example2@mail.com")
                .isDeleted(false)
                .phone("123456789")
                .fullName("First2 Middle2 Last2")
                .rating(2f)
                .build();
    }
}
