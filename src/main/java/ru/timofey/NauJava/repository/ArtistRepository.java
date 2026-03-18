package ru.timofey.NauJava.repository;

import org.springframework.data.repository.CrudRepository;
import ru.timofey.NauJava.entity.Artist;

public interface ArtistRepository extends CrudRepository<Artist, Long> {
}