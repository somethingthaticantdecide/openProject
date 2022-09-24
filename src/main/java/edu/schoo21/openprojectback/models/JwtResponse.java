package edu.schoo21.openprojectback.models;

import lombok.Data;

@Data
public class JwtResponse {

	private final String jwttoken;
	private final User user;

	public JwtResponse(String jwttoken, User user) {
		this.jwttoken = jwttoken;
		this.user = user;
	}

}