package edu.schoo21.openprojectback.models.dto;

import lombok.*;

@Data
public class UserDto {
    private String login;
    private String password;
    private String name;
    private Long phoneNumber;
    private String mail;
    private String address;
    private String avatar;
    private Float ranking;
}
