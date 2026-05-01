package ru.timofey.NauJava.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import ru.timofey.NauJava.entity.Artist;

@RepositoryRestResource
public interface ArtistRepository extends CrudRepository<Artist, Long> {
}