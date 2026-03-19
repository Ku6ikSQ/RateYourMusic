package ru.timofey.NauJava.repository;

import org.springframework.stereotype.Repository;
import ru.timofey.NauJava.entities.Track;

import java.util.List;

@Repository
public class TrackRepository implements CrudRepository<Track, Long> {
    private final List<Track> tracks;

    public TrackRepository(List<Track> tracks) {
        this.tracks = tracks;
    }

    @Override
    public void create(Track track) {
        tracks.add(track);
    }

    @Override
    public Track read(Long id) {
        return tracks
                .stream()
                .filter(t -> t.getId().equals(id))
                .findFirst().orElse(null);
    }

    @Override
    public void update(Track track) {
        for (int i = 0; i < tracks.size(); i++) {
            if (tracks.get(i).getId().equals(track.getId())) {
                tracks.set(i, track);
                return;
            }
        }
    }

    @Override
    public void delete(Long id) {
        tracks.removeIf(track -> track.getId().equals(id));
    }
}
