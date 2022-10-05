package edu.schoo21.openprojectback.models;

import lombok.*;

import javax.persistence.*;
import java.util.Date;

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
    private String text;
    private String name;
//    private String avatar;
    private Integer rating;

    public Feedback(Long user_id, Date date, String text, String name, Integer rating) {
        this.user_id = user_id;
        this.date = date;
        this.text = text;
        this.name = name;
//        this.avatar = avatar;
        this.rating = rating != null ? rating : Integer.valueOf(1);
    }
}
