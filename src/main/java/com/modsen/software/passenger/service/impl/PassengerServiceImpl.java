package com.modsen.software.passenger.service.impl;

import com.modsen.software.passenger.entity.Passenger;
import com.modsen.software.passenger.dto.PassengerRequest;
import com.modsen.software.passenger.dto.PassengerResponse;
import com.modsen.software.passenger.exception.PassengerAlreadyExistsException;
import com.modsen.software.passenger.exception.PassengerNotExistsException;
import com.modsen.software.passenger.repository.PassengerRepository;
import com.modsen.software.passenger.service.PassengerService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.ConversionService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class PassengerServiceImpl implements PassengerService {

    private final PassengerRepository passengerRepository;

    private final ConversionService conversionService;

    @Override
    @Transactional(readOnly = true)
    public PassengerResponse getById(Long id) {
        var category = passengerRepository.findById(id)
                .orElseThrow(()-> new PassengerNotExistsException(String.format("Passenger with id = %d not exists", id)));
        return conversionService.convert(category, PassengerResponse.class);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PassengerResponse> getAll(Integer pageNumber, Integer pageSize, String sortBy, String sortOrder) {
        return passengerRepository.findAll(PageRequest.of(pageNumber, pageSize, Sort.by(Sort.Direction.fromString(sortOrder), sortBy)))
                .getContent()
                .stream()
                .map((o)-> conversionService.convert(o, PassengerResponse.class))
                .toList();
    }

    @Override
    @Transactional
    public PassengerResponse save(PassengerRequest request) {
        var passenger = Objects.requireNonNull(conversionService.convert(request, Passenger.class));

        if (passenger.getId() != null && passengerRepository.existsById(passenger.getId())) {
            throw new PassengerAlreadyExistsException(String.format("Passenger with id = %d already exists", passenger.getId()));
        } else {
            passengerRepository.save(passenger);
        }

        return conversionService.convert(passenger, PassengerResponse.class);
    }

    @Override
    @Transactional
    public void softDelete(Long id) {
        if (passengerRepository.softDelete(id) == 0) {
            throw new PassengerNotExistsException(String.format("Passenger with id = %d not exists", id));
        }
    }

    @Override
    @Transactional
    public PassengerResponse update(PassengerRequest request) {
        if (!passengerRepository.existsById(request.getId()))
            throw new PassengerNotExistsException(String.format("Passenger with id = %d not exists", request.getId()));

        var updated = Objects.requireNonNull(conversionService.convert(request, Passenger.class));

        return conversionService.convert(passengerRepository.save(updated), PassengerResponse.class);
    }
}