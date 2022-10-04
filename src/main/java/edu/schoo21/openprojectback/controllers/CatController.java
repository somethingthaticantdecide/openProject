package edu.schoo21.openprojectback.controllers;

import edu.schoo21.openprojectback.models.Cat;
import edu.schoo21.openprojectback.models.dto.CatDto;
import edu.schoo21.openprojectback.models.response.CatProfileResponse;
import edu.schoo21.openprojectback.services.CatsService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;

@CrossOrigin
@RestController
@RequestMapping("/cats")
public class CatController {

    private final CatsService catsService;

    public CatController(CatsService catsService) {
        this.catsService = catsService;
    }

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
}
