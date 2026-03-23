package ru.timofey.NauJava.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;
import ru.timofey.NauJava.entity.Track;

@RepositoryRestResource
@Repository
public interface TrackRepository extends CrudRepository<Track, Long> {
}
