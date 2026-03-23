package ru.timofey.NauJava.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import ru.timofey.NauJava.entity.Album;

import java.util.List;

@RepositoryRestResource
public interface AlbumRepository extends CrudRepository<Album, Long> {
    List<Album> findByGenre_IdAndReleaseYearBetween(Long genreId, Integer startYear, Integer endYear);
}
