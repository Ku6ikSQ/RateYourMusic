package ru.timofey.NauJava.service.album;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import ru.timofey.NauJava.entity.Album;
import ru.timofey.NauJava.entity.Track;
import ru.timofey.NauJava.repository.AlbumRepository;
import ru.timofey.NauJava.repository.TrackRepository;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
@ActiveProfiles("test")
class AlbumServiceTest {

    @Autowired
    private AlbumService albumService;

    @Autowired
    private AlbumRepository albumRepository;

    @Autowired
    private TrackRepository trackRepository;

    @BeforeEach
    void setUp() {
        trackRepository.deleteAll();
        albumRepository.deleteAll();
    }

    @Test
    void testCreateAlbumWithTracks_Success() {
        // Given
        Album album = new Album();
        album.setTitle("Success Album");

        Track t1 = new Track();
        t1.setTitle("Track 1");

        List<Track> tracks = List.of(t1);

        // When
        albumService.createAlbumWithTracks(album, tracks);

        // Then
        Assertions.assertEquals(1, albumRepository.count(), "Альбом должен быть сохранен");
        Assertions.assertEquals(1, trackRepository.count(), "Трек должен быть сохранен");
    }

    @Test
    void testCreateAlbumWithTracks_Rollback() {
        // Given
        Album album = new Album();
        album.setTitle("Failure Album");

        List<Track> tracksWithNull = new ArrayList<>();
        tracksWithNull.add(new Track());
        tracksWithNull.add(null);

        // When & Then
        Assertions.assertThrows(Exception.class, () -> {
            albumService.createAlbumWithTracks(album, tracksWithNull);
        });

        // из-за rollback в базе не должно ничего остаться.
        Assertions.assertEquals(0, albumRepository.count(), "Альбом должен откатиться и не присутствовать в БД");
        Assertions.assertEquals(0, trackRepository.count(), "Треки не должны быть сохранены");
    }
}