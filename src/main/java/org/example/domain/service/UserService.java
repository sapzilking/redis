package org.example.domain.service;

import lombok.RequiredArgsConstructor;
import org.example.domain.entity.RedisHashUser;
import org.example.domain.entity.User;
import org.example.domain.repository.RedisHashUserRepository;
import org.example.domain.repository.UserRepository;
import org.hibernate.annotations.Cache;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;

import static org.example.domain.config.CacheConfig.CACHE1;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    private final RedisHashUserRepository redisHashUserRepository;
    private final RedisTemplate<String, User> userRedisTemplate;
    private final RedisTemplate<String, Object> objectRedisTemplate;

//    public User getUser(final Long id) {
//        // 1. cache get
//        var key = "users:%d".formatted(id);
//        var cachedUser = userRedisTemplate.opsForValue().get(key);
//        if (cachedUser != null) {
//            return cachedUser;
//        }
//        // 2. else db -> cache set
//        User user = userRepository.findById(id).orElseThrow();
//        userRedisTemplate.opsForValue().set(key, user, Duration.ofSeconds(30));
//
//        return user;
//    }

    public User getUser(final Long id) {
        // 1. cache get
        var key = "users:%d".formatted(id);
        var cachedUser = objectRedisTemplate.opsForValue().get(key);
        if (cachedUser != null) {
            return (User) cachedUser;
        }
        // 2. else db -> cache set
        User user = userRepository.findById(id).orElseThrow();
        objectRedisTemplate.opsForValue().set(key, user, Duration.ofSeconds(30));

        return user;
    }

    public RedisHashUser getUser2(final Long id) {
        // redis 값이 있으면 리턴 없으면 DB값 활용
        var cachedUser = redisHashUserRepository.findById(id).orElseGet(() -> {
            User user = userRepository.findById(id).orElseThrow();
            return redisHashUserRepository.save(RedisHashUser.builder()
                    .id(user.getId())
                    .name(user.getName())
                    .email(user.getEmail())
                    .createdAt(user.getCreatedAt())
                    .updatedAt(user.getUpdatedAt())
                    .build());
        });
        return cachedUser;
    }

    @Cacheable(cacheNames = CACHE1, key = "'user:' + #id")
    public User getUser3(final Long id) {
        return userRepository.findById(id).orElseThrow();
    }

}
