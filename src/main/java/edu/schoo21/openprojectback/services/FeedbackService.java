package edu.schoo21.openprojectback.services;

import edu.schoo21.openprojectback.exceptions.NotFoundException;
import edu.schoo21.openprojectback.models.Feedback;
import edu.schoo21.openprojectback.models.dto.FeedbackDto;
import edu.schoo21.openprojectback.repository.FeedbackRepository;
import edu.schoo21.openprojectback.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class FeedbackService {
    private final FeedbackRepository feedbackRepository;
    private final UserRepository userRepository;

    public FeedbackService(FeedbackRepository feedbackRepository, UserRepository userRepository) {
        this.feedbackRepository = feedbackRepository;
        this.userRepository = userRepository;
    }

    public void save(Feedback feedback) {
        feedbackRepository.saveAndFlush(feedback);
    }

    public Feedback findById(Long feedbackId) {
        return feedbackRepository.findById(feedbackId).orElseThrow(NotFoundException::new);
    }

    public void deleteById(Long id) {
        feedbackRepository.deleteById(id);
    }

    public Feedback update(FeedbackDto feedbackDto, String id) {
        Feedback feedback = feedbackRepository.findById(Long.valueOf(id)).orElseThrow(NotFoundException::new);
        Long userId = feedbackDto.getUserId();
        userRepository.findById(userId).orElseThrow(NotFoundException::new);
        feedback.setUser_id(userId);
        feedback.setDate(feedbackDto.getDate());
        feedback.setText(feedbackDto.getText());
        return feedbackRepository.saveAndFlush(feedback);
    }

    public Feedback addNew(FeedbackDto feedbackDto) {
        Long userId = feedbackDto.getUserId();
        userRepository.findById(userId).orElseThrow(NotFoundException::new);
        Feedback feedback = new Feedback();
        feedback.setUser_id(userId);
        feedback.setDate(feedbackDto.getDate());
        feedback.setText(feedbackDto.getText());
        return feedbackRepository.saveAndFlush(feedback);
    }

    public Collection<Feedback> findAll() {
        return feedbackRepository.findAll();
    }
}
