package ru.madrabit.restwebsevices.post;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.madrabit.restwebsevices.user.User;
import ru.madrabit.restwebsevices.user.UserRepository;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
public class PostController {

    private final PostDaoService service;
    private final UserRepository userRepository;
    private final PostRepository postRepository;

    public PostController(PostDaoService service, UserRepository userRepository, PostRepository postRepository) {
        this.service = service;
        this.userRepository = userRepository;
        this.postRepository = postRepository;
    }

    @GetMapping("/jpa/users/{id}/posts/")
    public List<Post> retrieveAllUserPosts(@PathVariable int id) {
        final Optional<User> user = userRepository.findById(id);
        return user.get().getPosts();
    }

    @GetMapping("/users/{userId}/posts/{postId}")
    public Post retrievePostDetails(@PathVariable int userId, @PathVariable int postId) {
        return service.findDetailsOfPost(userId, postId);
    }

    @PostMapping("/jpa/users/{userId}/posts/")
    public ResponseEntity<Object> save(@PathVariable int userId, @RequestBody Post post) {
        final User user = userRepository.findById(userId).get();
        post.setUser(user);
        postRepository.save(post);

        final Post newPost = service.save(userId, post);
        final URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(newPost.getId()).toUri();
        return ResponseEntity.created(location).build();
    }
}
