package com.example.demo.MyContact.controller;

import com.example.demo.MyContact.model.UserLoginDTO;
import com.example.demo.MyContact.model.UserRegisterDTO;
import com.example.demo.MyContact.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody UserRegisterDTO userRegisterDTO) {
        try {
            if (userRegisterDTO.getEmail() == null || userRegisterDTO.getPassword() == null ||
                    userRegisterDTO.getName() == null) {
                return new ResponseEntity<>("Invalid input data", HttpStatus.BAD_REQUEST);
            }
            authService.registerUser(
                    userRegisterDTO.getEmail(), userRegisterDTO.getPassword(), userRegisterDTO.getName());
            return new ResponseEntity<>("User registered successfully", HttpStatus.CREATED);
        } catch (SQLException e) {
            return new ResponseEntity<>("Registration failed", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/login") // 200 OK: 001dXNlckBleGFtcGxlLmNvbXxwYXNzd29yZDEyMw==
    public ResponseEntity<String> login(@RequestBody UserLoginDTO userLoginDTO) {
        try {
            if (userLoginDTO.getEmail() == null || userLoginDTO.getPassword() == null) {
                return new ResponseEntity<>("Invalid input data", HttpStatus.BAD_REQUEST);
            }
            String token = authService.authenticate(userLoginDTO.getEmail(), userLoginDTO.getPassword());
            if (token != null) {
                return new ResponseEntity<>(token, HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Invalid credentials", HttpStatus.UNAUTHORIZED);
            }
        } catch (SQLException e) {
            return new ResponseEntity<>("Login failed", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
