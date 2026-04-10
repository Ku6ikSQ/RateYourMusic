package ru.timofey.NauJava.service.track;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.timofey.NauJava.entity.Album;
import ru.timofey.NauJava.entity.Artist;
import ru.timofey.NauJava.entity.Track;
import ru.timofey.NauJava.repository.TrackRepository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TrackServiceImplTest {
    @Mock
    private TrackRepository trackRepository;

    @InjectMocks
    private TrackServiceImpl trackService;

    private Track testTrack;

    @BeforeEach
    void setUp() {
        testTrack = new Track();
        testTrack.setId(1L);
        testTrack.setTitle("Test Title");
        testTrack.setDurationSeconds(180);
    }

    @Test
    void createTrack_ShouldSaveTrack_WhenDataIsValid() {
        // Arrange
        String title = "New Song";
        int duration = 200;

        // Act
        trackService.createTrack(title, duration);

        // Assert
        ArgumentCaptor<Track> trackCaptor = ArgumentCaptor.forClass(Track.class);
        verify(trackRepository).save(trackCaptor.capture());

        Track savedTrack = trackCaptor.getValue();
        assertEquals(title, savedTrack.getTitle());
        assertEquals(duration, savedTrack.getDurationSeconds());
    }

    // --- Тесты для findById ---

    @Test
    void findById_ShouldReturnTrack_WhenFound() {
        // Arrange
        when(trackRepository.findById(1L)).thenReturn(Optional.of(testTrack));

        // Act
        Track result = trackService.findById(1L);

        // Assert
        assertNotNull(result);
        assertEquals("Test Title", result.getTitle());
    }

    @Test
    void findById_ShouldReturnNull_WhenNotFound() {
        // Arrange
        when(trackRepository.findById(2L)).thenReturn(Optional.empty());

        // Act
        Track result = trackService.findById(2L);

        // Assert
        assertNull(result);
    }

    @Test
    void getTrackAuthor_ShouldReturnStageName_WhenTrackHasArtist() {
        // Arrange
        Artist artist = new Artist();
        artist.setStageName("Linkin Park");

        Album album = new Album();
        album.setArtist(artist);

        testTrack.setAlbum(album);

        when(trackRepository.findById(1L)).thenReturn(Optional.of(testTrack));

        // Act
        String author = trackService.getTrackAuthor(1L);

        // Assert
        assertEquals("Linkin Park", author);
    }

    @Test
    void getTrackAuthor_ShouldReturnUnknown_WhenAlbumOrArtistMissing() {
        // Arrange
        when(trackRepository.findById(1L)).thenReturn(Optional.of(testTrack)); // Album is null

        // Act
        String author = trackService.getTrackAuthor(1L);

        // Assert
        assertEquals("unknown", author);
    }

    @Test
    void getTrackAuthor_ShouldReturnNotFound_WhenTrackDoesNotExist() {
        // Arrange
        when(trackRepository.findById(99L)).thenReturn(Optional.empty());

        // Act
        String author = trackService.getTrackAuthor(99L);

        // Assert
        assertEquals("not found", author);
    }

    @Test
    void renameTrack_ShouldUpdateTitle_WhenTrackExists() {
        // Arrange
        String newTitle = "Updated Title";
        when(trackRepository.findById(1L)).thenReturn(Optional.of(testTrack));

        // Act
        trackService.renameTrack(1L, newTitle);

        // Assert
        assertEquals(newTitle, testTrack.getTitle());
        verify(trackRepository, times(1)).save(testTrack);
    }

    @Test
    void renameTrack_ShouldDoNothing_WhenTrackDoesNotExist() {
        // Arrange
        when(trackRepository.findById(1L)).thenReturn(Optional.empty());

        // Act
        trackService.renameTrack(1L, "New Title");

        // Assert
        verify(trackRepository, never()).save(any(Track.class));
    }

    @Test
    void createTrack_ShouldThrowException_WhenRepositoryFails() {
        // Arrange
        doThrow(new RuntimeException("DB Error")).when(trackRepository).save(any(Track.class));

        // Act & Assert
        assertThrows(RuntimeException.class, () -> {
            trackService.createTrack("Title", 100);
        });
    }
}