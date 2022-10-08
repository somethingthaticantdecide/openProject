package edu.schoo21.openprojectback.models;

import edu.schoo21.openprojectback.models.dto.UserDto;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@AllArgsConstructor
@Table(name = "usr")
@Entity
public class User implements UserDetails {
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
    private boolean accountNonExpired;
    private boolean accountNonLocked;
    private boolean credentialsNonExpired;
    private boolean enabled;
    @OneToMany
    @ToString.Exclude
    private List<Feedback> feedbacks;
    @OneToMany
    @ToString.Exclude
    private List<Cat> cats;
    @OneToMany
    @ToString.Exclude
    private List<Cat> favorites;

    public User(String login, String password, String name, Long phoneNumber, String mail, String address, String avatar, Float ranking) {
        this.login = login;
        this.password = password;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.mail = mail;
        this.address = address;
        this.avatar = avatar;
        this.ranking = Objects.requireNonNullElse(ranking, 0f);
    }

    public User(UserDto userDto) {
        this.login = userDto.getLogin();
        this.password = userDto.getPassword();
        this.name = userDto.getName();
        this.phoneNumber = userDto.getPhoneNumber();
        this.mail = userDto.getMail();
        this.address = userDto.getAddress();
        this.ranking = userDto.getRanking();
        this.accountNonExpired = true;
        this.accountNonLocked = true;
        this.credentialsNonExpired = true;
        this.enabled = true;
        this.ranking = Objects.requireNonNullElse(ranking, 0f);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getUsername() {
        return this.login;
    }

    @Override
    public boolean isAccountNonExpired() {
        return this.accountNonExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return this.accountNonLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return this.credentialsNonExpired;
    }

    @Override
    public boolean isEnabled() {
        return this.enabled;
    }
}
