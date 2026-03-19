package ru.timofey.NauJava.repository.custom.review;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;
import ru.timofey.NauJava.entity.*;
import ru.timofey.NauJava.repository.*;

import java.util.List;

@SpringBootTest
@ActiveProfiles("test")
class ReviewRepositoryCustomTest {

    @Autowired
    private ReviewRepositoryCustom reviewRepositoryCustom;

    @Autowired
    private ReviewRepository reviewRepository; // для сохранения

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AlbumRepository albumRepository;

    @Autowired
    private ArtistRepository artistRepository;

    @Autowired
    private GenreRepository genreRepository;

    @BeforeEach
    void setUp() {
        reviewRepository.deleteAll();
        albumRepository.deleteAll();
        artistRepository.deleteAll();
        genreRepository.deleteAll();
    }

    @Test
    void testFindByUserUsername() {
        User user = new User();
        user.setUsername("alice");
        user.setEmail("alice@example.com");
        user.setPassword("pass");
        user.setRole(Role.USER);
        userRepository.save(user);

        Genre genre = new Genre();
        genre.setName("Electronic");
        genreRepository.save(genre);

        Artist artist = new Artist();
        artist.setStageName("Daft Punk");
        artistRepository.save(artist);

        Album album = new Album();
        album.setTitle("Discovery");
        album.setGenre(genre);
        album.setArtist(artist);
        album.setReleaseYear(2001);
        albumRepository.save(album);

        Review review = new Review();
        review.setUser(user);
        review.setAlbum(album);
        review.setScore(9);
        review.setReviewText("Great!");
        reviewRepository.save(review); // сохраняем через обычный репозиторий

        List<Review> found = reviewRepositoryCustom.findByUserUsername("alice");
        Assertions.assertEquals(1, found.size());
        Assertions.assertEquals("Great!", found.getFirst().getReviewText());
    }
}