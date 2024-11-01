package com.modsen.software.passenger.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.modsen.software.passenger.dto.PaginatedResponse;
import com.modsen.software.passenger.dto.PassengerRequest;
import com.modsen.software.passenger.dto.PassengerResponse;
import com.modsen.software.passenger.entity.Passenger;
import com.modsen.software.passenger.service.PassengerServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static com.modsen.software.passenger.util.Constants.*;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(value = PassengerController.class)
public class PassengerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PassengerServiceImpl passengerService;

    private final PaginatedResponse<PassengerResponse> paginatedResponse;
    private final List<PassengerResponse> passengerResponses;
    private final List<Passenger> passengers;

    {
        passengers = List.of(passenger, anotherPassenger);
        passengerResponses = List.of(passengerResponse, anotherPassengerResponse);

        paginatedResponse = new PaginatedResponse<>(
                passengerResponses,
                0,
                10,
                passengers.size()
        );
    }

    @Test
    void getAllWhenPassengersExist() throws Exception {
        when(passengerService.getAll(any(), any(), any(), any()))
                .thenReturn(passengers);

        mockMvc.perform(get("/api/v1/passengers")
                        .param("pageNumber", "0")
                        .param("pageSize", "10")
                        .param("sortBy", "id")
                        .param("sortOrder", "asc")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.items[0].id", is(passengerResponses.get(0).getId().intValue())))
                .andExpect(jsonPath("$.items[1].id", is(passengerResponses.get(1).getId().intValue())))
                .andExpect(jsonPath("$.items[0].fullName", is(passengerResponses.get(0).getFullName())))
                .andExpect(jsonPath("$.items[1].fullName", is(passengerResponses.get(1).getFullName())))
                .andExpect(jsonPath("$.total", is(paginatedResponse.getTotal())))
                .andExpect(jsonPath("$.page", is(paginatedResponse.getPage())))
                .andExpect(jsonPath("$.size", is(paginatedResponse.getSize())));

        verify(passengerService, times(1)).getAll(any(), any(), any(), any());
    }

    @Test
    void getByIdWhenPassengerExists() throws Exception {
        when(passengerService.getById(1L))
                .thenReturn(passenger);

        mockMvc.perform(get("/api/v1/passengers/{id}", passenger.getId())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(passengerResponse.getId().intValue())))
                .andExpect(jsonPath("$.fullName", is(passengerResponse.getFullName())))
                .andExpect(jsonPath("$.email", is(passengerResponse.getEmail())))
                .andExpect(jsonPath("$.rating", is(passengerResponse.getRating().doubleValue())))
                .andExpect(jsonPath("$.phone", is(passengerResponse.getPhone())))
                .andExpect(jsonPath("$.isDeleted", is(passengerResponse.getIsDeleted())));

        verify(passengerService, times(1)).getById(passenger.getId());
    }

    @Test
    void softDeleteWhenPassengerExists() throws Exception {
        mockMvc.perform(delete("/api/v1/passengers/{id}", passenger.getId()))
                .andExpect(status().isNoContent());

        verify(passengerService, times(1)).softDelete(passenger.getId());
    }

    @Test
    void saveWhenPassengerNotExists() throws Exception {
        ObjectMapper mapper = new ObjectMapper();

        when(passengerService.save(argThat(passenger -> passenger.getId().compareTo(1L) == 0))).thenReturn(passenger);

        mockMvc.perform(post("/api/v1/passengers")
                        .content(mapper.writeValueAsString(passengerRequest))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", is(passengerResponse.getId().intValue())))
                .andExpect(jsonPath("$.fullName", is(passengerResponse.getFullName())))
                .andExpect(jsonPath("$.email", is(passengerResponse.getEmail())))
                .andExpect(jsonPath("$.rating", is(passengerResponse.getRating().doubleValue())))
                .andExpect(jsonPath("$.phone", is(passengerResponse.getPhone())))
                .andExpect(jsonPath("$.isDeleted", is(passengerResponse.getIsDeleted())));

        verify(passengerService, times(1)).save(argThat(passenger -> passenger.getId().compareTo(1L) == 0));
    }

    @Test
    void updateWhenPassengerExists() throws Exception {
        PassengerRequest passengerRequestToUpdate = passengerRequest.toBuilder()
                .fullName("First Middle Last")
                .email("exampleUpdatedEmail@email.com")
                .phone("987654321")
                .build();
        
        
        Passenger passengerToUpdate = passenger.toBuilder()
                .email("exampleUpdatedEmail@email.com")
                .phone("987654321")
                .fullName("First Middle Last")
                .build();

        PassengerResponse passengerUpdateResponse = passengerResponse.toBuilder()
                .email("exampleUpdatedEmail@email.com")
                .phone("987654321")
                .fullName("First Middle Last")
                .build();

        ObjectMapper mapper = new ObjectMapper();

        when(passengerService.update(argThat(passenger -> passenger.getId().compareTo(1L) == 0))).thenReturn(passengerToUpdate);

        mockMvc.perform(put("/api/v1/passengers")
                        .content(mapper.writeValueAsString(passengerRequestToUpdate))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(passengerUpdateResponse.getId().intValue())))
                .andExpect(jsonPath("$.fullName", is(passengerUpdateResponse.getFullName())))
                .andExpect(jsonPath("$.email", is(passengerUpdateResponse.getEmail())))
                .andExpect(jsonPath("$.rating", is(passengerUpdateResponse.getRating().doubleValue())))
                .andExpect(jsonPath("$.phone", is(passengerUpdateResponse.getPhone())))
                .andExpect(jsonPath("$.isDeleted", is(passengerUpdateResponse.getIsDeleted())));

        verify(passengerService, times(1)).update(argThat(passenger -> passenger.getId().compareTo(1L) == 0));
    }
}
