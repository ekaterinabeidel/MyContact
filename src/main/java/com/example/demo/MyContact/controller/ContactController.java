package com.example.demo.MyContact.controller;

import com.example.demo.MyContact.model.ContactDTO;
import com.example.demo.MyContact.service.AuthService;
import com.example.demo.MyContact.service.DatabaseContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.demo.MyContact.model.Contact;
import com.example.demo.MyContact.service.ContactService;

import java.util.Base64;
import java.util.List;
import java.util.Optional;

/**
 * Контроллер (Controller)
 * Обрабатывает входящие запросы.
 * Предоставляет четыре точки доступа:
 * GET: Получение информации о контакте по его идентификатору или списка всех контактов.
 * POST: Сохранение нового контакта в базе данных.
 * PUT: Обновление информации о существующем контакте.
 * DELETE: Удаление контакта по его идентификатору.
 */

@RestController
@RequestMapping("/api/contacts")
public class ContactController {

    @Autowired
    private ContactService contactService;

    @GetMapping
    public ResponseEntity<List<Contact>> getAllContacts() {
        List<Contact> contacts = contactService.getAllContacts();
        return ResponseEntity.ok(contacts);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Contact> getContactById(@PathVariable Long id) {
        Optional<Contact> contact = contactService.getContactById(id);
        return contact.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Contact> createContact(@RequestBody ContactDTO contactDTO) {
        Contact contact = contactService.createContact(contactDTO);
        return ResponseEntity.ok(contact);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Contact> updateContact(@PathVariable Long id, @RequestBody ContactDTO updatedContact) {
        return ResponseEntity.ok(contactService.updateContact(id, updatedContact));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteContact(@PathVariable Long id) {
        contactService.deleteContact(id);
        return ResponseEntity.noContent().build();
    }
}
