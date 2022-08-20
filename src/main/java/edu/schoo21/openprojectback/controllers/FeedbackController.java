package edu.schoo21.openprojectback.controllers;

import edu.schoo21.openprojectback.models.Cat;
import edu.schoo21.openprojectback.models.Feedback;
import edu.schoo21.openprojectback.models.dto.FeedbackDto;
import edu.schoo21.openprojectback.services.FeedbackService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("/feedbacks")
public class FeedbackController {

    private final FeedbackService feedbackService;

    public FeedbackController(FeedbackService feedbackService) {
        this.feedbackService = feedbackService;
    }

    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public Collection<Feedback> getAllFeedbacks() {
        return feedbackService.findAll();
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public Feedback addNew(@RequestBody FeedbackDto feedbackDto) {
        return feedbackService.addNew(feedbackDto);
    }

    @GetMapping("/{feedback-id}")
    @ResponseStatus(HttpStatus.OK)
    public Feedback get(@PathVariable("feedback-id") String id) {
        return feedbackService.findById(Long.valueOf(id));
    }

    @PutMapping("/{feedback-id}")
    @ResponseStatus(HttpStatus.OK)
    public Feedback update(@RequestBody FeedbackDto feedbackDto, @PathVariable("feedback-id") String id) {
        return feedbackService.update(feedbackDto, id);
    }

    @DeleteMapping("/{feedback-id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable("feedback-id") String id) {
        feedbackService.deleteById(Long.valueOf(id));
    }
}
