package ru.madrabit.restwebsevices.post;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
public class PostController {

    private final PostDaoService service;

    public PostController(PostDaoService service) {
        this.service = service;
    }

    @GetMapping("/users/{id}/posts/")
    public List<Post> retrieveAllUserPosts(@PathVariable int id) {
       return service.findAllByUser(id);
    }

    @GetMapping("/users/{userId}/posts/{postId}")
    public Post retrievePostDetails(@PathVariable int userId, @PathVariable int postId) {
        return service.findDetailsOfPost(userId, postId);
    }

    @PostMapping("/users/{userId}/posts/")
    public ResponseEntity<Object> retrievePostDetails(@PathVariable int userId, @RequestBody Post post) {
        final Post newPost = service.save(userId, post);
        final URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(newPost.getId()).toUri();
        return ResponseEntity.created(location).build();
    }
}
