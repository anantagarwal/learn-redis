package io.anant.learnredis.controller;

import io.anant.learnredis.model.User;
import io.anant.learnredis.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@RestController
@AllArgsConstructor
@RequestMapping("/api/users")
public class UserController {

    UserRepository userRepository;

    @GetMapping
    public Iterable<User> all(@RequestParam(defaultValue = "") String email) {
        if (email.isEmpty()) {
            return userRepository.findAll();
        } else {
            Optional<User> user = Optional.ofNullable(userRepository.findFirstByEmail(email));
            return user.isPresent() ? List.of(user.get()) : Collections.emptyList();
        }
    }

}
