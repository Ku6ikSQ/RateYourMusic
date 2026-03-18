package ru.timofey.NauJava.repository;

import org.springframework.data.repository.CrudRepository;
import ru.timofey.NauJava.entity.Genre;

public interface GenreRepository extends CrudRepository<Genre, Long> {
}
