package edu.schoo21.openprojectback.models;

import lombok.*;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@AllArgsConstructor
@Entity
public class Feedback {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    private Long user_id;
    private Date date;
    @Column(columnDefinition="text")
    private String text;
    private String name;
    private String avatar;
    private Integer rating;

    public Feedback(Long user_id, Date date, String text, String name, Integer rating, String avatar) {
        this.user_id = user_id;
        this.date = date;
        this.text = text;
        this.name = name;
        this.rating = Objects.requireNonNullElse(rating, 1);
        this.avatar = avatar;
    }
}
