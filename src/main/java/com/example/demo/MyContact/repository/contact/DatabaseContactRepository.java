package com.example.demo.MyContact.repository.contact;

import com.example.demo.MyContact.entity.Contact;

import com.example.demo.MyContact.exception.ContactNotFoundException;
import com.example.demo.MyContact.exception.OwnerNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class DatabaseContactRepository implements ContactRepository {

    private final ContactJpaRepository contactJpaRepository;

    @Autowired
    public DatabaseContactRepository(ContactJpaRepository contactJpaRepository) {
        this.contactJpaRepository = contactJpaRepository;
    }

    @Override
    public List<Contact> findAll() {
        return contactJpaRepository.findAll();
    }

    @Override
    public Contact findById(Long id) {
        return contactJpaRepository.findById(id).orElseThrow(
                () -> new ContactNotFoundException("Contact not found with id: " + id)
        );
    }

    @Override
    public Contact createContact(Contact contact) {
        return contactJpaRepository.save(contact);
    }

    @Override
    public Contact updateContact(Long id, Contact contact) {
        return contactJpaRepository.findById(id)
                .map(existingContact -> {
                    existingContact.setName(contact.getName());
                    existingContact.setEmail(contact.getEmail());
                    return contactJpaRepository.save(existingContact);
                })
                .orElseThrow(
                        () -> new ContactNotFoundException("Contact not found with id: " + id)
                );
    }

    @Override
    public void deleteById(Long id) {
        if (!contactJpaRepository.existsById(id)) {
            throw new ContactNotFoundException("Contact not found with id: " + id);
        }
        contactJpaRepository.deleteById(id);
    }

    @Override
    public List<Contact> findByUserId(Long ownerId) {
        List<Contact> contacts = contactJpaRepository.findByOwnerId(ownerId);
        if (contacts.isEmpty()) {
            throw new OwnerNotFoundException("Owner with id " + ownerId + " not found.");
        }
        return contacts;
    }
}
