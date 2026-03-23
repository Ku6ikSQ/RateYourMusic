package ru.timofey.NauJava.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import ru.timofey.NauJava.entity.Platform;

@RepositoryRestResource
public interface PlatformRepository extends CrudRepository<Platform, Long> {
}
