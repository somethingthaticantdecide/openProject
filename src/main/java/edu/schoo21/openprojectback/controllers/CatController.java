package edu.schoo21.openprojectback.controllers;

import edu.schoo21.openprojectback.models.Cat;
import edu.schoo21.openprojectback.models.dto.CatDto;
import edu.schoo21.openprojectback.services.CatsService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

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
    public Collection<Cat> getAllCats() {
        return catsService.findAll();
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public Cat addNewCat(@RequestBody CatDto catDto) {
        return catsService.addNewCat(catDto);
    }

    @GetMapping("/{cat-id}")
    @ResponseStatus(HttpStatus.OK)
    public Cat getCat(@PathVariable("cat-id") String id) {
        return catsService.findById(Long.valueOf(id));
    }

    @PutMapping("/{cat-id}")
    @ResponseStatus(HttpStatus.OK)
    public Cat updateCat(@RequestBody CatDto catDto, @PathVariable("cat-id") String id) {
        return catsService.update(catDto, id);
    }

    @DeleteMapping("/{cat-id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCat(@PathVariable("cat-id") String id) {
        catsService.deleteById(Long.valueOf(id));
    }
}
