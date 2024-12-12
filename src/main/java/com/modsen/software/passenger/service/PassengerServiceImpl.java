package com.modsen.software.passenger.service;

import com.modsen.software.passenger.entity.Passenger;
import com.modsen.software.passenger.exception.BaseCustomException;
import com.modsen.software.passenger.exception.PassengerAlreadyExistsException;
import com.modsen.software.passenger.exception.PassengerNotExistsException;
import com.modsen.software.passenger.filter.tokeninfo.UserTokenInfo;
import com.modsen.software.passenger.filter.tokeninfo.util.ReactiveRequestContextHolder;
import com.modsen.software.passenger.filter.tokeninfo.util.UserRole;
import com.modsen.software.passenger.repository.PassengerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static com.modsen.software.passenger.filter.tokeninfo.util.UserRole.*;

@Service
@RequiredArgsConstructor
public class PassengerServiceImpl {

    private final PassengerRepository passengerRepository;

    @Transactional(readOnly = true)
    public Mono<Passenger> getById(Long id) {
        return passengerRepository.findById(id)
                .switchIfEmpty(Mono.error(new PassengerNotExistsException(String.format("Passenger with id = %d not exists", id))));
    }

    @Transactional(readOnly = true)
    public Flux<Passenger> getAll(Integer pageNumber, Integer pageSize, String sortBy, String sortOrder) {
        return switch (Sort.Direction.fromString(sortOrder)) {
            case ASC -> passengerRepository.findAllAsc(sortBy, pageSize, pageNumber * pageSize);
            case DESC -> passengerRepository.findAllDesc(sortBy, pageSize, pageNumber * pageSize);
        };
    }

    @Transactional
    public Mono<Passenger> save(Passenger passenger) {
        return (passenger.getId() != null ? passengerRepository.existsById(passenger.getId()) : Mono.just(false))
                .flatMap(exists -> {
                    if (exists) {
                        return Mono.error(new PassengerAlreadyExistsException(
                                String.format("Passenger with id = %d already exists", passenger.getId())));
                    }

                    return passengerRepository.existsByEmail(passenger.getEmail());
                })
                .flatMap(emailExists -> {
                    if (emailExists) {
                        return Mono.error(new PassengerAlreadyExistsException(
                                String.format("Passenger with email = %s already exists", passenger.getEmail())));
                    }

                    return passengerRepository.existsByPhone(passenger.getPhone());
                })
                .flatMap(phoneExists -> {
                    if (phoneExists) {
                        return Mono.error(new PassengerAlreadyExistsException(
                                String.format("Passenger with phoneNumber = %s already exists", passenger.getPhone())));
                    }

                    return passengerRepository.save(passenger);
                });
    }

    @Transactional
    public Mono<Void> softDelete(Long id) {
        return ReactiveRequestContextHolder.getUserTokenInfo().flatMap(
                userTokenInfo -> {
                    if (!(userTokenInfo.getRoles().contains(ADMIN) || (userTokenInfo.getId() != null && userTokenInfo.getId().compareTo(id) == 0)))
                        throw new BaseCustomException(HttpStatus.FORBIDDEN.value(), "Token doesn't belong to the user with such id");

                    return passengerRepository.softDelete(id)
                        .flatMap(nAffectedLines -> {
                            if (nAffectedLines == 0)
                                return Mono.error(new PassengerNotExistsException(String.format("Passenger with id = %d not exists", id)));

                            return Mono.empty();
                        });
                }
        );
    }

    @Transactional
    public Mono<Passenger> update(Passenger request) {
        return ReactiveRequestContextHolder.getUserTokenInfo()
                .flatMap(userTokenInfo -> {

                    if (request.getId() == null) {
                        return Mono.error(new BaseCustomException(HttpStatus.BAD_REQUEST.value(), "Incorrect user's id = null"));
                    }


                    if (!userTokenInfo.getRoles().contains(ADMIN) &&
                            (userTokenInfo.getId() == null || !userTokenInfo.getId().equals(request.getId()))) {
                        return Mono.error(new BaseCustomException(HttpStatus.FORBIDDEN.value(), "Token doesn't belong to the user with such id"));
                    }


                    return passengerRepository.findById(request.getId())
                            .switchIfEmpty(Mono.error(new PassengerNotExistsException(String.format("Passenger with id = %d not exists", request.getId()))));
                })
                .flatMap(existingPassenger ->
                        passengerRepository.findByEmail(request.getEmail())
                            .filter(passengerByEmail -> !passengerByEmail.getId().equals(request.getId()))
                            .flatMap(passengerByEmail -> Mono.error(new PassengerAlreadyExistsException(
                                    String.format("Passenger with email = %s already exists", request.getEmail()))))
                            .switchIfEmpty(Mono.just(existingPassenger)))
                .flatMap(existingPassenger ->
                        passengerRepository.findByPhone(request.getPhone())
                            .filter(passengerByPhone -> !passengerByPhone.getId().equals(request.getId()))
                            .flatMap(passengerByPhone -> Mono.error(new PassengerAlreadyExistsException(
                                    String.format("Passenger with phoneNumber = %s already exists", request.getPhone()))))
                            .switchIfEmpty(Mono.just(existingPassenger)))
                .flatMap(existingPassenger -> {
                    request.setVersion(((Passenger) existingPassenger) .getVersion());
                    return passengerRepository.save(request);
                });
    }

    @Transactional
    public Mono<Void> updateRating(Long id, Float newRating) {
        return passengerRepository.findById(id).switchIfEmpty(
                Mono.error(new PassengerNotExistsException(String.format("Passenger with id = %d not exists", id))))
                .doOnNext(passenger -> {
                    passenger.setRating(newRating);

                    passengerRepository.save(passenger);
                }).then();
    }
}