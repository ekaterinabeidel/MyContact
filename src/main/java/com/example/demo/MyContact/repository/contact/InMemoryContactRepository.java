package com.example.demo.MyContact.repository.contact;

import com.example.demo.MyContact.model.contact.Contact;
import com.example.demo.MyContact.model.contact.ContactDTO;


import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

public class InMemoryContactRepository implements ContactRepository {

    private final Map<Long, Contact> contacts = new HashMap<>();

    private final AtomicLong idCounter = new AtomicLong();

    @Override
    public List<Contact> findAll() {
        return new ArrayList<>(contacts.values());
    }

    @Override
    public Optional<Contact> findById(Long id) {
        return Optional.ofNullable(contacts.get(id));
    }

    @Override
    public Contact createContact(Contact contact) {
        if (contact == null) {
            throw new IllegalArgumentException("Contact cannot be null");
        }

        long id = idCounter.incrementAndGet();
        contact.setId(id);
        contacts.put(id, contact);
        return contact;
    }

    @Override
    public Contact updateContact(Long id, ContactDTO contactDTO) {
        if (id == null || contactDTO == null) {
            throw new IllegalArgumentException("ID and ContactDTO cannot be null");
        }

        Contact existingContact = contacts.get(id);
        if (existingContact == null) {
            throw new NoSuchElementException("Contact with id " + id + " does not exist.");
        }

        existingContact.setName(contactDTO.getName());
        existingContact.setFullname(contactDTO.getFullname());
        existingContact.setEmail(contactDTO.getEmail());
        existingContact.setPhones(contactDTO.getPhones());

        return existingContact;
    }

    @Override
    public Contact saveContact(Contact contact) {
        if (contact == null) {
            throw new IllegalArgumentException("Contact cannot be null");
        }

        Long id = contact.getId();
        if (id == null) {
            return createContact(contact);
        } else {
            if (contacts.containsKey(id)) {
                return updateContact(id, new ContactDTO(contact));
            } else {
                throw new NoSuchElementException("Contact does not exist.");
            }
        }
    }

    @Override
    public int deleteById(Long id) {
        if (contacts.containsKey(id)) {
            contacts.remove(id);
            return 1;
        } else {
            return 0;
        }
    }
}
