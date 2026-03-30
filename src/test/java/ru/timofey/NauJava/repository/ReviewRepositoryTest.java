package ru.timofey.NauJava.repository;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;
import ru.timofey.NauJava.entity.*;

import java.util.List;

@SpringBootTest
@ActiveProfiles("test")
class ReviewRepositoryTest {

    @Autowired
    private ReviewRepository reviewRepository;

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
        user.setUsername("john");
        user.setEmail("john@example.com");
        user.setPassword("pass");
        user.setRole(Role.USER);
        userRepository.save(user);

        Genre genre = new Genre();
        genre.setName("Pop");
        genreRepository.save(genre);

        Artist artist = new Artist();
        artist.setStageName("MJ");
        artistRepository.save(artist);

        Album album = new Album();
        album.setTitle("Thriller");
        album.setGenre(genre);
        album.setArtist(artist);
        album.setReleaseYear(1982);
        albumRepository.save(album);

        Review review = new Review();
        review.setUser(user);
        review.setAlbum(album);
        review.setScore(10);
        review.setReviewText("Awesome!");
        reviewRepository.save(review);

        List<Review> found = reviewRepository.findByUserUsername("john");
        Assertions.assertEquals(1, found.size());
        Assertions.assertEquals("Awesome!", found.getFirst().getReviewText());
    }
}