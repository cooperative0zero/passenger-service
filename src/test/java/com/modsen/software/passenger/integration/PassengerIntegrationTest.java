package com.modsen.software.passenger.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.modsen.software.passenger.dto.PassengerRequest;
import com.modsen.software.passenger.entity.Passenger;
import com.modsen.software.passenger.repository.PassengerRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.web.servlet.MockMvc;


import java.util.List;

import static com.modsen.software.passenger.util.Constants.*;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class PassengerIntegrationTest extends AbstractIntegrationTest {

    @Autowired
    private PassengerRepository passengerRepository;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @BeforeEach
    void prepare() {
        jdbcTemplate.execute("TRUNCATE TABLE passengers RESTART IDENTITY;");
    }

    @Test
    void testGetAllPassengersWhenPassengersExist() throws Exception {
        passengerRepository.saveAll(List.of(passenger, anotherPassenger));

        mockMvc.perform(get("/api/v1/passengers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.total ", is(2)))
                .andExpect(jsonPath("$.items[1].fullName", is(passenger.getFullName())))
                .andExpect(jsonPath("$.items[1].email", is(passenger.getEmail())))
                .andExpect(jsonPath("$.items[1].phone", is(passenger.getPhone())))
                .andExpect(jsonPath("$.items[0].fullName", is(anotherPassenger.getFullName())))
                .andExpect(jsonPath("$.items[0].email", is(anotherPassenger.getEmail())))
                .andExpect(jsonPath("$.items[0].phone", is(anotherPassenger.getPhone())));
    }

    @Test
    void testGetPassengerByIdWhenPassengerExists() throws Exception {
        passengerRepository.save(passenger);

        mockMvc.perform(get("/api/v1/passengers/{id}", passenger.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(passenger.getId().intValue())))
                .andExpect(jsonPath("$.fullName", is(passenger.getFullName())))
                .andExpect(jsonPath("$.email", is(passenger.getEmail())))
                .andExpect(jsonPath("$.rating", is(passenger.getRating().doubleValue())))
                .andExpect(jsonPath("$.phone", is(passenger.getPhone())))
                .andExpect(jsonPath("$.isDeleted", is(passenger.getIsDeleted())));
    }

    @Test
    void testSavePassengerWhenPassengerNotExists() throws Exception {
        mockMvc.perform(post("/api/v1/passengers")
                        .content(mapper.writeValueAsString(passengerRequest))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.fullName", is(passenger.getFullName())))
                .andExpect(jsonPath("$.email", is(passenger.getEmail())))
                .andExpect(jsonPath("$.rating", is(passenger.getRating().doubleValue())))
                .andExpect(jsonPath("$.phone", is(passenger.getPhone())))
                .andExpect(jsonPath("$.isDeleted", is(passenger.getIsDeleted())));
    }

    @Test
    void testUpdatePassengerWhenPassengerExists() throws Exception {
        PassengerRequest passengerRequestToUpdate = passengerRequest.toBuilder()
                .email("exampleUpdated@email.com")
                .phone("987654321")
                .build();

        passengerRepository.save(passenger);
        mockMvc.perform(put("/api/v1/passengers")
                        .content(mapper.writeValueAsString(passengerRequestToUpdate))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(passengerRequestToUpdate.getId().intValue())))
                .andExpect(jsonPath("$.fullName", is(passengerRequestToUpdate.getFullName())))
                .andExpect(jsonPath("$.email", is(passengerRequestToUpdate.getEmail())))
                .andExpect(jsonPath("$.rating", is(passengerRequestToUpdate.getRating().doubleValue())))
                .andExpect(jsonPath("$.phone", is(passengerRequestToUpdate.getPhone())))
                .andExpect(jsonPath("$.isDeleted", is(passengerRequestToUpdate.getIsDeleted())));
    }

    @Test
    void testSoftDeleteWhenPassengerExists() throws Exception {
        passengerRepository.save(passenger);

        mockMvc.perform(delete("/api/v1/passengers/{id}", passenger.getId()))
                .andExpect(status().isNoContent());
    }
}
