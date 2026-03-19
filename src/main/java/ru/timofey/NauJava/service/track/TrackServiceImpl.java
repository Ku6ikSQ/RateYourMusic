package ru.timofey.NauJava.service.track;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.timofey.NauJava.repository.TrackRepository;
import ru.timofey.NauJava.entity.Track;

import java.util.ArrayList;
import java.util.List;

@Service
public class TrackServiceImpl implements TrackService {

    private final TrackRepository trackRepository;

    @Autowired
    public TrackServiceImpl(TrackRepository trackRepository) {
        this.trackRepository = trackRepository;
    }

    @Transactional
    public void createTrack(String title, int duration) {
        Track newTrack = new Track();
        newTrack.setTitle(title);
        newTrack.setDurationSeconds(duration);

        trackRepository.save(newTrack);
    }

    @Override
    public Track findById(Long id) {
        return trackRepository.findById(id).orElse(null);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        trackRepository.deleteById(id);
    }

    @Override
    @Transactional
    public void renameTrack(Long id, String newTitle) {
        trackRepository.findById(id).ifPresent(track -> {
            track.setTitle(newTitle);
            trackRepository.save(track);
        });
    }

    @Override
    public String getTrackAuthor(Long trackId) {
        return trackRepository.findById(trackId)
                .map(track -> {
                    if (track.getAlbum() != null && track.getAlbum().getArtist() != null) {
                        return track.getAlbum().getArtist().getStageName();
                    }
                    return "unknown";
                })
                .orElse("not found");
    }

    @Override
    public List<Track> findAll() {
        List<Track> tracks = new ArrayList<>();
        trackRepository.findAll().forEach(tracks::add);
        return tracks;
    }

    public String getTrackAuthor(Long trackId) {
        Track track = trackRepository.findById(trackId).orElse(null);
        if (track == null || track.getAlbum() == null || track.getAlbum().getArtist() == null)
            return "unknown";
        return track.getAlbum().getArtist().getStageName();
    }
}