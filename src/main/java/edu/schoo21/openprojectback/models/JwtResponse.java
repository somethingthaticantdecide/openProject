package edu.schoo21.openprojectback.models;

import lombok.Data;

@Data
public class JwtResponse {

	private final String jwttoken;
	private String login;
	private String name;
	private Long phoneNumber;
	private String mail;
	private String address;
	private String avatar;
	private Float ranking;

	public JwtResponse(String jwttoken, String login, String name, Long phoneNumber, String mail, String address, String avatar, Float ranking) {
		this.jwttoken = jwttoken;
		this.login = login;
		this.name = name;
		this.phoneNumber = phoneNumber;
		this.mail = mail;
		this.address = address;
		this.avatar = avatar;
		this.ranking = ranking;
	}

}