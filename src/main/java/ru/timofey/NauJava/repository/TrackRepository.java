package ru.timofey.NauJava.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import ru.timofey.NauJava.entity.Track;

@RepositoryRestResource
public interface TrackRepository extends CrudRepository<Track, Long>, PagingAndSortingRepository<Track, Long> {
}
