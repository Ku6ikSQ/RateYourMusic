package ru.timofey.NauJava.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.timofey.NauJava.entity.Album;
import ru.timofey.NauJava.repository.AlbumRepository;

import java.util.List;

@RestController
@RequestMapping("/api/albums/custom")
public class AlbumCustomController {

    private final AlbumRepository albumRepository;

    @Autowired
    public AlbumCustomController(AlbumRepository albumRepository) {
        this.albumRepository = albumRepository;
    }

    @GetMapping
    public List<Album> getByGenreAndYear(
            @RequestParam Long genreId,
            @RequestParam Integer startYear,
            @RequestParam Integer endYear
    ) {
        return albumRepository.findByGenre_IdAndReleaseYearBetween(
                genreId, startYear, endYear
        );
    }
}