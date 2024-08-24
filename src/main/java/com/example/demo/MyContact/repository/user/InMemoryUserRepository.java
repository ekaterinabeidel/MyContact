package com.example.demo.MyContact.repository.user;

import com.example.demo.MyContact.model.user.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class InMemoryUserRepository implements UserRepository {
    private final List<User> users = new ArrayList<>();
    private long userIdCounter = 1;

    @Override
    public Optional<User> findByEmail(String email) {
        return users.stream()
                .filter(user -> user.getEmail().equals(email))
                .findFirst();
    }

    @Override
    public Optional<User> findByEmailAndPassword(String email, String password) {
        return users.stream()
                .filter(user -> user.getEmail().equals(email) && user.getPassword().equals(password))
                .findFirst();
    }

    @Override
    public void save(User user) {
        user.setId(userIdCounter++);
        users.add(user);
    }
}
