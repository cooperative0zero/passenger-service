package com.modsen.software.passenger.service;

import com.modsen.software.passenger.dto.PassengerResponse;
import com.modsen.software.passenger.dto.PassengerRequest;

import java.util.List;

public interface PassengerService {
    PassengerResponse getById(Long id);

    List<PassengerResponse> getAll(Integer pageNumber, Integer pageSize, String sortBy, String sortOrder);

    PassengerResponse save(PassengerRequest request);

    void softDelete(Long id);

    PassengerResponse update(PassengerRequest request);
}
