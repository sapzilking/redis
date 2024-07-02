package org.example;

import lombok.RequiredArgsConstructor;
import org.example.cacheTest.User;
import org.example.cacheTest.UserRepository;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@RequiredArgsConstructor
public class Main implements ApplicationRunner {

    private final UserRepository userRepository;
    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
//        RedisString.StringTest();
//        ListAndSet.ListAndSetTest();
//        RedisHash.hashTest();
//        SortedSet.test();
//        RedisGeospatial.test();
//        RedisBitmap.test();

    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        userRepository.save(User.builder().name("Bob").email("bob@fast.co.kr").build());
        userRepository.save(User.builder().name("Tony").email("tony@fast.co.kr").build());
        userRepository.save(User.builder().name("Mike").email("mike@fast.co.kr").build());
        userRepository.save(User.builder().name("Ryan").email("ryan@fast.co.kr").build());
    }
}