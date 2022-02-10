package ru.madrabit.restwebsevices.user;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;


@RestController
public class UserController {

    private final UserDaoService service;

    public UserController(UserDaoService service) {
        this.service = service;
    }

    @GetMapping("/users")
    public List<User> retrieveAllUsers() {
        return service.findAll();
    }

    @GetMapping("/users/{id}")
    public User retrieveUser(@PathVariable int id) {
        final User user = service.findById(id);
        if (user == null) {
            throw new UserNotFoundException("User not found by id " + id);
        }
        return user;
    }

    @PostMapping("/users/")
    public ResponseEntity<Object> saveUser(@RequestBody User user) {
        final User savedUser = service.save(user);
        final URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedUser.getId()).toUri();
        return ResponseEntity.created(location).build();
    }
}

