package com.example.demo.MyContact.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ResponsePhoneDTO {
    private Long id;
    private String phoneNumber;
}
