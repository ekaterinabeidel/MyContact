package com.example.demo.MyContact.repository;

import com.example.demo.MyContact.model.Contact;

import java.util.*;

public class InMemoryRepository implements ContactRepository {

    private final Map<Long, Contact> contacts = new HashMap<>();
    private long idCounter = 1;

    @Override
    public List<Contact> findAll() {
        return new ArrayList<>(contacts.values());
    }

    @Override
    public Optional<Contact> findById(Long id) {
        return Optional.ofNullable(contacts.get(id));
    }

    @Override
    public Contact save(Contact contact) {
        if (contact.getId() == null) {
            contact.setId(idCounter++);
        }
        contacts.put(contact.getId(), contact);
        return contact;
    }

    @Override
    public void deleteById(Long id) {
        contacts.remove(id);
    }

    public Contact update(Contact contact) {
        if (contact.getId() == null || !contacts.containsKey(contact.getId())) {
            throw new IllegalArgumentException("Contact with id " + contact.getId() + " does not exist.");
        }
        contacts.put(contact.getId(), contact);
        return contact;
    }
}
