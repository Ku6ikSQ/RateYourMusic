package ru.timofey.NauJava.repository;

import ru.timofey.NauJava.entity.Review;
import java.util.List;

public interface ReviewRepositoryCustom {
    List<Review> findByUserUsername(String username);
}
