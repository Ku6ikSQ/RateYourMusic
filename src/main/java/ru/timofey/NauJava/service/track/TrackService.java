package ru.timofey.NauJava.service.track;

import ru.timofey.NauJava.entity.Track;

import java.util.List;

public interface TrackService {
    void createTrack(String title, int duration);
    Track findById(Long id);
    void deleteById(Long id);
    void renameTrack(Long id, String newTitle);
    String getTrackAuthor(Long trackId);
    List<Track> findAll();
}