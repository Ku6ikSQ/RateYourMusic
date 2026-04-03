package ru.timofey.NauJava.service.album;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import ru.timofey.NauJava.entity.Album;
import ru.timofey.NauJava.entity.Track;
import ru.timofey.NauJava.repository.AlbumRepository;
import ru.timofey.NauJava.repository.TrackRepository;

import java.util.List;

@Service
public class AlbumServiceImpl implements AlbumService {
    private final AlbumRepository albumRepository;
    private final TrackRepository trackRepository;
    private final PlatformTransactionManager transactionManager;

    @Autowired
    public AlbumServiceImpl(AlbumRepository albumRepository,
                            TrackRepository trackRepository,
                            PlatformTransactionManager transactionManager) {
        this.albumRepository = albumRepository;
        this.trackRepository = trackRepository;
        this.transactionManager = transactionManager;
    }

    @Override
    public void createAlbumWithTracks(Album album, List<Track> tracks) {
        var status = transactionManager.getTransaction(new DefaultTransactionDefinition());
        try {
            var savedAlbum = albumRepository.save(album);
            for(var track : tracks) {
                track.setAlbum(savedAlbum);
                trackRepository.save(track);
            }
            transactionManager.commit(status);
        } catch (Exception e) {
            transactionManager.rollback(status);
            throw e;
        }
    }
}
