package edu.schoo21.openprojectback.models;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@AllArgsConstructor
@Table(name = "usr")
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    private String login;
    private String password;
    private String name;
    private Long phoneNumber;
    private String mail;
    private String address;
    private String avatar;
    private Float ranking;
    @OneToMany
    @ToString.Exclude
    private List<Feedback> feedbacks;
    @OneToMany
    @ToString.Exclude
    private List<Cat> cats;
    @OneToMany
    @ToString.Exclude
    private List<Cat> favorites;
    @OneToMany
    @ToString.Exclude
    private List<Chat> chats;

    public User(String login, String password, String name, Long phoneNumber, String mail, String address, String avatar, Float ranking) {
        this.login = login;
        this.password = password;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.mail = mail;
        this.address = address;
        this.avatar = avatar;
        this.ranking = ranking;
    }
}
