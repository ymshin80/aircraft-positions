package com.dev.aircraft_positions.utils;

import java.util.List;
import java.util.function.Consumer;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import com.dev.aircraft_positions.domain.Aircraft;
import com.dev.aircraft_positions.repository.AircraftRepository;

//@Configuration
@Component
public class PositionRetriever {

	
	private final AircraftRepository repository;
	private final WebClient client;
	
	public PositionRetriever(AircraftRepository repository, WebClient client) {
		this.client = client;
		this.repository = repository;
	}
	
	public Iterable<Aircraft> retrieveAircraftPositions(String endpoint) {
		
		repository.deleteAll();
		
		client.get()
			.uri((null != endpoint) ? endpoint : "")
			.retrieve()
			.bodyToFlux(Aircraft.class)
			.filter(ac -> !ac.getReg().isEmpty())
			.toStream()
			.forEach(repository::save);
		
		return repository.findAll();
		
	}
	
//	private final AircraftRepository repository;
//	private final WebSocketHandler handler;
//	
//	public PositionRetriever(AircraftRepository repository, WebSocketHandler handler) {
//		this.handler = handler;
//		this.repository = repository;
//	}
//	
//	@Bean
//	Consumer<List<Aircraft>> retrieveAircraftPositions() {
//		System.out.println("##########################retrieveAircraftPositions##############################");
//		return acList -> {
//			repository.deleteAll();
//			repository.saveAll(acList);
//			
//			sendPositions();
//		};
//	}
//
//	private void sendPositions() {
//		if(repository.count() > 0) {
//			for(WebSocketSession s : handler.getSessionList()) {
//				try {
//					s.sendMessage(
//						new TextMessage(repository.findAll().toString())
//					);
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			}
//		}
//	}
}
