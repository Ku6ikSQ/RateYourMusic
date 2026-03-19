package ru.timofey.NauJava.service;

import ru.timofey.NauJava.entities.Track;

public interface TrackService {
    void createTrack(String title, String genre, int duration);
    Track findById(Long id);
    void deleteById(Long id);
    void renameTrack(Long id, String newTitle);
}