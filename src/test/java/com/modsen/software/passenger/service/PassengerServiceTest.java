package com.modsen.software.passenger.service;

import com.modsen.software.passenger.entity.Passenger;
import com.modsen.software.passenger.repository.PassengerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static com.modsen.software.passenger.util.Constants.anotherPassenger;
import static com.modsen.software.passenger.util.Constants.passenger;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class PassengerServiceTest {

    @Mock
    private PassengerRepository passengerRepository;

    @InjectMocks
    private PassengerServiceImpl passengerService;

    @Test
    void saveWhenPassengerNotExists() {
        when(passengerRepository.save(any(Passenger.class))).thenAnswer(invocationOnMock -> invocationOnMock.getArgument(0));
        when(passengerRepository.existsById(passenger.getId())).thenReturn(false);

        Passenger savedPassenger = passengerService.save(passenger);

        assertNotNull(savedPassenger);
        assertEquals(passenger.getId(), savedPassenger.getId());
        assertEquals(passenger.getPhone(), savedPassenger.getPhone());
        assertEquals(passenger.getEmail(), savedPassenger.getEmail());
    }

    @Test
    void updateWhenPassengerExists() {
        Passenger passengerToUpdate = passenger.toBuilder()
                .email("exampleUpdated@email.com")
                .phone("1234567")
                .build();

        when(passengerRepository.existsById(passengerToUpdate.getId())).thenReturn(true);
        when(passengerRepository.save(any(Passenger.class))).thenAnswer(invocationOnMock -> invocationOnMock.getArgument(0));

        Passenger updatedPassenger = passengerService.update(passengerToUpdate);

        assertNotNull(updatedPassenger);
        assertEquals(passengerToUpdate.getId(), updatedPassenger.getId());
        assertEquals(passengerToUpdate.getPhone(), updatedPassenger.getPhone());
        assertEquals(passengerToUpdate.getEmail(), updatedPassenger.getEmail());
    }

    @Test
    void softDeleteWhenPassengerExists() {
        when(passengerRepository.softDelete(anyLong())).thenReturn(1);

        assertDoesNotThrow(() -> passengerService.softDelete(1L));
    }

    @Test
    void getByIdWhenPassengerExists() {
        when(passengerRepository.findById(1L)).thenReturn(Optional.of(passenger));

        Passenger result = passengerService.getById(1L);
        assertEquals(passenger.getId(), result.getId());
        assertEquals(passenger.getPhone(), result.getPhone());
        assertEquals(passenger.getFullName(), result.getFullName());
        assertEquals(passenger.getEmail(), result.getEmail());
    }

    @Test
    void getAllWhenPassengersExist() {
        Pageable pageable = PageRequest.of(0, 10, Sort.Direction.ASC, "id");
        List<Passenger> toReturn = Stream.of(passenger, anotherPassenger).toList();

        when(passengerRepository.findAll(pageable)).thenReturn(new PageImpl<>(toReturn));

        List<Passenger> result = passengerService.getAll(pageable.getPageNumber(), pageable.getPageSize(),
                "id", Sort.Direction.ASC.name());

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(passenger.getId(), result.get(0).getId());
        assertEquals(anotherPassenger.getId(), result.get(1).getId());
    }
}
