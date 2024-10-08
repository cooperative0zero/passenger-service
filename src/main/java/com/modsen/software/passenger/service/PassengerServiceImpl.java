package com.modsen.software.passenger.service;

import com.modsen.software.passenger.entity.Passenger;
import com.modsen.software.passenger.exception.PassengerAlreadyExistsException;
import com.modsen.software.passenger.exception.PassengerNotExistsException;
import com.modsen.software.passenger.repository.PassengerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PassengerServiceImpl {

    private final PassengerRepository passengerRepository;


    @Transactional(readOnly = true)
    public Passenger getById(Long id) {
        return passengerRepository.findById(id)
                .orElseThrow(()-> new PassengerNotExistsException(String.format("Passenger with id = %d not exists", id)));
    }

    @Transactional(readOnly = true)
    public List<Passenger> getAll(Integer pageNumber, Integer pageSize, String sortBy, String sortOrder) {
        return passengerRepository.findAll(PageRequest.of(pageNumber, pageSize, Sort.by(Sort.Direction.fromString(sortOrder), sortBy)))
                .getContent();
    }

    @Transactional
    public Passenger save(Passenger passenger) {
        Passenger saved;
        if (passenger.getId() != null && passengerRepository.existsById(passenger.getId())) {
            throw new PassengerAlreadyExistsException(String.format("Passenger with id = %d already exists", passenger.getId()));
        } else {
            saved = passengerRepository.save(passenger);
        }

        return saved;
    }

    @Transactional
    public void softDelete(Long id) {
        if (passengerRepository.softDelete(id) == 0) {
            throw new PassengerNotExistsException(String.format("Passenger with id = %d not exists", id));
        }
    }

    @Transactional
    public Passenger update(Passenger request) {
        if (!passengerRepository.existsById(request.getId()))
            throw new PassengerNotExistsException(String.format("Passenger with id = %d not exists", request.getId()));

        return passengerRepository.save(request);
    }
}