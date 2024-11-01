package com.modsen.software.passenger.repository;

import com.modsen.software.passenger.entity.Passenger;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PassengerRepository extends JpaRepository<Passenger, Long> {

    boolean existsByEmail(String email);
    boolean existsByPhone(String phone);
    Optional<Passenger> findByEmail(String email);
    Optional<Passenger> findByPhone(String phone);

    @Modifying
    @Query("UPDATE Passenger p SET p.isDeleted = true WHERE p.id = :id")
    int softDelete(@Param("id") Long id);
}
