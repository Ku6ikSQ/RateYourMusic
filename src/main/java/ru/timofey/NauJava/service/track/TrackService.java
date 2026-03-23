package ru.timofey.NauJava.service.track;

import ru.timofey.NauJava.entity.Track;

public interface TrackService {
    void createTrack(String title, int durationSeconds);
    Track findById(Long id);
    void deleteById(Long id);
    void renameTrack(Long id, String newTitle);
    String getTrackAuthor(Long trackId);
}