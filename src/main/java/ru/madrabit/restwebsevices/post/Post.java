package ru.madrabit.restwebsevices.post;

import ru.madrabit.restwebsevices.user.User;

public class Post {

    private int id;
    private String header;
    private String text;
    private User user;

    public Post(int id, String header, String text, User user) {
        this.id = id;
        this.header = header;
        this.text = text;
        this.user = user;
    }

    public Post() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "Post{" +
                "id=" + id +
                ", header='" + header + '\'' +
                ", text='" + text + '\'' +
                ", user=" + user +
                '}';
    }
}
