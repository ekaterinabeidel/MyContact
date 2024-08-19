package com.example.demo.MyContact.service;

import com.example.demo.MyContact.model.Contact;
import com.example.demo.MyContact.model.ContactDTO;
import com.example.demo.MyContact.repository.ContactRepository;

import java.util.List;
import java.util.Optional;

public class DatabaseContactService implements ContactService {

    private final ContactRepository contactRepository;

    public DatabaseContactService(ContactRepository contactRepository) {
        this.contactRepository = contactRepository;
    }

    @Override
    public List<Contact> getAllContacts() {
        return contactRepository.findAll();
    }

    @Override
    public Optional<Contact> getContactById(Long id) {
        return contactRepository.findById(id);
    }

    @Override
    public Contact saveContact(Contact contact) {
        return contactRepository.save(contact);
    }

    @Override
    public Contact updateContact(Long id, ContactDTO updatedContact) {
        return contactRepository.findById(id).map(contact -> {
            contact.setName(updatedContact.getName());
            contact.setEmail(updatedContact.getEmail());
            contact.setPhone(updatedContact.getPhone());
            return contactRepository.save(contact);
        }).orElseThrow(() -> new RuntimeException("Contact not found"));
    }

    @Override
    public void deleteContact(Long id) {
        contactRepository.deleteById(id);
    }
}
