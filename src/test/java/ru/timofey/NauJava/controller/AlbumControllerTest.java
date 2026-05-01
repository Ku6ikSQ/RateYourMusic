package ru.timofey.NauJava.controller;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;
import ru.timofey.NauJava.entity.Album;
import ru.timofey.NauJava.entity.Genre;
import ru.timofey.NauJava.repository.AlbumRepository;
import ru.timofey.NauJava.repository.GenreRepository;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
class AlbumCustomControllerRestTest {

    @LocalServerPort
    private int port;

    @Autowired
    private AlbumRepository albumRepository;

    @Autowired
    private GenreRepository genreRepository;

    private Long savedGenreId;
    private RequestSpecification spec;

    private static final String ENDPOINT = "/api/albums/custom";

    @BeforeEach
    void setUp() {
        albumRepository.deleteAll();
        genreRepository.deleteAll();

        Genre genre = new Genre();
        genre.setName("Rock");
        Genre savedGenre = genreRepository.save(genre);

        assertNotNull(savedGenre.getId(), "База данных не вернула ID для Genre.");
        savedGenreId = savedGenre.getId();

        Album album = new Album();
        album.setTitle("Test Integration Album");
        album.setReleaseYear(2022);
        album.setGenre(savedGenre);
        albumRepository.save(album);

        spec = new RequestSpecBuilder()
                .setBaseUri("http://localhost")
                .setPort(port)
                .setContentType(ContentType.JSON)
                .log(io.restassured.filter.log.LogDetail.ALL)
                .build();
    }

    @Test
    void getByGenreAndYear_ShouldReturn200_WhenAlbumsExist() {
        given(spec)
                .queryParam("genreId", savedGenreId)
                .queryParam("startYear", 2000)
                .queryParam("endYear", 2026)
                .when()
                .get(ENDPOINT)
                .then()
                .log().ifValidationFails()
                .statusCode(200)
                .body("$", not(empty()));
    }

    @Test
    void getByGenreAndYear_ShouldReturn404_WhenNoAlbumsFound() {
        given(spec)
                .queryParam("genreId", savedGenreId)
                .queryParam("startYear", 1900)
                .queryParam("endYear", 1901)
                .when()
                .get(ENDPOINT)
                .then()
                .log().ifValidationFails()
                .statusCode(404);
    }

    @Test
    void getByGenreAndYear_ShouldReturn400_WhenParametersMissing() {
        given(spec)
                .queryParam("genreId", savedGenreId)
                // startYear и endYear not passed
                .when()
                .get(ENDPOINT)
                .then()
                .log().ifValidationFails()
                .statusCode(400);
    }

    @Test
    void getByGenreAndYear_ShouldReturn400_WhenInvalidParamType() {
        given(spec)
                .queryParam("genreId", "not-a-number")
                .queryParam("startYear", 2000)
                .queryParam("endYear", 2026)
                .when()
                .get(ENDPOINT)
                .then()
                .log().ifValidationFails()
                .statusCode(400);
    }
}
