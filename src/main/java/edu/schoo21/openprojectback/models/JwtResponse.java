package edu.schoo21.openprojectback.models;

import lombok.Data;
import java.util.List;

@Data
public class JwtResponse {

	private final String jwttoken;
	private Long id;
	private String login;
	private String name;
	private Long phoneNumber;
	private String mail;
	private String address;
	private String avatar;
	private Float ranking;
	private List<Feedback> feedbacks;
	private List<Cat> cats;
	private List<Cat> favorites;
	private List<Chat> chats;

	public JwtResponse(String jwttoken, User user) {
		this.jwttoken = jwttoken;
		this.id = user.getId();
		this.login = user.getLogin();
		this.name = user.getName();
		this.phoneNumber = user.getPhoneNumber();
		this.mail = user.getMail();
		this.address = user.getAddress();
		this.avatar = user.getAvatar();
		this.ranking = user.getRanking();
		this.feedbacks = user.getFeedbacks();
		this.cats = user.getCats();
		this.favorites = user.getFavorites();
		this.chats = user.getChats();
	}

}