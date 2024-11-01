package com.modsen.software.passenger.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
@Schema(description = "Passenger response")
public class PassengerResponse {
    @Schema(description = "Identifier")
    private Long id;

    @Schema(description = "Passenger's full name")
    private String fullName;

    @Schema(description = "Passenger's email")
    private String email;

    @Schema(description = "Passenger's phone number")
    private String phone;

    @Schema(description = "Determines whether a soft-delete has been applied")
    private Boolean isDeleted;

    @Schema(description = "Passenger's rating")
    private Float rating;
}