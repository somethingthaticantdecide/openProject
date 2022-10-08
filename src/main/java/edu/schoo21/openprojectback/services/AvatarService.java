package edu.schoo21.openprojectback.services;

import edu.schoo21.openprojectback.models.Avatar;
import edu.schoo21.openprojectback.repository.AvatarRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Base64;

@Service
public class AvatarService {
    private final AvatarRepository avatarRepository;
    public static final String AVATARS = "/avatars/";
    @Value("${app.address}")
    public String APP_ADDRESS;

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

    public String saveImageToBase(MultipartFile file) throws IOException {
        if (file != null && file.getSize() > 0) {
            Avatar avatar = new Avatar();
            avatar.setAvatar(Base64.getEncoder().encodeToString(file.getBytes()));
            saveAndFlush(avatar);
            return APP_ADDRESS + AVATARS + avatar.getId();
        }
        return "";
    }
}
