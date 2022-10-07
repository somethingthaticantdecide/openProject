package edu.schoo21.openprojectback.controllers;

import edu.schoo21.openprojectback.models.Avatar;
import edu.schoo21.openprojectback.models.Cat;
import edu.schoo21.openprojectback.models.User;
import edu.schoo21.openprojectback.models.dto.CatDto;
import edu.schoo21.openprojectback.models.response.CatProfileResponse;
import edu.schoo21.openprojectback.services.AvatarService;
import edu.schoo21.openprojectback.services.CatsService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Collection;
import java.util.stream.Collectors;

@CrossOrigin
@RestController
@RequiredArgsConstructor
@RequestMapping("/cats")
public class CatController {
    public static final String AVATARS = "/avatars/";
    @Value("${app.address}")
    public String APP_ADDRESS;
    private final AvatarService avatarService;
    private final CatsService catsService;

    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public Collection<CatProfileResponse> getAllCats() {
        return catsService.findAll().stream().map(CatProfileResponse::new).collect(Collectors.toCollection(ArrayList::new));
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public Cat addNewCat(@RequestBody CatDto catDto) {
        return catsService.addNewCat(catDto);
    }

    @GetMapping("/{cat-id}")
    @ResponseStatus(HttpStatus.OK)
    public CatProfileResponse getCat(@PathVariable("cat-id") Long id) {
        return new CatProfileResponse(catsService.findById(id));
    }

    @PutMapping("/{cat-id}")
    @ResponseStatus(HttpStatus.OK)
    public Cat updateCat(@RequestBody CatDto catDto, @PathVariable("cat-id") Long id) {
        return catsService.update(catDto, id);
    }

    @DeleteMapping("/{cat-id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCat(@PathVariable("cat-id") String id) {
        catsService.deleteById(Long.valueOf(id));
    }

    @PostMapping(value = "/{cat-id}/avatar", consumes = "multipart/form-data")
    public ResponseEntity<?> addFilm(@RequestParam("file") MultipartFile file, @PathVariable("cat-id") Long id) throws IOException {
        Avatar avatar = new Avatar();
        Cat cat = catsService.findById(id);
        if (cat != null && file.getSize() > 0) {
            avatar.setAvatar(Base64.getEncoder().encodeToString(file.getBytes()));
            avatarService.saveAndFlush(avatar);
            cat.setAvatar(APP_ADDRESS + AVATARS + avatar.getId());
            catsService.save(cat);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.badRequest().build();
    }

}
