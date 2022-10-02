package edu.schoo21.openprojectback.controllers;

import edu.schoo21.openprojectback.models.Cat;
import edu.schoo21.openprojectback.models.Feedback;
import edu.schoo21.openprojectback.models.User;
import edu.schoo21.openprojectback.models.dto.CatDto;
import edu.schoo21.openprojectback.models.dto.FeedbackDto;
import edu.schoo21.openprojectback.models.dto.UserDto;
import edu.schoo21.openprojectback.requests.IdRequest;
import edu.schoo21.openprojectback.requests.RankRequest;
import edu.schoo21.openprojectback.services.CatsService;
import edu.schoo21.openprojectback.services.FeedbackService;
import edu.schoo21.openprojectback.services.UsersService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@CrossOrigin
@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UsersController {

    private final UsersService usersService;
    private final FeedbackService feedbackService;
    private final CatsService catsService;

    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public Collection<User> getAllUsers() {
        return usersService.findAll();
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public User addNewUser(@RequestBody UserDto userDto) {
        return usersService.addNewUser(userDto);
    }

    @GetMapping("/{user-id}")
    @ResponseStatus(HttpStatus.OK)
    public User getUser(@PathVariable("user-id") String id) {
        return usersService.findById(Long.valueOf(id));
    }

    @GetMapping("/info")
    @ResponseStatus(HttpStatus.OK)
    public User getInfo(Authentication authentication) {
        if (authentication != null) {
            return usersService.findByLogin(authentication.getName());
        }
        return null;
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

    @PostMapping("/{user-id}")
    @ResponseStatus(HttpStatus.OK)
    public void addRanking(@RequestBody RankRequest rank, @PathVariable("user-id") Long id) {
        User user = usersService.findById(id);
        user.getRankings().add(rank.getRank());
        user.setRanking((float) user.getRankings().stream().mapToInt(r -> r).average().orElse(0));
        usersService.save(user);
    }

    /////////////////////////

    @GetMapping("/{user-id}/feedbacks")
    @ResponseStatus(HttpStatus.OK)
    public Collection<Feedback> getFeedbacksByUser(@PathVariable("user-id") String id) {
        return usersService.findById(Long.valueOf(id)).getFeedbacks();
    }

    @PostMapping("/{user-id}/feedbacks")
    @ResponseStatus(HttpStatus.CREATED)
    public Feedback addFeedbackToUser(@RequestBody FeedbackDto feedbackDto, @PathVariable("user-id") String userId) {
        Feedback feedback = new Feedback(Long.valueOf(feedbackDto.getUserId()), feedbackDto.getDate(), feedbackDto.getText());
        feedbackService.save(feedback);

        User user = usersService.findById(Long.valueOf(userId));
        user.getFeedbacks().add(feedback);
        usersService.save(user);
        return feedback;
    }

    @PutMapping("/{user-id}/feedbacks/{feedback-id}")
    @ResponseStatus(HttpStatus.OK)
    public User updateFeedbackToUser(@RequestBody FeedbackDto feedbackDto, @PathVariable("user-id") String userId, @PathVariable("feedback-id") String feedbackId) {
        Feedback feedback = feedbackService.findById(Long.valueOf(feedbackId));
        feedback.setUser_id(Long.valueOf(feedbackDto.getUserId()));
        feedback.setDate(feedbackDto.getDate());
        feedback.setText(feedbackDto.getText());
        feedbackService.save(feedback);
        return usersService.findById(Long.valueOf(userId));
    }

    @DeleteMapping("/{user-id}/feedbacks/{feedback-id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteFeedbacksFromUser(@PathVariable("user-id") String userId, @PathVariable("feedback-id") Long feedbackId) {
        User user = usersService.findById(Long.valueOf(userId));
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
    public User updateCatsToUser(@RequestBody CatDto catDto, @PathVariable("user-id") String userId, @PathVariable("cat-id") String catId) {
        catsService.update(catDto, catId);
        return usersService.findById(Long.valueOf(userId));
    }

    @DeleteMapping("/{user-id}/cats/{cat-id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCatsFromUser(@PathVariable("user-id") String userId, @PathVariable("cat-id") Long catId) {
        User user = usersService.findById(Long.valueOf(userId));
        Cat cat = catsService.findById(catId);
        user.getCats().remove(cat);
        feedbackService.deleteById(catId);
        usersService.save(user);
    }

    //////////////////////////////

    @GetMapping("/{user-id}/favourites")
    @ResponseStatus(HttpStatus.OK)
    public Collection<Cat> getFavouritesCatsByUser(@PathVariable("user-id") String id) {
        return usersService.findById(Long.valueOf(id)).getFavorites();
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
    public User updateFavouritesCatToUser(@RequestBody CatDto catDto, @PathVariable("user-id") String userId, @PathVariable("cat-id") String catId) {
        catsService.update(catDto, catId);
        return usersService.findById(Long.valueOf(userId));
    }

    @DeleteMapping("/{user-id}/favourites/{cat-id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteFavouritesCatFromUser(@PathVariable("user-id") String userId, @PathVariable("cat-id") Long catId) {
        User user = usersService.findById(Long.valueOf(userId));
        Cat cat = catsService.findById(catId);
        user.getFavorites().remove(cat);
        feedbackService.deleteById(catId);
        usersService.save(user);
    }
}
