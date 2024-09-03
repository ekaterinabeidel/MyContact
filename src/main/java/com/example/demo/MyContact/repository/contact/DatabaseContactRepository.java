package com.example.demo.MyContact.repository.contact;

import com.example.demo.MyContact.entity.Contact;

import com.example.demo.MyContact.exception.ContactNotFoundException;
import com.example.demo.MyContact.exception.OwnerNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class DatabaseContactRepository implements ContactRepository {
    @Autowired
    private final JpaContactsRepository jpaContactsRepository;

    public DatabaseContactRepository(JpaContactsRepository jpaContactsRepository) {
        this.jpaContactsRepository = jpaContactsRepository;
    }

    @Override
    public List<Contact> findAll() {
        return jpaContactsRepository.findAll();
    }

    @Override
    public Contact findById(Long id) {
        return jpaContactsRepository.findById(id).orElseThrow(
                () -> new ContactNotFoundException("Contact not found with id: " + id)
        );
    }

    @Override
    public Contact createContact(Contact contact) {
        return jpaContactsRepository.save(contact);
    }

    @Override
    public Contact updateContact(Long id, Contact contact) {
        return jpaContactsRepository.findById(id)
                .map(existingContact -> {
                    existingContact.setName(contact.getName());
                    existingContact.setEmail(contact.getEmail());
                    return jpaContactsRepository.save(existingContact);
                })
                .orElseThrow(
                        () -> new ContactNotFoundException("Contact not found with id: " + id)
                );
    }

    @Override
    public void deleteById(Long id) {
        if (!jpaContactsRepository.existsById(id)) {
            throw new ContactNotFoundException("Contact not found with id: " + id);
        }
        jpaContactsRepository.deleteById(id);
    }

    @Override
    public List<Contact> findByUserId(Long ownerId) {
        List<Contact> contacts = jpaContactsRepository.findByUserId(ownerId);
        if (contacts.isEmpty()) {
            throw new OwnerNotFoundException("Owner with id " + ownerId + " not found.");
        }
        return contacts;
    }
}
