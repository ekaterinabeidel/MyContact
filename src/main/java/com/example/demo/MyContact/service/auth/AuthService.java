package com.example.demo.MyContact.service.auth;

import com.example.demo.MyContact.entity.User;
import com.example.demo.MyContact.exception.EmailAlreadyExistsException;
import com.example.demo.MyContact.repository.user.UserRepository;
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

    public void registerUser(String email, String password, String name, String role) throws SQLException {
        if(userRepository.existsByEmail(email)){
            throw new EmailAlreadyExistsException("User with email " + email + " already exists");
        }
        User user = new User();
        user.setEmail(email);
        user.setPassword(hashPassword(password));
        user.setRole("ADMIN");
        user.setName(name);
        user.setRole(role);
        userRepository.save(user);
    }

    public String authenticate(String email, String password) throws SQLException {
        Optional<User> optionalUser = userRepository.findByEmail(email);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            if (user.getPassword().equals(hashPassword(password))) {
                return generateToken(email, password, user.getRole());
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

    private String generateToken(String email, String password, String role) {
        return "001" + Base64.getEncoder().encodeToString((email + "|" + password+ "|" + role).getBytes());
    }

    public boolean validateToken(String token) {
        try {
            String decodedToken = new String(Base64.getDecoder().decode(token.substring(3)));
            String[] parts = decodedToken.split("\\|");
            if (parts.length != 3) {
                return false;
            }
            String email = parts[0];
            String password = parts[1];
            String role = parts[2];

            Optional<User> optionalUser = userRepository.findByEmail(email);
            if (optionalUser.isPresent()) {
                User user = optionalUser.get();
                return user.getPassword().equals(hashPassword(password))
                        && user.getRole().equals(role);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public Long getUserIdFromToken(String token) {
        String decodedToken = new String(Base64.getDecoder().decode(token.substring(3)));
        String email = decodedToken.split("\\|")[0];

        Optional<User> optionalUser = userRepository.findByEmail(email);
        return optionalUser.map(User::getId).orElse(null);
    }

    public String getRoleFromToken(String token){
        String decodedToken = new String(Base64.getDecoder().decode(token.substring(3)));
        String role = decodedToken.split("\\|")[2];
        if (!role.isEmpty()){
            return role;
        }
        return null;
    }
}
