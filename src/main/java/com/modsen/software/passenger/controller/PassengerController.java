package com.modsen.software.passenger.controller;

import com.modsen.software.passenger.dto.PassengerRequest;
import com.modsen.software.passenger.dto.PassengerResponse;
import com.modsen.software.passenger.service.PassengerService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/passengers")
public class PassengerController {

    private final PassengerService passengerService;

    @GetMapping
    public ResponseEntity<List<PassengerResponse>> getAll(@RequestParam(required = false, defaultValue = "0") @Min(0) Integer pageNumber,
                                                          @RequestParam(required = false, defaultValue = "10") @Min(1) Integer pageSize,
                                                          @RequestParam(required = false, defaultValue = "id") String sortBy,
                                                          @RequestParam(required = false, defaultValue = "desc") String sortOrder) {

        List<PassengerResponse> response = passengerService.getAll(pageNumber, pageSize, sortBy, sortOrder);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PassengerResponse> getById(@PathVariable @Min(1) Long id) {
        PassengerResponse response = passengerService.getById(id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> softDelete(@PathVariable @Min(1) Long id) {
        passengerService.softDelete(id);
        return new ResponseEntity<>("Deleted successfully", HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<PassengerResponse> save(@RequestBody @Valid PassengerRequest passenger) {
        PassengerResponse response = passengerService.save(passenger);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<PassengerResponse> update(@RequestBody @Valid PassengerRequest passenger) {
        PassengerResponse response = passengerService.update(passenger);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
