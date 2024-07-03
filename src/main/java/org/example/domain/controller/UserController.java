package org.example.domain.controller;

import lombok.RequiredArgsConstructor;
import org.example.domain.entity.RedisHashUser;
import org.example.domain.entity.User;
import org.example.domain.service.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class UserController {
    private final UserService userService;

    @GetMapping("/users/{id}")
    public User getUser(@PathVariable Long id) {
        return userService.getUser(id);
    }

    @GetMapping("/redishash-users/{id}")
    public RedisHashUser getUser2(@PathVariable Long id) {
        return userService.getUser2(id);
    }

    @GetMapping("/users/{id}")
    public User getUser3(@PathVariable Long id) {
        return userService.getUser3(id);
    }

}
