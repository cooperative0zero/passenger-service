package com.modsen.software.passenger.dto;

import jakarta.validation.constraints.*;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PassengerRequest {

    @Min(1)
    private Long id;

    @NotBlank
    private String fullName;

    @Email
    @NotBlank
    private String email;

    @Pattern(regexp = "^\\d{6,}$")
    @NotBlank
    private String phone;

    @NotNull
    private Boolean isDeleted;

    private Float rating;
}

