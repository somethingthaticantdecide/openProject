package edu.schoo21.openprojectback.services;

import edu.schoo21.openprojectback.repository.ChatRepository;
import org.springframework.stereotype.Service;

@Service
public class ChatService {
    private final ChatRepository chatRepository;

    public ChatService(ChatRepository chatRepository) {
        this.chatRepository = chatRepository;
    }
}
