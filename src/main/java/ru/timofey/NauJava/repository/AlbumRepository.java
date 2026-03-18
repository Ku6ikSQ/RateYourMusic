package ru.timofey.NauJava.repository;

import org.springframework.data.repository.CrudRepository;
import ru.timofey.NauJava.entities.Album;

public interface AlbumRepository extends CrudRepository<Album, Long> {
}
