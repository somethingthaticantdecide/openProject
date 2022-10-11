package edu.schoo21.openprojectback.controllers;

import edu.schoo21.openprojectback.models.Avatar;
import edu.schoo21.openprojectback.models.Cat;
import edu.schoo21.openprojectback.models.dto.CatDto;
import edu.schoo21.openprojectback.models.response.CatProfileResponse;
import edu.schoo21.openprojectback.requests.CatSearchRequest;
import edu.schoo21.openprojectback.requests.SearchRequest;
import edu.schoo21.openprojectback.services.AvatarService;
import edu.schoo21.openprojectback.services.CatsService;
import edu.schoo21.openprojectback.specification.CatSpecifications;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.jpa.domain.Specification;
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

    @PostMapping("/search")
    @ResponseStatus(HttpStatus.OK)
    public Collection<CatProfileResponse> searchCats(@RequestBody CatSearchRequest searchRequest) {
        Specification<Cat> specs = Specification
                .where(CatSpecifications.likeAddress(searchRequest.getAddress()))
                .and(CatSpecifications.likeBreed(searchRequest.getBreed()))
                .and(CatSpecifications.equalAge(searchRequest.getAge()))
                .and(CatSpecifications.equalSex(searchRequest.getSex()))
                .and(CatSpecifications.equalPassport(searchRequest.getPassport()))
                .and(CatSpecifications.equalVaccination(searchRequest.getVaccination()))
                .and(CatSpecifications.equalCertificates(searchRequest.getCertificates()))
                .and(CatSpecifications.priceFrom(searchRequest.getPriceFrom()))
                .and(CatSpecifications.priceTo(searchRequest.getPriceTo()));
        return catsService.findAll(specs).stream().map(CatProfileResponse::new).collect(Collectors.toCollection(ArrayList::new));
    }

    @PostMapping("/filter")
    @ResponseStatus(HttpStatus.OK)
    public Collection<CatProfileResponse> filterCats(@RequestBody SearchRequest searchRequest) {
        Specification<Cat> specs = Specification
                .where(CatSpecifications.likeAddress(searchRequest.getSearch()))
                .or(CatSpecifications.likeBreed(searchRequest.getSearch()))
                .or(CatSpecifications.likeInfo(searchRequest.getSearch()))
                .or(CatSpecifications.likeName(searchRequest.getSearch()));
        return catsService.findAll(specs).stream().map(CatProfileResponse::new).collect(Collectors.toCollection(ArrayList::new));
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
        Cat cat = catsService.findById(id);
        return catsService.update(catDto, cat);
    }

    @DeleteMapping("/{cat-id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCat(@PathVariable("cat-id") String id) {
        catsService.deleteById(Long.valueOf(id));
    }

    @PostMapping(value = "/{cat-id}/photo", consumes = "multipart/form-data")
    public ResponseEntity<?> addPhoto(@RequestParam("file") MultipartFile file, @PathVariable("cat-id") Long id) throws IOException {
        Avatar avatar = new Avatar();
        Cat cat = catsService.findById(id);
        if (cat != null && file.getSize() > 0) {
            avatar.setAvatar(Base64.getEncoder().encodeToString(file.getBytes()));
            avatarService.saveAndFlush(avatar);
            cat.setPhoto(APP_ADDRESS + AVATARS + avatar.getId());
            catsService.save(cat);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.badRequest().build();
    }

}
