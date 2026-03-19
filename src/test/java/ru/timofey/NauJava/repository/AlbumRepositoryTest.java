package ru.timofey.NauJava.repository;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.timofey.NauJava.entity.Album;
import ru.timofey.NauJava.entity.Artist;
import ru.timofey.NauJava.entity.Genre;

import java.util.List;

@SpringBootTest
class AlbumRepositoryTest {

    @Autowired
    private AlbumRepository albumRepository;

    @Autowired
    private GenreRepository genreRepository;

    @Autowired
    private ArtistRepository artistRepository;

    @Test
    void testFindByGenre_IdAndReleaseYearBetween() {
        Genre genre = new Genre();
        genre.setName("Rock");
        genreRepository.save(genre);

        Artist artist = new Artist();
        artist.setStageName("Queen");
        artistRepository.save(artist);

        Album a1 = new Album();
        a1.setTitle("Album1");
        a1.setGenre(genre);
        a1.setArtist(artist);
        a1.setReleaseYear(1980);
        albumRepository.save(a1);

        Album a2 = new Album();
        a2.setTitle("Album2");
        a2.setGenre(genre);
        a2.setArtist(artist);
        a2.setReleaseYear(1990);
        albumRepository.save(a2);

        List<Album> results = albumRepository.findByGenre_IdAndReleaseYearBetween(
                genre.getId(), 1975, 1985
        );

        Assertions.assertEquals(1, results.size());
        Assertions.assertEquals("Album1", results.getFirst().getTitle());
    }
}