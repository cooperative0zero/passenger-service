package com.modsen.software.passenger.repository;

import com.modsen.software.passenger.entity.Passenger;
import com.modsen.software.passenger.util.Constants;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Optional;

import static com.modsen.software.passenger.util.Constants.anotherPassenger;
import static com.modsen.software.passenger.util.Constants.passenger;

@DataJpaTest
@ActiveProfiles("test")
public class PassengerRepositoryTest {

    @Autowired
    private PassengerRepository repository;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @BeforeEach
    void setup() {
        jdbcTemplate.execute("TRUNCATE TABLE passengers RESTART IDENTITY;");
    }

    @Test
    void saveWhenPassengerNotExists() {
        Passenger savedPassenger = repository.save(passenger);

        Assertions.assertNotNull(savedPassenger);
        Assertions.assertEquals(passenger.getId(), savedPassenger.getId());
        Assertions.assertEquals(passenger.getEmail(), savedPassenger.getEmail());
        Assertions.assertEquals(passenger.getFullName(), savedPassenger.getFullName());
    }

    @Test
    void updateWhenPassengerExists() {
        repository.save(passenger);

        Passenger passenger = Constants.passenger.toBuilder()
                .phone("12345678")
                .email("exampleUpdated@email.com")
                .build();

        Passenger updatedPassenger = repository.save(passenger);
        Assertions.assertNotNull(updatedPassenger);
        Assertions.assertEquals("12345678", updatedPassenger.getPhone());
        Assertions.assertEquals("exampleUpdated@email.com", updatedPassenger.getEmail());
    }

    @Test
    void softDeleteWhenPassengerExists() {
        repository.save(passenger);

        Passenger passenger = Constants.passenger.toBuilder()
                        .isDeleted(true)
                        .build();

        Passenger removedPassenger = repository.save(passenger);
        Assertions.assertNotEquals(Optional.empty(), repository.findById(removedPassenger.getId()));
        Assertions.assertTrue(removedPassenger.getIsDeleted());
    }

    @Test
    void findByIdWhenPassengerExists() {
        Passenger savedPassenger = repository.save(passenger);

        Optional<Passenger> foundPassenger = repository.findById(savedPassenger.getId());
        Assertions.assertTrue(foundPassenger.isPresent());
        Assertions.assertEquals(passenger.getEmail(), foundPassenger.get().getEmail());
    }

    @Test
    void findAllWhenPassengersExist() {
        repository.save(passenger);
        repository.save(anotherPassenger);

        List<Passenger> passengers = repository.findAll();

        Assertions.assertEquals(2, passengers.size());
        Assertions.assertEquals(passenger.getId(), passengers.get(0).getId());
        Assertions.assertEquals(anotherPassenger.getId(), passengers.get(1).getId());
    }
}