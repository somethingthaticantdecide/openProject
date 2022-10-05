package edu.schoo21.openprojectback.controllers;

import edu.schoo21.openprojectback.models.User;
import edu.schoo21.openprojectback.services.UsersService;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Base64;

@Controller
@AllArgsConstructor
@RequestMapping("/images")
public class ImagesController {

    private final UsersService usersService;

    @GetMapping(value = "/{user-id}/poster", produces = MediaType.IMAGE_JPEG_VALUE)
    public ResponseEntity<byte[]> getContent(@PathVariable("user-id") Long id) {
        User user = usersService.findById(id);
        byte[] bytes = Base64.getDecoder().decode(user.getAvatar());
        return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(bytes);
    }
}