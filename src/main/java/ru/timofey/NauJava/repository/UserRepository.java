package ru.timofey.NauJava.repository;

import org.springframework.data.repository.CrudRepository;
import ru.timofey.NauJava.entity.User;

public interface UserRepository extends CrudRepository<User, Long> {
}
