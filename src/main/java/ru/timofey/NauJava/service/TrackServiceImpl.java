package ru.timofey.NauJava.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.timofey.NauJava.repository.TrackRepository;
import ru.timofey.NauJava.entities.Track;

@Service
public class TrackServiceImpl implements TrackService {

    private final TrackRepository trackRepository;
    private long nextId = 1; // счетчик для id

    @Autowired
    public TrackServiceImpl(TrackRepository trackRepository) {
        this.trackRepository = trackRepository;
    }

    @Override
    public void createTrack(String title, String author, int duration) {
        Track newTrack = new Track();
        newTrack.setId(nextId++); // авто-генерация id
        newTrack.setTitle(title);
        newTrack.setAuthor(author);
        newTrack.setDuration(duration);
        trackRepository.create(newTrack);
    }

    @Override
    public Track findById(Long id) {
        return trackRepository.read(id);
    }

    @Override
    public void deleteById(Long id) {
        trackRepository.delete(id);
    }

    @Override
    public void renameTrack(Long id, String newTitle) {
        Track existingTrack = trackRepository.read(id);
        if (existingTrack != null) {
            existingTrack.setTitle(newTitle);
            trackRepository.update(existingTrack);
        }
    }
}