package com.example.demo.MyContact.dto;

import lombok.Data;

@Data
public class UserRegisterDTO {
    private String email;
    private String password;
    private String name;
    private String role;
}
