package com.modsen.software.passenger.controller;

import com.modsen.software.passenger.dto.PaginatedResponse;
import com.modsen.software.passenger.dto.PassengerRequest;
import com.modsen.software.passenger.dto.PassengerResponse;
import com.modsen.software.passenger.entity.Passenger;
import com.modsen.software.passenger.service.PassengerServiceImpl;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.ConversionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/passengers")
public class PassengerController {

    private final PassengerServiceImpl passengerService;

    private final ConversionService conversionService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public PaginatedResponse<PassengerResponse> getAll(@RequestParam(required = false, defaultValue = "0") @Min(0) Integer pageNumber,
                                                        @RequestParam(required = false, defaultValue = "10") @Min(1) Integer pageSize,
                                                        @RequestParam(required = false, defaultValue = "id") String sortBy,
                                                        @RequestParam(required = false, defaultValue = "desc") String sortOrder) {

        var result = passengerService.getAll(pageNumber, pageSize, sortBy, sortOrder)
                .stream()
                .map(passenger -> conversionService.convert(passenger, PassengerResponse.class))
                .toList();

        return new PaginatedResponse<>(result, pageNumber, pageSize, result.size());
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public PassengerResponse getById(@PathVariable @Min(1) Long id) {
        return conversionService.convert(passengerService.getById(id), PassengerResponse.class);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void softDelete(@PathVariable @Min(1) Long id) {
        passengerService.softDelete(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PassengerResponse save(@RequestBody @Valid PassengerRequest passengerRequest) {
        return conversionService.convert(passengerService.save(
                Objects.requireNonNull(conversionService.convert(passengerRequest, Passenger.class))), PassengerResponse.class);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public PassengerResponse update(@RequestBody @Valid PassengerRequest passenger) {
        return conversionService.convert(passengerService.update(Objects.requireNonNull(conversionService.convert(passenger, Passenger.class))),
                PassengerResponse.class);
    }
}
