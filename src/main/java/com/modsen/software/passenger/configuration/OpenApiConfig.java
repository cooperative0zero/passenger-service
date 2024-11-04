package com.modsen.software.passenger.configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;

@OpenAPIDefinition(
        info = @Info(
                title = "Passengers service Api",
                description = "Passengers service", version = "1.0.0",
                contact = @Contact(
                        name = "Aleksej",
                        email = "example@mail.com"
                )
        )
)
public class OpenApiConfig {

}
