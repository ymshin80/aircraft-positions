package com.dev.aircraft_positions.domain;

import java.util.Map;
import java.util.Objects;

public record OAuth2UserInfo(String name,String email,String profile) {
	//compact 생성자
	public OAuth2UserInfo {
		
		Objects.requireNonNull(name);
		Objects.requireNonNull(email);
		//해더의 값 유효성 체크 
		if(!email.contains("@")) {
			throw new IllegalArgumentException("email 형식");
		}
		
		Objects.requireNonNull(profile);
		
	}
	
	public static OAuth2UserInfo of(String registrationId, Map<String, Object> attributes) {
		return switch (registrationId) {
			case "okta" -> ofOkta(attributes);
			case "kakao" -> ofKakao(attributes); 
			default -> throw new IllegalArgumentException("Unexpected value: " + registrationId);
		};
	}
	
	
	private static OAuth2UserInfo ofKakao(Map<String, Object> attributes) {
		Map<String, Object> properties = (Map<String, Object>) attributes.get("properties");
	//    Map<String, Object> profile = (Map<String, Object>) account.get("profile");
	    
		return OAuth2UserInfo.builder()
				.name((String)properties.get("nickname"))
				.email("siyumy80@gmail.com")
				.profile((String)properties.get("profile_image")).build();
	}

	private static OAuth2UserInfo ofOkta(Map<String, Object> attributes) {
		return OAuth2UserInfo.builder()
				.name((String)attributes.get("name"))
				.email((String)attributes.get("email"))
				.profile((String)attributes.get("profile")).build();
	}
	
	public static OAuth2UserInfo.Builder builder() {
		return new OAuth2UserInfo.Builder();
	}
	
	public static class Builder {
		private String name;
		private String email;
		private String profile;
		
		Builder(){}
	
		public Builder name(final String name) {
			this.name = name;
			return this;
		}
		
		public Builder email(final String email) {
			this.email = email;
			return this;
		}
		
		public Builder profile(final String profile) {
			this.profile = profile;
			return this;
		}
		
		public OAuth2UserInfo build() {
			return new OAuth2UserInfo(this.name, this.email, this.profile);
		}
		
	
	}
	
	public  Member toEntity() {
		return Member.builder()
				.nickName(name)
				.email(email)
				.profile(profile)
				.id(null)
				.state(Role.ROLE_USER)
				.build();
	}
}