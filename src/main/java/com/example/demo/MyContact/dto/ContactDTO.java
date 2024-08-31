package com.example.demo.MyContact.dto;

import com.example.demo.MyContact.entity.Phone;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor

public class ContactDTO {
    private String name;
    private String fullname;
    private String email;
    private List<Phone> phones;

}