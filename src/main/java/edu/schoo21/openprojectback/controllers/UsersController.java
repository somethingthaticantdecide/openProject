package edu.schoo21.openprojectback.controllers;

import edu.schoo21.openprojectback.models.Cat;
import edu.schoo21.openprojectback.models.Feedback;
import edu.schoo21.openprojectback.models.User;
import edu.schoo21.openprojectback.models.dto.CatDto;
import edu.schoo21.openprojectback.models.dto.FeedbackDto;
import edu.schoo21.openprojectback.models.dto.UserDto;
import edu.schoo21.openprojectback.requests.IdRequest;
import edu.schoo21.openprojectback.services.AvatarService;
import edu.schoo21.openprojectback.services.CatsService;
import edu.schoo21.openprojectback.services.FeedbackService;
import edu.schoo21.openprojectback.services.UsersService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Collection;

@CrossOrigin
@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UsersController {
    public static final String AVATARS = "/avatars/";
    @Value("${app.address}")
    public String APP_ADDRESS;
    private final UsersService usersService;
    private final FeedbackService feedbackService;
    private final CatsService catsService;
    private final AvatarService avatarService;

    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public Collection<User> getAllUsers() {
        return usersService.findAll();
    }

    @GetMapping("/{user-id}")
    @ResponseStatus(HttpStatus.OK)
    public User getUser(@PathVariable("user-id") Long id) {
        return usersService.findById(id);
    }

    @GetMapping("/info")
    @ResponseStatus(HttpStatus.OK)
    public User getInfo(Authentication authentication) {
        if (authentication != null) {
            return usersService.findByLogin(authentication.getName());
        }
        return null;
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> addNewUser(@RequestBody UserDto userDto) {
        if (usersService.findUserByLogin(userDto.getLogin()) != null)
            return ResponseEntity.badRequest().body("User with this login already exist!");
        User user = usersService.addNewUser(userDto);
        return ResponseEntity.ok(user);
    }

    @PutMapping("/{user-id}")
    @ResponseStatus(HttpStatus.OK)
    public User updateUser(@RequestBody UserDto userDto, @PathVariable("user-id") Long id) {
        return usersService.updateUser(userDto, id);
    }

    @DeleteMapping("/{user-id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(@PathVariable("user-id") Long id) {
        usersService.deleteById(id);
    }

    @PostMapping(value = "/{user-id}/avatar", consumes = "multipart/form-data")
    public ResponseEntity<?> addAvatar(@RequestParam("file") MultipartFile file, @PathVariable("user-id") Long id) throws IOException {
        try {
            User user = usersService.findById(id);
            user.setAvatar(avatarService.addAvatar(file));
            usersService.save(user);
        } catch (IOException e) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok().build();
    }

    /////////////////////////

    @GetMapping("/{user-id}/feedbacks")
    @ResponseStatus(HttpStatus.OK)
    public Collection<Feedback> getFeedbacksByUser(@PathVariable("user-id") Long id) {
        return usersService.findById(id).getFeedbacks();
    }

    @PostMapping("/{user-id}/feedbacks")
    @ResponseStatus(HttpStatus.CREATED)
    public Feedback addFeedbackToUser(@RequestBody FeedbackDto feedbackDto, @PathVariable("user-id") Long userId) {
        User user = usersService.findById(userId);
        User feedbackUser = usersService.findById(feedbackDto.getUserId());
        Feedback feedback = new Feedback(feedbackUser.getId(), feedbackDto.getDate(), feedbackDto.getText(),
                feedbackUser.getName(), feedbackDto.getRating());
        feedbackService.save(feedback);
        user.getFeedbacks().add(feedback);
        usersService.countRanking(user);
        usersService.save(user);
        return feedback;
    }

    @PutMapping("/{user-id}/feedbacks/{feedback-id}")
    @ResponseStatus(HttpStatus.OK)
    public User updateFeedbackToUser(@RequestBody FeedbackDto feedbackDto, @PathVariable("user-id") Long userId,
                                     @PathVariable("feedback-id") Long feedbackId) {
        Feedback feedback = feedbackService.findById(feedbackId);
        feedback.setUser_id(feedbackDto.getUserId());
        feedback.setDate(feedbackDto.getDate());
        feedback.setText(feedbackDto.getText());
        feedbackService.save(feedback);
        User user = usersService.findById(userId);
        usersService.countRanking(user);
        return user;
    }

    @DeleteMapping("/{user-id}/feedbacks/{feedback-id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteFeedbacksFromUser(@PathVariable("user-id") Long userId, @PathVariable("feedback-id") Long feedbackId) {
        User user = usersService.findById(userId);
        Feedback feedback = feedbackService.findById(feedbackId);
        user.getFeedbacks().remove(feedback);
        feedbackService.deleteById(feedbackId);
        usersService.save(user);
    }

    /////////////////////////

    @GetMapping("/{user-id}/cats")
    @ResponseStatus(HttpStatus.OK)
    public Collection<Cat> getCatsByUser(@PathVariable("user-id") String id) {
        return usersService.findById(Long.valueOf(id)).getCats();
    }

    @PostMapping("/{user-id}/cats")
    @ResponseStatus(HttpStatus.CREATED)
    public Cat addCatsToUser(@RequestBody CatDto catDto, @PathVariable("user-id") Long userId) {
        User user = usersService.findById(userId);
        Cat cat = new Cat(catDto, user);
        catsService.save(cat);

        user.getCats().add(cat);
        usersService.save(user);
        return cat;
    }

    @PutMapping("/{user-id}/cats/{cat-id}")
    @ResponseStatus(HttpStatus.OK)
    public User updateCatsToUser(@RequestBody CatDto catDto, @PathVariable("user-id") Long userId, @PathVariable("cat-id") Long catId) {
        catsService.update(catDto, catId);
        return usersService.findById(userId);
    }

    @DeleteMapping("/{user-id}/cats/{cat-id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCatsFromUser(@PathVariable("user-id") Long userId, @PathVariable("cat-id") Long catId) {
        User user = usersService.findById(userId);
        Cat cat = catsService.findById(catId);
        user.getCats().remove(cat);
        feedbackService.deleteById(catId);
        usersService.save(user);
    }

    //////////////////////////////

    @GetMapping("/{user-id}/favourites")
    @ResponseStatus(HttpStatus.OK)
    public Collection<Cat> getFavouritesCatsByUser(@PathVariable("user-id") Long id) {
        return usersService.findById(id).getFavorites();
    }

    @PostMapping("/{user-id}/favourites")
    @ResponseStatus(HttpStatus.CREATED)
    public Cat addFavouritesCatByIdToUser(@RequestBody IdRequest idRequest, @PathVariable("user-id") Long userId) {
        User user = usersService.findById(userId);
        Cat cat = catsService.findById(idRequest.getId());

        user.getFavorites().add(cat);
        usersService.save(user);
        return cat;
    }

    @PutMapping("/{user-id}/favourites/{cat-id}")
    @ResponseStatus(HttpStatus.OK)
    public User updateFavouritesCatToUser(@RequestBody CatDto catDto, @PathVariable("user-id") Long userId, @PathVariable("cat-id") Long catId) {
        catsService.update(catDto, catId);
        return usersService.findById(userId);
    }

    @DeleteMapping("/{user-id}/favourites/{cat-id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteFavouritesCatFromUser(@PathVariable("user-id") Long userId, @PathVariable("cat-id") Long catId) {
        User user = usersService.findById(userId);
        Cat cat = catsService.findById(catId);
        user.getFavorites().remove(cat);
        feedbackService.deleteById(catId);
        usersService.save(user);
    }
}
