package com.modsen.software.passenger.component.step;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.modsen.software.passenger.dto.PassengerRequest;
import com.modsen.software.passenger.entity.Passenger;
import com.modsen.software.passenger.integration.AbstractIntegrationTest;
import com.modsen.software.passenger.repository.PassengerRepository;
import com.modsen.software.passenger.util.Constants;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.spring.CucumberContextConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.List;

import static com.modsen.software.passenger.util.Constants.*;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@CucumberContextConfiguration
@SpringBootTest
@AutoConfigureMockMvc
public class PassengerServiceTestSteps extends AbstractIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    private ResultActions result;

    public PassengerServiceTestSteps(PassengerRepository passengerRepository) {
        passengerRepository.saveAll(List.of(passenger, anotherPassenger));
    }

    @When("I request all passengers from database through service")
    public void iRequestAllPassengersFromDatabaseThroughService() throws Exception {
        result = mockMvc.perform(get("/api/v1/passengers")
                .param("pageNumber", "0")
                .param("pageSize", "10")
                .param("sortBy", "id")
                .param("sortOrder", "asc")
                .contentType(MediaType.APPLICATION_JSON));
    }

    @Then("first passengers page should be returned")
    public void firstPassengersPageShouldBeReturned() throws Exception {
        result.andExpect(jsonPath("$.items[0].id", is(passenger.getId().intValue())))
                .andExpect(jsonPath("$.items[1].id", is(anotherPassenger.getId().intValue())))
                .andExpect(jsonPath("$.items[0].fullName", is(passenger.getFullName())))
                .andExpect(jsonPath("$.items[1].fullName", is(anotherPassenger.getFullName())))
                .andExpect(jsonPath("$.total", is(2)))
                .andExpect(jsonPath("$.page", is(0)))
                .andExpect(jsonPath("$.size", is(10)));
    }

    @And("all passengers request complete with code {int} \\(OK)")
    public void allPassengersRequestCompleteWithCodeOK(int status) throws Exception {
        result.andExpect(status().is(status));
    }

    @When("I request passenger with id = {int} from database through service")
    public void iRequestPassengerWithIdFromDatabaseThroughService(int id) throws Exception {
        result = mockMvc.perform(get("/api/v1/passengers/{id}", id));
    }

    @Then("find by id request complete with code {int} \\(OK) for passenger")
    public void findByIdRequestCompleteWithCodeOKForPassenger(int status) throws Exception {
        result.andExpect(status().is(status));
    }

    @And("returned passenger must be with name {string}, email {string} and phone number {string}")
    public void returnedPassengerMustBeWithNameEmailAndPhoneNumber(String name, String email, String phone) throws Exception {
        result.andExpect(jsonPath("$.fullName", is(name)))
                .andExpect(jsonPath("$.email", is(email)))
                .andExpect(jsonPath("$.phone", is(phone)));
    }

    @Then("request complete with code {int}\\(NOT_FOUND) and indicates that specified passenger not found")
    public void requestCompleteWithCodeNOT_FOUNDAndIndicatesThatSpecifiedPassengerNotFound(int status) throws Exception {
        result.andExpect(status().is(status));
    }

    @When("I save new passenger with name {string}, email {string} and phone number {string}")
    public void iSaveNewPassengerWithNameEmailAndPhoneNumber(String fullName, String email, String phone) throws Exception {
        PassengerRequest passengerRequest = Constants.passengerRequest.toBuilder()
                        .id(null)
                        .fullName(fullName)
                        .email(email)
                        .phone(phone)
                        .build();

        result = mockMvc.perform(post("/api/v1/passengers")
                .content(mapper.writeValueAsString(passengerRequest))
                .contentType(MediaType.APPLICATION_JSON));
    }

    @Then("saved passenger with name {string}, email {string}, phone number {string} should be returned")
    public void savedPassengerWithNameEmailPhoneNumberShouldBeReturned(String fullName, String email, String phone) throws Exception {
         result.andExpect(jsonPath("$.fullName", is(fullName)))
                 .andExpect(jsonPath("$.email", is(email)))
                 .andExpect(jsonPath("$.phone", is(phone)));
    }

    @Then("the response should indicate with code {int} that email already owned by another passenger")
    public void theResponseShouldIndicateWithCodeThatEmailAlreadyOwnedByAnotherPassenger(int status) throws Exception {
        result.andExpect(status().is(status));
    }

    @Then("the response should indicate with code {int} that phone already owned by another passenger")
    public void theResponseShouldIndicateWithCodeThatPhoneAlreadyOwnedByAnotherPassenger(int status) throws Exception {
        result.andExpect(status().is(status));
    }

    @When("I try to update passenger with id = {int} changing name to {string} and email to {string}")
    public void iTryToUpdatePassengerWithIdChangingNameToAndEmailTo(int id, String fullName, String email) throws Exception {
        PassengerRequest passengerRequestToUpdate = passengerRequest.toBuilder()
                .id((long) id)
                .fullName(fullName)
                .email(email)
                .build();

        result = mockMvc.perform(put("/api/v1/passengers")
                .content(mapper.writeValueAsString(passengerRequestToUpdate))
                .contentType(MediaType.APPLICATION_JSON));
    }

    @Then("updated passenger with name {string} and email {string} should be returned")
    public void updatedPassengerWithNameAndEmailShouldBeReturned(String fullName, String email) throws Exception {
        result.andExpect(jsonPath("$.fullName", is(fullName)))
                .andExpect(jsonPath("$.email", is(email)));
    }

    @When("I try to update passenger with id = {int} changing email to {string}")
    public void iTryToUpdatePassengerWithIdChangingEmailTo(int id, String email) throws Exception {
        PassengerRequest passengerRequestToUpdate = passengerRequest.toBuilder()
                .id((long) id)
                .email(email)
                .build();

        result = mockMvc.perform(put("/api/v1/passengers")
                .content(mapper.writeValueAsString(passengerRequestToUpdate))
                .contentType(MediaType.APPLICATION_JSON));
    }

    @Then("the response should indicate with code {int} that updated email already owned by another passenger")
    public void theResponseShouldIndicateWithCodeThatUpdatedEmailAlreadyOwnedByAnotherPassenger(int status) throws Exception {
        result.andExpect(status().is(status));
    }

    @When("I try to delete passenger with id = {int}")
    public void iTryToDeletePassengerWithId(int id) throws Exception {
        result = mockMvc.perform(delete("/api/v1/passengers/{id}", id));
    }

    @Then("the response should indicate successful delete of passenger with code {int}\\(NO_CONTENT)")
    public void theResponseShouldIndicateSuccessfulDeleteOfPassengerWithCodeNO_CONTENT(int status) throws Exception {
        result.andExpect(status().is(status));
    }

    @Then("request complete with code {int}\\(NOT_FOUND) and indicates that specified for delete passenger not found")
    public void requestCompleteWithCodeNOT_FOUNDAndIndicatesThatSpecifiedForDeletePassengerNotFound(int status) throws Exception {
        result.andExpect(status().is(status));
    }
}
