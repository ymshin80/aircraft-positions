package com.dev.aircraft_positions;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AircraftPositionsApplication {

	public static void main(String[] args) {
		SpringApplication application = new SpringApplication(AircraftPositionsApplication.class);
		
		application.setWebApplicationType(WebApplicationType.REACTIVE);
		
		application.run( args);
		
		
	}

}
