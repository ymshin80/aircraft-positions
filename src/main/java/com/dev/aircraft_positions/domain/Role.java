package com.dev.aircraft_positions.domain;

public enum Role {

	ROLE_USER("ROLE_USER"),
	ROLE_ANONYMOUS("ROLE_ANONYMOUS");
	
	String role;
	
	Role(String role) {
		this.role = role;
	}
	
	public String value() {
		return role;
	}
}
