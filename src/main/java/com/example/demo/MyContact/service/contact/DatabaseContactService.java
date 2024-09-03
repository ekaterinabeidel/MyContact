package com.example.demo.MyContact.service.contact;

import com.example.demo.MyContact.entity.Phone;
import com.example.demo.MyContact.entity.User;
import com.example.demo.MyContact.exception.UserNotFoundException;
import com.example.demo.MyContact.mapper.ContactMapper;
import com.example.demo.MyContact.dto.CreateContactDTO;
import com.example.demo.MyContact.dto.ResponseContactDTO;
import com.example.demo.MyContact.dto.UpdateContactDTO;
import com.example.demo.MyContact.entity.Contact;
import com.example.demo.MyContact.exception.ContactDTONullException;
import com.example.demo.MyContact.exception.ContactIdNullException;
import com.example.demo.MyContact.exception.OwnerIdNullException;
import com.example.demo.MyContact.repository.contact.ContactRepository;
import com.example.demo.MyContact.repository.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class DatabaseContactService implements ContactService {

    @Autowired
    private ContactRepository contactRepository;
    @Autowired
    private ContactMapper contactMapper;
    @Autowired
    private UserRepository userRepository;


    @Override
    public List<ResponseContactDTO> getAllContacts() {
        List<Contact> contacts = contactRepository.findAll();
        return contactMapper.toResponseDTOList(contacts);
    }

    @Override
    public ResponseContactDTO getContactById(Long id) {
        Contact contact = contactRepository.findById(id);
        return contactMapper.toResponseDTO(contact);
    }

    @Override
    public List<ResponseContactDTO> getContactsByUserId(Long ownerId) {
        List<Contact> contacts = contactRepository.findByUserId(ownerId);
        return contactMapper.toResponseDTOList(contacts);
    }

    @Override
    public ResponseContactDTO createContact(Long ownerId, CreateContactDTO createContactDTO) {
        if (ownerId == null) {
            throw new OwnerIdNullException("OwnerId cannot be null");
        }
        if (createContactDTO == null) {
            throw new ContactDTONullException("ContactDTO cannot be null");
        }
        Contact contact = contactMapper.toEntity(createContactDTO);
        User owner = userRepository.findById(ownerId)
                .orElseThrow(() -> new UserNotFoundException("User not found with id: " + ownerId));
        contact.setUser(owner);
        contact.getPhones().forEach(phone -> phone.setContact(contact));
        Contact createdContact = contactRepository.createContact(contact);
        return contactMapper.toResponseDTO(createdContact);
    }

    @Override
    public ResponseContactDTO updateContact(Long id, UpdateContactDTO updateContactDTO) {
        if (id == null) {
            throw new ContactIdNullException("ID cannot be null");
        }
        if (updateContactDTO == null) {
            throw new ContactDTONullException("ContactDTO cannot be null");
        }
        Contact existingContact = contactRepository.findById(id);
        contactMapper.updateEntity(updateContactDTO, existingContact);
        if (existingContact.getPhones() != null) {
            for (Phone phone : existingContact.getPhones()) {
                phone.setContact(existingContact);
            }
        }
        Contact updatedContact = contactRepository.updateContact(id, existingContact);
        return contactMapper.toResponseDTO(updatedContact);
    }

    @Override
    public void deleteContact(Long id) {
        contactRepository.deleteById(id);
    }


}
