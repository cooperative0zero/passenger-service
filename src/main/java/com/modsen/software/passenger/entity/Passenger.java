package com.modsen.software.passenger.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

@Entity
@Table(name = "passengers")
@Builder
public class Passenger {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "p_id")
    private Long id;

    @Column(name = "p_full_name", nullable = false)
    private String fullName;

    @Column(name = "p_email", nullable = false)
    private String email;

    @Column(name = "p_phone", nullable = false)
    private String phone;

    @Column(name = "p_deleted", nullable = false)
    private Boolean isDeleted;

    @Column(name = "p_rating")
    private Float rating;
}
