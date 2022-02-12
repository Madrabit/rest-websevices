package ru.madrabit.restwebsevices.user;

import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.Collections;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;


@RestController
public class UserController {

    private final UserDaoService service;

    public UserController(UserDaoService service) {
        this.service = service;
    }

    @GetMapping("/users")
    public MappingJacksonValue retrieveAllUsers() {
        final SimpleBeanPropertyFilter.FilterExceptFilter filter = new SimpleBeanPropertyFilter
                .FilterExceptFilter(Collections.singleton("name"));
        FilterProvider filters = new SimpleFilterProvider()
                .addFilter("UserDTOFilter", filter);
        MappingJacksonValue mapping = new MappingJacksonValue(service.findAll());
        mapping.setFilters(filters);
        return mapping;
    }

    @GetMapping("/users/{id}")
    public EntityModel<User> retrieveUser(@PathVariable int id) {
        final User user = service.findById(id);
        if (user == null) {
            throw new UserNotFoundException("User not found by id " + id);
        }
        EntityModel<User> resource = EntityModel.of(user);

        WebMvcLinkBuilder linkTo =
                linkTo(methodOn(this.getClass()).retrieveAllUsers());

        resource.add(linkTo.withRel("all-users"));

        return resource;
    }

    @PostMapping("/users/")
    public ResponseEntity<Object> saveUser(@Valid @RequestBody User user) {
        final User savedUser = service.save(user);
        final URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedUser.getId()).toUri();
        return ResponseEntity.created(location).build();
    }

    @DeleteMapping("/users/{id}")
    public User deleteUser(@PathVariable int id) {
        final User user = service.delete(id);
        if (user == null) {
            throw new UserNotFoundException("User not found by id " + id);
        }
        return user;
    }
}

