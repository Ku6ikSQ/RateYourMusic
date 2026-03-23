package ru.timofey.NauJava.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.timofey.NauJava.entity.Review;
import ru.timofey.NauJava.repository.ReviewRepository;

import java.util.List;

@RestController
@RequestMapping("/api/reviews/custom")
public class ReviewCustomController {
    private final ReviewRepository reviewRepository;

    @Autowired
    public ReviewCustomController(ReviewRepository reviewRepository) {
        this.reviewRepository = reviewRepository;
    }

    @GetMapping
    public List<Review> getByUsername(@RequestParam String username) {
        return reviewRepository.findByUserUsername(username);
    }
}