package ru.timofey.NauJava.service.track;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.timofey.NauJava.entity.Track;
import ru.timofey.NauJava.repository.TrackRepository;

import java.util.Optional;

@Service
public class TrackServiceImpl implements TrackService {

    private final TrackRepository trackRepository;

    @Autowired
    public TrackServiceImpl(TrackRepository trackRepository) {
        this.trackRepository = trackRepository;
    }

    @Override
    public void createTrack(String title, int durationSeconds) {
        Track newTrack = new Track();
        newTrack.setTitle(title);
        newTrack.setDurationSeconds(durationSeconds);
        trackRepository.save(newTrack);
    }

    @Override
    public Track findById(Long id) {
        Optional<Track> trackOpt = trackRepository.findById(id);
        return trackOpt.orElse(null);
    }

    @Override
    public void deleteById(Long id) {
        trackRepository.deleteById(id);
    }

    @Override
    public void renameTrack(Long id, String newTitle) {
        Optional<Track> trackOpt = trackRepository.findById(id);
        if (trackOpt.isPresent()) {
            Track track = trackOpt.get();
            track.setTitle(newTitle);
            trackRepository.save(track);
        }
    }
}