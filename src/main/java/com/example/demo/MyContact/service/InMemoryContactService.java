package com.example.demo.MyContact.service;

import com.example.demo.MyContact.model.Contact;
import com.example.demo.MyContact.model.ContactDTO;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class InMemoryContactService implements ContactService {

    private final Map<Long, Contact> contacts = new HashMap<>();
    private long idCounter = 1;

    @Override
    public List<Contact> getAllContacts() {
        return new ArrayList<>(contacts.values());
    }

    @Override
    public Optional<Contact> getContactById(Long id) {
        return Optional.ofNullable(contacts.get(id));
    }

    @Override
    public Contact saveContact(Contact contact) {
        contact.setId(idCounter++);
        contacts.put(contact.getId(), contact);
        return contact;
    }

    @Override
    public Contact updateContact(Long id, ContactDTO updatedContact) {
        Contact contact = contacts.get(id);
        if (contact != null) {
            contact.setName(updatedContact.getName());
            contact.setEmail(updatedContact.getEmail());
            contact.setPhone(updatedContact.getPhone());
            return contact;
        } else {
            throw new RuntimeException("Contact not found");
        }
    }

    @Override
    public void deleteContact(Long id) {
        contacts.remove(id);
    }
}
