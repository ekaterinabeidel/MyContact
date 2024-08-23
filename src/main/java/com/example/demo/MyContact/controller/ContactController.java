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
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

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
@Tag(name = "Contacts", description = "Описание")
public class ContactController {

    @Autowired
    private ContactService contactService;
    @Operation(summary = "Получить все контакты", description = "Возвращает список всех контактов.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Успешное получение списка контактов") })
    @GetMapping
    public ResponseEntity<List<Contact>> getAllContacts() {
        List<Contact> contacts = contactService.getAllContacts();
        return ResponseEntity.ok(contacts);
    }
    @Operation(summary = "Получить контакт по ID", description = "Возвращает контакт по указанному идентификатору.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Успешное получение контакта"),
            @ApiResponse(responseCode = "404", description = "Контакт не найден") })
    @GetMapping("/{id}")
    public ResponseEntity<Contact> getContactById(@PathVariable Long id) {
        Optional<Contact> contact = contactService.getContactById(id);
        return contact.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Operation(summary = "Создать новый контакт", description = "Создает новый контакт с указанными данными.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Контакт успешно создан") })
    @PostMapping
    public ResponseEntity<Contact> createContact(@RequestBody ContactDTO contactDTO) {
        Contact contact = contactService.createContact(contactDTO);
        return ResponseEntity.ok(contact);
    }

    @Operation(summary = "Обновить контакт", description = "Обновляет данные существующего контакта.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Контакт успешно обновлен"),
            @ApiResponse(responseCode = "404", description = "Контакт не найден") })
    @PutMapping("/{id}")
    public ResponseEntity<Contact> updateContact(@PathVariable Long id, @RequestBody ContactDTO updatedContact) {
        return ResponseEntity.ok(contactService.updateContact(id, updatedContact));
    }

    @Operation(summary = "Удалить контакт", description = "Удаляет контакт по указанному идентификатору.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Контакт успешно удален"),
            @ApiResponse(responseCode = "404", description = "Контакт не найден") })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteContact(@PathVariable Long id) {
        boolean deleted = contactService.deleteContact(id);
        return deleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
}
