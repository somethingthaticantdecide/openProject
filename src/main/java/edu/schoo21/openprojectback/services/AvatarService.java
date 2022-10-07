package edu.schoo21.openprojectback.services;

import edu.schoo21.openprojectback.models.Avatar;
import edu.schoo21.openprojectback.models.User;
import edu.schoo21.openprojectback.repository.AvatarRepository;
import edu.schoo21.openprojectback.repository.ChatRepository;
import org.springframework.stereotype.Service;

@Service
public class AvatarService {
    private final AvatarRepository avatarRepository;

    public AvatarService(AvatarRepository avatarRepository) {
        this.avatarRepository = avatarRepository;
    }

    public Avatar findById(Long id) {
        return avatarRepository.findById(id).orElse(null);
    }

    public void save(Avatar avatar) {
        avatarRepository.save(avatar);
    }

    public void saveAndFlush(Avatar avatar) {
        avatarRepository.saveAndFlush(avatar);
    }
}
