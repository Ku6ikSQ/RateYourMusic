package ru.timofey.NauJava.repository;

import org.springframework.data.repository.CrudRepository;
import ru.timofey.NauJava.entities.Review;

public interface ReviewRepository extends CrudRepository<Review, Long> {
}
