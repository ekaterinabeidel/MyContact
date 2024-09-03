package com.example.demo.MyContact.repository.user;

import com.example.demo.MyContact.entity.User;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.FluentQuery;

import java.util.Optional;
import java.util.function.Function;


public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByEmail (String email);

    Optional<User> findByEmailAndPassword(String email, String password);

    Optional<User> findByEmail(String email);

}
