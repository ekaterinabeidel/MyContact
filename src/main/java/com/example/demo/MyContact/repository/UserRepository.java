package com.example.demo.MyContact.repository;

import com.example.demo.MyContact.model.User;

import java.sql.SQLException;
import java.util.Optional;


public interface UserRepository {
    void save(User user) throws SQLException;
    Optional<User> findByEmailAndPassword(String email, String password);
    Optional<User> findByEmail(String email) throws SQLException;
}
