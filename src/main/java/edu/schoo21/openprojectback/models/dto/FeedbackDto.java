package edu.schoo21.openprojectback.models.dto;

import lombok.*;
import java.util.Date;

@Data
public class FeedbackDto {
    private Long userId;
    private Date date;
    private String text;
    private Integer rating;
}
