package com.modsen.software.passenger.repository;

import com.modsen.software.passenger.entity.Passenger;
import jakarta.persistence.LockModeType;
import jakarta.persistence.QueryHint;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PassengerRepository extends JpaRepository<Passenger, Long> {

    boolean existsByEmail(String email);
    boolean existsByPhone(String phone);

    @Lock(LockModeType.PESSIMISTIC_READ)
    @QueryHints(@QueryHint(name = "jakarta.persistence.lock.timeout", value = "5000"))
    Optional<Passenger> findByEmail(String email);

    @Lock(LockModeType.PESSIMISTIC_READ)
    @QueryHints(@QueryHint(name = "jakarta.persistence.lock.timeout", value = "5000"))
    Optional<Passenger> findByPhone(String phone);

    @Modifying
    @Query("UPDATE Passenger p SET p.isDeleted = true WHERE p.id = :id")
    int softDelete(@Param("id") Long id);
}
