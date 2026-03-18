package ru.timofey.NauJava.repository;

import org.springframework.data.repository.CrudRepository;
import ru.timofey.NauJava.entity.Platform;

public interface PlatformRepository extends CrudRepository<Platform, Long> {
}
