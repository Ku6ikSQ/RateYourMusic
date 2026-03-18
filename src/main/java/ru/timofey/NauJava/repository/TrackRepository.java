package ru.timofey.NauJava.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.timofey.NauJava.entities.Track;

@Repository
public interface TrackRepository extends CrudRepository<Track, Long> {
}
