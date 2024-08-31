package com.example.demo.MyContact.repository.user;

import com.example.demo.MyContact.entity.User;

import java.util.Optional;


public interface UserRepository {
    void save(User user);
    Optional<User> findByEmailAndPassword(String email, String password);
    Optional<User> findByEmail(String email);
}
