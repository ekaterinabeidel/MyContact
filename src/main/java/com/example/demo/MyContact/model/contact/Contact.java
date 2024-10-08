package com.example.demo.MyContact.model.contact;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor

public class Contact {

    private Long id;
    private String name;
    private String fullname;
    private String email;
    private List<String> phones;
    private Long ownerId;

    public Contact() {

    }

}
