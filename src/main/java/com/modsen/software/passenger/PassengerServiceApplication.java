package com.modsen.software.passenger;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;

@SpringBootApplication
public class PassengerServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(PassengerServiceApplication.class, args);
	}

}
