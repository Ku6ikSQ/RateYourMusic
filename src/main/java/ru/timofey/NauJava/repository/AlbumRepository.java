package ru.timofey.NauJava.repository;

import org.springframework.data.repository.CrudRepository;
import ru.timofey.NauJava.entities.Album;

import java.util.List;

public interface AlbumRepository extends CrudRepository<Album, Long> {
    List<Album> findByGenre_IdAndReleaseYearBetween(Long genreId, Integer startYear, Integer endYear);
}
