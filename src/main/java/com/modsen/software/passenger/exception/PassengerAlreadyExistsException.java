package com.modsen.software.passenger.exception;

import org.springframework.http.HttpStatus;

public class PassengerAlreadyExistsException extends BaseCustomException{

    public PassengerAlreadyExistsException(String message) {
        super(HttpStatus.BAD_REQUEST.value(), message);
    }
}
