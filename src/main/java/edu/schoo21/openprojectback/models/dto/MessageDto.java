package edu.schoo21.openprojectback.models.dto;

import lombok.*;

import java.util.Date;

@Data
public class MessageDto {
    private Date date;
    private String message;
}
