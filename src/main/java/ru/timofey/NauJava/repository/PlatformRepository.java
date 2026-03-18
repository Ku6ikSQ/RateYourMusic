package ru.timofey.NauJava.repository;

import org.springframework.data.repository.CrudRepository;
import ru.timofey.NauJava.entities.Platform;

public interface PlatformRepository extends CrudRepository<Platform, Long> {
}
