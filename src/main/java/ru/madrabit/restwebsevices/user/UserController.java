package ru.madrabit.restwebsevices.user;

import org.springframework.web.bind.annotation.*;

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
        return service.findById(id);
    }

    @PostMapping("/users/")
    public void saveUser(@RequestBody User user) {
        final User save = service.save(user);
    }
}

