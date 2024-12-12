package com.modsen.software.passenger.repository;

import com.modsen.software.passenger.entity.Passenger;
import org.springframework.data.r2dbc.repository.Modifying;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface PassengerRepository extends ReactiveCrudRepository<Passenger, Long> {

    @Query("SELECT * FROM passengers ORDER BY :sortBy ASC LIMIT :limit OFFSET :offset")
    Flux<Passenger> findAllAsc(@Param("sortBy") String sortBy,
                                            @Param("limit") int limit,
                                            @Param("offset") int offset);

    @Query("SELECT * FROM passengers ORDER BY :sortBy DESC LIMIT :limit OFFSET :offset")
    Flux<Passenger> findAllDesc(@Param("sortBy") String sortBy,
                                             @Param("limit") int limit,
                                             @Param("offset") int offset);

    Mono<Boolean> existsByEmail(String email);
    Mono<Boolean> existsByPhone(String phone);

    @Query("SELECT * FROM passengers WHERE p_email = :email FOR SHARE")
    Mono<Passenger> findByEmail(@Param("email") String email);

    @Query("SELECT * FROM passengers WHERE p_phone = :phone FOR SHARE")
    Mono<Passenger> findByPhone(@Param("phone") String phone);

    @Modifying
    @Query("UPDATE passengers SET p_deleted = true WHERE p_id = :id")
    Mono<Integer> softDelete(@Param("id") Long id);
}

