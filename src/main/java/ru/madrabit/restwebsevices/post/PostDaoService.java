package ru.madrabit.restwebsevices.post;

import org.springframework.stereotype.Service;
import ru.madrabit.restwebsevices.user.User;
import ru.madrabit.restwebsevices.user.UserDaoService;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class PostDaoService {

    private static List<Post> posts = new ArrayList();

    private final UserDaoService userDaoService;

    private static AtomicInteger postIdCount = new AtomicInteger(3);

    public PostDaoService(UserDaoService userDaoService) {
        this.userDaoService = userDaoService;
        posts.add(new Post(1, "First post", "Some decs", userDaoService.findById(1)));
        posts.add(new Post(2, "Second post", "Some decs", userDaoService.findById(1)));
        posts.add(new Post(3, "First post", "Some decs", userDaoService.findById(2)));
    }



    public List<Post> findAllByUser(int id) {
        List<Post> list = new ArrayList<>();
        for (Post post : posts) {
           if (post.getUser().getId() == id) {
               list.add(post);
           }
        }
        return list;
    }

    public Post findDetailsOfPost(int userId, int postId) {
        final List<Post> posts = findAllByUser(userId);
        for (Post post : posts) {
            if (post.getId() == postId) {
                return post;
            }
        }
        return null;
    }

    public Post save(int id, Post post) {
        final User user = userDaoService.findById(id);
        posts.add(new Post(postIdCount.incrementAndGet(), post.getHeader(), post.getText(), user));
        return post;
    }
}
