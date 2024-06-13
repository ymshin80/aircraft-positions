package com.dev.aircraft_positions.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;

@Entity
public class Member {
	
	@Id	
	private Long id;
	
	
	private String email;
	
	private String profile;
	
	@JsonProperty("nick_name")
	private String nickName;
	
	
	@Enumerated(EnumType.STRING)
	private Role state;
	
	public Member() {}
	public Member(Long id, String email, String profile, String nickName, Role state) {
		this.id = id;
		this.email = email;
		this.profile = profile;
		this.nickName = nickName;
		this.state = state;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public Role getState() {
		return state;
	}

	public void setState(Role state) {
		this.state = state;
	}
	
	public String getProfile() {
		return profile;
	}

	public void setProfile(String profile) {
		this.profile = profile;
	}

	public static Member.Builder builder() {
		return new Member.Builder();
	}
	
	public static class Builder {
		private Long id;
		private String email;
		private String nickName;
		private String profile;
		private Role state;
		
		Builder() {}
		
		public Builder id(final Long id) {
			this.id = id;
			return this;
		}
		public Builder email(final String email) {
			this.email = email;
			return this;
		}
		
		public Builder nickName(final String nickName) {
			this.nickName = nickName;
			return this;
		}
		
		public Builder profile(final String profile) {
			this.profile = profile;
			return this;
		}
		public Builder state(final Role state) {
			this.state = state;
			return this;
		}
		public Member build() {
			return new Member(this.id, this.email, this.profile, this.nickName, this.state);
		}
			
	}
	
	
	
}
