package com.modsen.software.passenger.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Passenger request")
@Builder(toBuilder = true)
public class PassengerRequest {

    @Min(1)
    @Schema(description = "Identifier")
    private Long id;

    @NotBlank
    @Schema(description = "Passenger's full name")
    private String fullName;

    @Email
    @NotBlank
    @Schema(description = "Passenger's email")
    private String email;

    @Pattern(regexp = "^\\d{6,}$")
    @NotBlank
    @Schema(description = "Passenger's phone number")
    private String phone;

    @NotNull
    @Schema(description = "Determines whether a soft-delete has been applied")
    private Boolean isDeleted;

    @Schema(description = "Passenger's rating", hidden = true)
    private Float rating;
}

