package edu.schoo21.openprojectback.models.dto;

import edu.schoo21.openprojectback.models.Message;
import edu.schoo21.openprojectback.models.User;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Data
public class ChatDto {
    private User user;
    private List<Message> messages;
}
