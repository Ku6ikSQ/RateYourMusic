package ru.timofey.NauJava.service.track;

import ru.timofey.NauJava.entity.Track;

public interface TrackService {
    void createTrack(String title, String genre, int duration);
    Track findById(Long id);
    void deleteById(Long id);
    void renameTrack(Long id, String newTitle);
}