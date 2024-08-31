package com.example.demo.MyContact.service.contact;

import com.example.demo.MyContact.dto.ContactDTO;
import com.example.demo.MyContact.entity.Contact;
import com.example.demo.MyContact.entity.Phone;
import com.example.demo.MyContact.repository.contact.ContactRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class DatabaseContactService implements ContactService {

    @Autowired
    private ContactRepository contactRepository;

    @Override
    public List<Contact> getAllContacts() {
        return contactRepository.findAll();
    }

    @Override
    public Optional<Contact> getContactById(Long id) {
        return contactRepository.findById(id);
    }

    @Override
    public Contact createContact(Long ownerId, ContactDTO contactDTO) {
        if (contactDTO == null) {
            throw new IllegalArgumentException("ContactDTO cannot be null");
        }

        Contact contact = new Contact();
        contact.setName(contactDTO.getName());
        contact.setFullname(contactDTO.getFullname());
        contact.setEmail(contactDTO.getEmail());
        contact.setPhones(contactDTO.getPhones());
        contact.setOwnerId(ownerId);
        if (contactDTO.getPhones() != null) {
            List<Phone> phones = contactDTO.getPhones().stream()
                    .peek(phone -> phone.setContact(contact))
                    .collect(Collectors.toList());
            contact.setPhones(phones);
        } else {
            contact.setPhones(new ArrayList<>());
        }

        return contactRepository.createContact(contact);
    }

    @Override
    public Contact updateContact(Long id, ContactDTO updatedContact) {
        if (id == null || updatedContact == null) {
            throw new IllegalArgumentException("ID and ContactDTO cannot be null");
        }

        Contact existingContact = contactRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Contact with id " + id + " does not exist."));

        existingContact.setName(updatedContact.getName());
        existingContact.setFullname(updatedContact.getFullname());
        existingContact.setEmail(updatedContact.getEmail());
        existingContact.setPhones(updatedContact.getPhones());

        return contactRepository.updateContact(id, updatedContact);
    }

    @Override
    public boolean deleteContact(Long id) {
        int rowsAffected = contactRepository.deleteById(id);
        return rowsAffected > 0;
    }

    @Override
    public List<Contact> getContactsByUserId(Long ownerId) {
        return contactRepository.findByUserId(ownerId);
    }
}
