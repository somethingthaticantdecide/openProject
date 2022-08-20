package edu.schoo21.openprojectback.models;

import edu.schoo21.openprojectback.models.dto.FeedbackDto;
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
    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;
    private Date date;
    private String text;

    public Feedback(User user, Date date, String text) {
        this.user = user;
        this.date = date;
        this.text = text;
    }
}
