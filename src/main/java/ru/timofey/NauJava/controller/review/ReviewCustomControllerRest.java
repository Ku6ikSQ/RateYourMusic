package ru.timofey.NauJava.controller.review;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.timofey.NauJava.entity.Review;
import ru.timofey.NauJava.exception.ResourceNotFoundException;
import ru.timofey.NauJava.repository.ReviewRepository;
import ru.timofey.NauJava.repository.UserRepository;

import java.util.List;

@RestController
@RequestMapping("/api/reviews/custom")
public class ReviewCustomControllerRest {
    private final ReviewRepository reviewRepository;
    private final UserRepository userRepository;

    @Autowired
    public ReviewCustomControllerRest(ReviewRepository reviewRepository, UserRepository userRepository) {
        this.reviewRepository = reviewRepository;
        this.userRepository = userRepository;
    }

    @GetMapping
    public List<Review> getByUsername(@RequestParam String username) {
        if (!userRepository.existsByUsername(username))
            throw new ResourceNotFoundException("User not found: " + username);
        return reviewRepository.findByUserUsername(username);
    }
}
