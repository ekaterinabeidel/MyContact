package com.example.demo.MyContact.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor


public class Contact {


    private Long id;
    private String name;
    private String fullname;
    private String email;
    private String phone;


    public Contact() {

    }
}
