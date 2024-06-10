package com.dev.aircraft_positions.web;

import org.springframework.messaging.rsocket.RSocketRequester;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.result.view.Rendering;
import org.springframework.http.MediaType;
import com.dev.aircraft_positions.domain.Aircraft;
import com.dev.aircraft_positions.repository.AircraftRepository;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Controller
public class PostionController {

	private final AircraftRepository repository;
	private final RSocketRequester requester;
	private WebClient client = 
			WebClient.create("http://localhost:7634/aircraft");
	
	
	
	public PostionController(AircraftRepository repository, RSocketRequester.Builder builder) {
		this.repository = repository;
		this.requester = builder.tcp("localhost", 7635);
	}
	
	@GetMapping("/aircraft")
	public String getCurrentAircraftPosition(Model model) {
		Flux<Aircraft> aircraftFlux = repository.deleteAll()
					.thenMany(client.get()
									.retrieve()
									.bodyToFlux(Aircraft.class)
									.filter(p -> !p.getReg().isEmpty())
									.flatMap(repository::save)
					);
		
		//return Mono.just(Rendering.view("pages/views/positions").modelAttribute("currentPositions", aircraftFlux).build());
		model.addAttribute("currentPositions", aircraftFlux);
		return "pages/views/positions";
	}
	
	@GetMapping(value="/acstream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
	public Flux<Aircraft> getCurrentACPositionStream() {
		return requester.route("acstream")
					.data("Requesting aircraft positions")
					.retrieveFlux(Aircraft.class);
	}
	
}
