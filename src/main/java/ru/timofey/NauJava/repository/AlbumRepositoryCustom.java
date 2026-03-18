package ru.timofey.NauJava.repository;

import ru.timofey.NauJava.entity.Album;
import java.util.List;

public interface AlbumRepositoryCustom {
    List<Album> findByGenreNameAndReleaseYearBetween(String genreName, Integer startYear, Integer endYear);
}