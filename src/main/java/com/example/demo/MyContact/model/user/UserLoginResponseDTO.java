package com.example.demo.MyContact.model.user;

import lombok.Data;

@Data
public class UserLoginResponseDTO {
    private String email;
    private String token;
}
