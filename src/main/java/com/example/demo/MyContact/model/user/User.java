package com.example.demo.MyContact.model.user;

import com.example.demo.MyContact.model.contact.Contact;
import lombok.Data;

import java.util.List;

@Data
public class User {
    private Long id;
    private String email;
    private String password;
    private String role;
    private String name;
    private List<Contact> contacts;

}
