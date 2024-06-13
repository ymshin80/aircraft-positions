package com.dev.aircraft_positions.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.security.oauth2.resource.OAuth2ResourceServerProperties;
import org.springframework.boot.autoconfigure.security.reactive.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.client.oidc.web.logout.OidcClientInitiatedLogoutSuccessHandler;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.web.OAuth2AuthorizedClientRepository;
import org.springframework.security.oauth2.client.web.reactive.function.client.ServletOAuth2AuthorizedClientExchangeFilterFunction;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtDecoders;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.reactive.function.client.WebClient;

import com.dev.aircraft_positions.security.handler.OAuth2SuccessHandler;

import static org.springframework.security.config.Customizer.withDefaults;
@Configuration
@EnableWebSecurity
@EnableMethodSecurity(securedEnabled = true)
public class SecurityConfig {

	@Value("${spring.security.oauth2.client.provider.okta.issuer-uri}")
	private String issuerUri;
	
	private final ClientRegistrationRepository clientRegistrationRepository;
	//private final Custom
	private final CustomOAuth2UserService oauth2UserService;
	private final OAuth2SuccessHandler oauth2SuccessHandler;
	
	public SecurityConfig(ClientRegistrationRepository clientRegistrationRepository, CustomOAuth2UserService oauth2UserService, OAuth2SuccessHandler oauth2SuccessHandler) {
		this.clientRegistrationRepository = clientRegistrationRepository;
		this.oauth2UserService = oauth2UserService;
		this.oauth2SuccessHandler = oauth2SuccessHandler;
	}
	
//	@Bean
//	public WebSecurityCustomizer webSecurityCustomizer() {
//		return (web) -> {
//			web
//				.ignoring()
//				.requestMatchers(
//					AntPathRequestMatcher.antMatcher("/test")
//				);
//		};
//	}
	
	
	@Bean
	public SecurityFilterChain configure(HttpSecurity http) throws Exception {
		
		http
			.csrf(AbstractHttpConfigurer::disable)
			.cors(AbstractHttpConfigurer::disable)
			.formLogin(AbstractHttpConfigurer::disable)
			.logout(AbstractHttpConfigurer::disable)
			.sessionManagement(c -> {
				c.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
			})
			.authorizeHttpRequests((requests) ->  
				requests.requestMatchers(
					AntPathRequestMatcher.antMatcher("/"),
					AntPathRequestMatcher.antMatcher("/test"),
					AntPathRequestMatcher.antMatcher("/auth/success")
				).permitAll()
				.anyRequest().authenticated());
		http.oauth2Client(withDefaults());
		http.logout((logout) -> logout.logoutSuccessHandler(oidcLogoutSuccessHandler()));
		http.oauth2Login(oauth ->
		// OAuth2 로그인 기능에 대한 여러 설정의 진입점
		// OAuth2 로그인 성공 이후 사용자 정보를 가져올 때의 설정을 담당
			oauth.userInfoEndpoint(c -> c.userService(oauth2UserService))
			.successHandler(oauth2SuccessHandler)
		);
		
		return http.build();
	}
	
	
	private OidcClientInitiatedLogoutSuccessHandler oidcLogoutSuccessHandler() {
		
		OidcClientInitiatedLogoutSuccessHandler logoutHandler = 
				new OidcClientInitiatedLogoutSuccessHandler(clientRegistrationRepository);
		logoutHandler.setPostLogoutRedirectUri("http://localhost:8082");
		
		return logoutHandler;
	}


	@Bean
	WebClient client(ClientRegistrationRepository regRepo, OAuth2AuthorizedClientRepository cliRepo) {
		
		ServletOAuth2AuthorizedClientExchangeFilterFunction filter = 
				new ServletOAuth2AuthorizedClientExchangeFilterFunction(regRepo, cliRepo);
		
		filter.setDefaultOAuth2AuthorizedClient(true);
		
		return WebClient.builder()
				.baseUrl("http://localhost:7634/")
				.apply(filter.oauth2Configuration())
				.build();
	}
	
	
	@Bean
	public JwtDecoder jwtDecoder(OAuth2ResourceServerProperties properties) {
	    return JwtDecoders.fromIssuerLocation(issuerUri);
	}
}
