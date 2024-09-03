package com.example.demo.MyContact.dto;

import lombok.Data;

import java.util.List;

@Data

public class ResponseContactDTO {
    private Long id;
    private String name;
    private String fullname;
    private String email;
    private List<ResponsePhoneDTO> phones;
    private ResopnseUserDTO user;
}
