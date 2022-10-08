package edu.schoo21.openprojectback.models.dto;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

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
