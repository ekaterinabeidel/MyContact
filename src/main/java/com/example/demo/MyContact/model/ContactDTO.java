package com.example.demo.MyContact.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor

public class ContactDTO {
    private String name;
    private String fullname;
    private String email;
    private String phone;
    public ContactDTO(Contact contact) {
        this.name = contact.getName();
        this.fullname = contact.getFullname();
        this.email = contact.getEmail();
        this.phone = contact.getPhone();
    }
}
