package ru.timofey.NauJava.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import ru.timofey.NauJava.entity.Review;

import java.util.List;

public interface ReviewRepository extends CrudRepository<Review, Long> {

    @Query("SELECT r FROM Review r WHERE r.user.username = :username")
    List<Review> findByUserUsername(String username);
}