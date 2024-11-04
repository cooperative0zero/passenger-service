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
import java.util.Optional;

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
            if (passengerRepository.existsByEmail(passenger.getEmail()))
                throw new PassengerAlreadyExistsException(String.format("Passenger with email = %s already exists", passenger.getEmail()));

            if (passengerRepository.existsByPhone(passenger.getPhone()))
                throw new PassengerAlreadyExistsException(String.format("Passenger with phoneNumber = %s already exists", passenger.getPhone()));

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

        Optional<Passenger> passengerByEmail = passengerRepository.findByEmail(request.getEmail());
        Optional<Passenger> passengerByPhone = passengerRepository.findByPhone(request.getPhone());

        if (passengerByEmail.isPresent() && passengerByEmail.get().getId().compareTo(request.getId()) != 0)
            throw new PassengerAlreadyExistsException(String.format("Passenger with email = %s already exists", request.getEmail()));

        if (passengerByPhone.isPresent() && passengerByPhone.get().getId().compareTo(request.getId()) != 0)
            throw new PassengerAlreadyExistsException(String.format("Passenger with phoneNumber = %s already exists", request.getPhone()));

        return passengerRepository.save(request);
    }
}