package com.example.demo.MyContact.controller;

import com.example.demo.MyContact.model.ContactDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.demo.MyContact.model.Contact;
import com.example.demo.MyContact.service.ContactService;

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


    private final ContactService contactService;

    public ContactController(ContactService contactService) {
        this.contactService = contactService;
    }

    @GetMapping
    public List<Contact> getAllContacts() {
        return contactService.getAllContacts();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Contact> getContactById(@PathVariable Long id) {
        Optional<Contact> contact = contactService.getContactById(id);
        return contact.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public Contact createContact(@RequestBody Contact contact) {
        return contactService.saveContact(contact);
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

    //@PostMapping("/postResponseAPI")
    //public ResponseEntity<ResponseAPI> postResponseAPI(@RequestBody RequestBodyClient requestBodyClient){
    //    ResponseAPI responseAPI = new ResponseAPI();
    //      if(requestBodyClient==null || requestBodyClient.data==null  || requestBodyClient.data.isEmpty){
    //      responseAPI.status = 400;
    //      return ResponseEntity.badRequest().body(responseAPI));
    //    responseAPI.statusCode=200;
    //    responseAPI.response=requestBodyClient.data.toUpperCase();
    //    return  ResponseEntity.ok(responseAPI);
    //}
}
