package com.example.demo.MyContact.service;

import com.example.demo.MyContact.model.User;
import com.example.demo.MyContact.repository.DataBaseUserRepository;
import com.example.demo.MyContact.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.Base64;
import java.util.Optional;

@Service
public class AuthService {
    private final UserRepository userRepository;

    @Autowired
    public AuthService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void registerUser(String email, String password, String name) throws SQLException {
        User user = new User();
        user.setEmail(email);
        user.setPassword(hashPassword(password));
        user.setRole("ROLE_ADMIN");
        user.setName(name);
        userRepository.save(user);
    }

    public String authenticate(String email, String password) throws SQLException {
        Optional<User> optionalUser = userRepository.findByEmail(email);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            if (user.getPassword().equals(hashPassword(password))) {
                return generateToken(email, password);
            }
        }
        return null;
    }

    private String hashPassword(String password) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(password.getBytes());
            return Base64.getEncoder().encodeToString(hash);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Error hashing password", e);
        }
    }

    private String generateToken(String email, String password) {
        return "001" + Base64.getEncoder().encodeToString((email + "|" + password).getBytes());
    }

    public boolean validateToken(String token) {
        try {
            String decodedToken = new String(Base64.getDecoder().decode(token.substring(3)));
            String[] parts = decodedToken.split("\\|");
            if (parts.length != 2) {
                return false;
            }
            String email = parts[0];
            String password = parts[1];

            Optional<User> optionalUser = userRepository.findByEmail(email);
            if (optionalUser.isPresent()) {
                User user = optionalUser.get();
                return user.getPassword().equals(hashPassword(password));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
