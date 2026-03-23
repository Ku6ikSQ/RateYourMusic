package ru.timofey.NauJava.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import ru.timofey.NauJava.entity.Review;

import java.util.List;

@RepositoryRestResource
public interface ReviewRepository extends CrudRepository<Review, Long> {

    @Query("SELECT r FROM Review r WHERE r.user.username = :username")
    List<Review> findByUserUsername(String username);
}