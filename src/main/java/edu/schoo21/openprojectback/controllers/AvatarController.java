package edu.schoo21.openprojectback.controllers;

import edu.schoo21.openprojectback.services.AvatarService;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Base64;

@Controller
@AllArgsConstructor
@RequestMapping("/avatars")
public class AvatarController {

    private final AvatarService avatarService;

    @GetMapping(value = "/{user-id}", produces = MediaType.IMAGE_JPEG_VALUE)
    public ResponseEntity<byte[]> getContent(@PathVariable("user-id") Long id) {
        return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(Base64.getDecoder().decode(avatarService.findById(id).getAvatar()));
    }
}