package ru.madrabit.restwebsevices.user;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class UserDaoService {

    private static AtomicInteger userIds = new AtomicInteger(3);

    private static List<User> users = new ArrayList<>();

    static {
        users.add(new User(1, "Ivan", new Date()));
        users.add(new User(2, "Andrew", new Date()));
        users.add(new User(3, "Jhon", new Date()));
    }

    public List<User> findAll() {
        return users;
    }

    public User findById(int id) {
        for (User user : users) {
            if (user.getId() == id) {
                return user;
            }
        }
        return null;
    }

    public User save(User user) {
        if (user.getId() == 0) {
            user.setId(userIds.incrementAndGet());
        }
        users.add(user);
        return user;
    }

    public User delete(int id) {
        final Iterator<User> iterator = users.iterator();
        while (iterator.hasNext()) {
            User user = iterator.next();
            if (user.getId() == id) {
                iterator.remove();
                return user;
            }
        }
        return null;
    }
}
