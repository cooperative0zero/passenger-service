package com.modsen.software.passenger.contract;

import com.modsen.software.passenger.dto.PassengerRequest;
import com.modsen.software.passenger.entity.Passenger;
import com.modsen.software.passenger.integration.AbstractIntegrationTest;
import com.modsen.software.passenger.repository.PassengerRepository;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.verifier.messaging.boot.AutoConfigureMessageVerifier;
import org.springframework.test.web.servlet.MockMvc;

import javax.annotation.PostConstruct;
import java.util.List;

@SpringBootTest
@AutoConfigureMessageVerifier
@AutoConfigureMockMvc
public class PassengerBaseContractTest extends AbstractIntegrationTest {

    private static Passenger passenger;

    private static Passenger secondPassenger;

    @Autowired
    private PassengerRepository passengerRepository;

    @Autowired
    private MockMvc mockMvc;

    static {
        passenger = Passenger.builder()
                .id(1L)
                .email("example@mail.com")
                .isDeleted(false)
                .phone("987654321")
                .fullName("First Middle Last")
                .rating(1f)
                .build();

        secondPassenger = Passenger.builder()
                .id(2L)
                .email("example2@mail.com")
                .isDeleted(false)
                .phone("123456789")
                .fullName("First2 Middle2 Last2")
                .rating(2f)
                .build();
    }

    @PostConstruct
    public void init() {
        this.passengerRepository.saveAll(List.of(passenger, secondPassenger));

        RestAssuredMockMvc.mockMvc(mockMvc);
    }
}
