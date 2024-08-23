package com.example.demo.MyContact.controller;

import com.example.demo.MyContact.model.Contact;
import com.example.demo.MyContact.model.ContactDTO;
import com.example.demo.MyContact.service.AuthService;
import com.example.demo.MyContact.service.ContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/token/contacts")
public class ContactTokenController {
    @Autowired
    private ContactService contactService;

    @Autowired
    private AuthService authService;

    private boolean isTokenValid(String token) {
        return token != null && authService.validateToken(token);
    }

    @GetMapping
    public ResponseEntity<List<Contact>> getAllContactsToken(@RequestHeader("Authorization") String token) {
        if (!isTokenValid(token)) {
            return ResponseEntity.status(401).build();
        }
        List<Contact> contacts = contactService.getAllContacts();
        return ResponseEntity.ok(contacts);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Contact> getContactByIdToken(@RequestHeader("Authorization") String token, @PathVariable Long id) {
        if (!isTokenValid(token)) {
            return ResponseEntity.status(401).build();
        }
        Optional<Contact> contact = contactService.getContactById(id);
        return contact.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Contact> createContactToken(@RequestHeader("Authorization") String token, @RequestBody ContactDTO contactDTO) {
        if (!isTokenValid(token)) {
            return ResponseEntity.status(401).build();
        }
        Contact contact = contactService.createContact(contactDTO);
        return ResponseEntity.ok(contact);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Contact> updateContactToken(@RequestHeader("Authorization") String token, @PathVariable Long id, @RequestBody ContactDTO updatedContact) {
        if (!isTokenValid(token)) {
            return ResponseEntity.status(401).build();
        }
        return ResponseEntity.ok(contactService.updateContact(id, updatedContact));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteContactToken(@RequestHeader("Authorization") String token, @PathVariable Long id) {
        if (!isTokenValid(token)) {
            return ResponseEntity.status(401).build();
        }
        contactService.deleteContact(id);
        return ResponseEntity.noContent().build();
    }
}
