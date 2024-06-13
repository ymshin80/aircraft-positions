package com.dev.aircraft_positions.web;

import org.springframework.messaging.rsocket.RSocketRequester;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.result.view.Rendering;

import java.net.MalformedURLException;
import java.net.URL;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import com.dev.aircraft_positions.domain.Aircraft;
import com.dev.aircraft_positions.repository.AircraftRepository;
import com.dev.aircraft_positions.utils.PositionRetriever;
import com.okta.spring.boot.oauth.config.OktaOAuth2Properties;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Controller
public class PostionController {

	private static final Logger logger = LoggerFactory.getLogger(PostionController.class);
	
	private final PositionRetriever retriever;
	private static final String STATE = "state";
	private static final String SCOPES = "scopes";
	private static final String OKTA_BASE_URL = "oktaBaseUrl";
	private static final String OKTA_CLIENT_ID = "oktaClientId";
	private static final String REDIRECT_URI = "redirectUri";
	private static final String ISSUER_URI = "issuerUri";

	private final OktaOAuth2Properties oktaOAuth2Properties;

	/*
	 * private final AircraftRepository repository; private final RSocketRequester
	 * requester; private WebClient client =
	 * WebClient.create("http://localhost:7634/aircraft");
	 */
	private final ClientRegistrationRepository clientRegistrationRepository;

    public PostionController(ClientRegistrationRepository clientRegistrationRepository, PositionRetriever retriever, OktaOAuth2Properties oktaOAuth2Properties) {
        this.oktaOAuth2Properties = oktaOAuth2Properties;
        this.retriever = retriever;
        this.clientRegistrationRepository = clientRegistrationRepository;
    }
    
    @GetMapping("/success")
    public String loginSuccess(@RequestParam String accessToken, Model model) {
    	model.addAttribute("accessToken", accessToken);
    	return "";
    }
    
    @GetMapping("/test")
    public String test(Model model) {
    	if(logger.isDebugEnabled()) {
    		logger.debug("################test##################");
    	}
    	ClientRegistration oktaRegistration =this.clientRegistrationRepository.findByRegistrationId("kakao");
    	
    	model.addAttribute("grantType", oktaRegistration.getAuthorizationGrantType().getValue());
    	model.addAttribute("registrationId", oktaRegistration.getRegistrationId());
    	model.addAttribute("redirectUri", oktaRegistration.getRedirectUri());
    	model.addAttribute("scopes", oktaRegistration.getScopes());
    	return "pages/views/oauthProperties";
    }
    
    
    @GetMapping("/okta-custom-login")
    public String login(@RequestParam String state, Model model) throws MalformedURLException {
    	logger.debug("#######################login#########################");
    	logger.debug("######{}######", state);
    	
    	logger.debug("######{}######", oktaOAuth2Properties.getScopes());
    	logger.debug("######{}######", oktaOAuth2Properties.getClientId());
    	
    	String issuer = oktaOAuth2Properties.getIssuer();
    	logger.debug("###issuer###{}######", issuer);
    	String orgUrl = new URL(new URL(issuer), "/").toString();
    	logger.debug("####orgUrl##{}######", orgUrl);
    	
    	logger.debug("#############################");
    	model.addAttribute(STATE, state);
    	model.addAttribute(SCOPES, oktaOAuth2Properties.getScopes());
    	model.addAttribute(OKTA_BASE_URL, orgUrl);
    	model.addAttribute(OKTA_CLIENT_ID, oktaOAuth2Properties.getClientId());
    	model.addAttribute(REDIRECT_URI, "/login/oauth2/code/okta");
    	model.addAttribute(ISSUER_URI, issuer);
    	
    	return "pages/views/login";
    }
	    
	
	@GetMapping("/aircraft")
	public Iterable<Aircraft> getCurrentAircraftPositions() {
		return retriever.retrieveAircraftPositions("aircraft");
				
	}
	
	@GetMapping("/aircraftadmin")
	public  Iterable<Aircraft> getCurrentAircraftPositionsAdminPrivs() {
		return retriever.retrieveAircraftPositions("aircraftadmin");
	}
	
//	public PostionController(AircraftRepository repository, RSocketRequester.Builder builder) {
//		this.repository = repository;
//		this.requester = builder.tcp("localhost", 7635);
//	}
//	
//	@GetMapping("/aircraft")
//	public String getCurrentAircraftPosition(Model model) {
////		Flux<Aircraft> aircraftFlux = repository.deleteAll()
////					.thenMany(client.get()
////									.retrieve()
////									.bodyToFlux(Aircraft.class)
////									.filter(p -> !p.getReg().isEmpty())
////									.flatMap(repository::save)
////					);
//		//return Mono.just(Rendering.view("pages/views/positions").modelAttribute("currentPositions", aircraftFlux).build());
//		//model.addAttribute("currentPositions", aircraftFlux);
//		return "pages/views/positions";
//	}
//	
//	@GetMapping(value="/acstream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
//	public Flux<Aircraft> getCurrentACPositionStream() {
//		return requester.route("acstream")
//					.data("Requesting aircraft positions")
//					.retrieveFlux(Aircraft.class);
//	}
	
}
