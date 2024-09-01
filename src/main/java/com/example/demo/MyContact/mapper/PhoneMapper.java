package com.example.demo.MyContact.mapper;

import com.example.demo.MyContact.dto.ResponsePhoneDTO;
import com.example.demo.MyContact.entity.Phone;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PhoneMapper {

    ResponsePhoneDTO toResponsePhoneDTO (Phone phone);
}
