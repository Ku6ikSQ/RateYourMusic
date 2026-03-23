package ru.timofey.NauJava.service.album;

import ru.timofey.NauJava.entity.Album;
import ru.timofey.NauJava.entity.Track;

import java.util.List;

public interface AlbumService {
    void createAlbumWithTracks(Album album, List<Track> tracks);
}