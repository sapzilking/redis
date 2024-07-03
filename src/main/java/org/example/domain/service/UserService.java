package org.example.domain.service;

import lombok.RequiredArgsConstructor;
import org.example.domain.entity.User;
import org.example.domain.repository.UserRepository;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    private final RedisTemplate<String, User> userRedisTemplate;
    public User getUser(final Long id) {
        // 1. cache get
        var key = "users:%d".formatted(id);
        var cachedUser = userRedisTemplate.opsForValue().get(key);
        if (cachedUser != null) {
            return cachedUser;
        }
        // 2. else db -> cache set
        User user = userRepository.findById(id).orElseThrow();
        userRedisTemplate.opsForValue().set(key, user, Duration.ofSeconds(30));

        return user;
    }

}
