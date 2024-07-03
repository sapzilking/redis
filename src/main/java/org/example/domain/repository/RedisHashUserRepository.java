package org.example.domain.repository;

import org.example.domain.entity.RedisHashUser;
import org.springframework.data.repository.CrudRepository;

public interface RedisHashUserRepository extends CrudRepository<RedisHashUser, Long> {
}
