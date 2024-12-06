package com.modsen.software.passenger.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Version;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

@Entity
@Table(name = "passengers")
@Builder(toBuilder = true)
public class Passenger {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column("p_id")
    private Long id;

    @Column("p_full_name")
    private String fullName;

    @Column("p_email")
    private String email;

    @Column("p_phone")
    private String phone;

    @Column("p_deleted")
    private Boolean isDeleted;

    @Column("p_rating")
    private Float rating;

    @Version
    @Column("p_version")
    private Long version;
}
