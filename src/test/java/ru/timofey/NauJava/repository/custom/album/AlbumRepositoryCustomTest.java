package ru.timofey.NauJava.repository.custom.album;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;
import ru.timofey.NauJava.entity.Album;
import ru.timofey.NauJava.entity.Artist;
import ru.timofey.NauJava.entity.Genre;
import ru.timofey.NauJava.repository.AlbumRepository;
import ru.timofey.NauJava.repository.ArtistRepository;
import ru.timofey.NauJava.repository.GenreRepository;
import ru.timofey.NauJava.repository.ReviewRepository;

import java.util.List;

@SpringBootTest
@ActiveProfiles("test")
class AlbumRepositoryCustomTest {

    @Autowired
    private AlbumRepositoryCustom albumRepositoryCustom;

    @Autowired
    private GenreRepository genreRepository;

    @Autowired
    private ArtistRepository artistRepository;

    @Autowired
    private AlbumRepository albumRepository;

    @Autowired
    private ReviewRepository reviewRepository;

    @BeforeEach
    void setUp() {
        reviewRepository.deleteAll();
        albumRepository.deleteAll();
        artistRepository.deleteAll();
        genreRepository.deleteAll();
    }

    @Test
    void testFindByGenreNameAndReleaseYearBetween() {
        Genre genre = new Genre();
        genre.setName("Jazz");
        genreRepository.save(genre);

        Artist artist = new Artist();
        artist.setStageName("Miles");
        artistRepository.save(artist);

        Album a1 = new Album();
        a1.setTitle("Kind of Blue");
        a1.setGenre(genre);
        a1.setArtist(artist);
        a1.setReleaseYear(1959);
        albumRepository.save(a1);

        Album a2 = new Album();
        a2.setTitle("Bitches Brew");
        a2.setGenre(genre);
        a2.setArtist(artist);
        a2.setReleaseYear(1970);
        albumRepository.save(a2);

        List<Album> result = albumRepositoryCustom.findByGenreNameAndReleaseYearBetween(
                "Jazz", 1950, 1960
        );

        Assertions.assertEquals(1, result.size());
        Assertions.assertEquals("Kind of Blue", result.getFirst().getTitle());
    }
}