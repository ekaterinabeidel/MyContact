package com.example.demo.MyContact.model;

import lombok.Data;

@Data
public class User {
    private Long id;
    private String email;
    private String password;
    private String role;
    private String name;
}
