package com.example.demo.MyContact.mapper;

import com.example.demo.MyContact.dto.CreateContactDTO;
import com.example.demo.MyContact.dto.ResponseContactDTO;
import com.example.demo.MyContact.dto.UpdateContactDTO;
import com.example.demo.MyContact.entity.Contact;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ContactMapper {
    Contact toEntity(CreateContactDTO createDTO);

    void updateEntity(UpdateContactDTO updateDTO, @MappingTarget Contact contact);

    ResponseContactDTO toResponseDTO(Contact contact);
    List<Contact> toEntityList(List<CreateContactDTO> createDTOList);

    List<ResponseContactDTO> toResponseDTOList(List<Contact> contactList);
}
