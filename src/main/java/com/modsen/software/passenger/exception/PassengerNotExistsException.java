package com.modsen.software.passenger.exception;

import org.springframework.http.HttpStatus;

public class PassengerNotExistsException extends BaseCustomException {
    public PassengerNotExistsException(String message) {
        super(HttpStatus.NOT_FOUND.value(), message);
    }
}
