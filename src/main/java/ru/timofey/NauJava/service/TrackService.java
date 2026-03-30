package ru.timofey.NauJava.service;

import ru.timofey.NauJava.entities.Track;

import java.util.List;

public interface TrackService {
    void createTrack(String title, String author, int duration);
    Track findById(Long id);
    void deleteById(Long id);
    void renameTrack(Long id, String newTitle);
    List<Track> findAll();
}