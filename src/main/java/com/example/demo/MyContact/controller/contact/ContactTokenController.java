package com.example.demo.MyContact.controller.contact;

import com.example.demo.MyContact.dto.CreateContactDTO;
import com.example.demo.MyContact.dto.ResponseContactDTO;
import com.example.demo.MyContact.dto.UpdateContactDTO;
import com.example.demo.MyContact.service.auth.AuthService;
import com.example.demo.MyContact.service.contact.ContactService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/token/contacts")
@Tag(name = "Contacts", description = "Описание")
public class ContactTokenController {
    @Autowired
    private ContactService contactService;

    @Autowired
    private AuthService authService;

    private boolean isTokenValid(String token) {
        return token != null && authService.validateToken(token);
    }

    @Operation(summary = "Получить все мои контакты", description = "Возвращает список всех контактов юзера.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Успешное получение списка контактов")})
    @GetMapping
    public ResponseEntity<List<ResponseContactDTO>> getAllContactsToken(@RequestHeader("Authorization") String token) {
        if (!isTokenValid(token)) {
            return ResponseEntity.status(401).build();
        }
        Long ownerId = authService.getUserIdFromToken(token);
        if (ownerId == null) {
            return ResponseEntity.status(401).build();
        }
        List<ResponseContactDTO> contacts = contactService.getContactsByUserId(ownerId);
        return ResponseEntity.ok(contacts);
    }

    @Operation(summary = "Получить контакт по ID", description = "Возвращает контакт по указанному идентификатору.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Успешное получение контакта"),
            @ApiResponse(responseCode = "404", description = "Контакт не найден")})
    @GetMapping("/{id}")
    public ResponseEntity<ResponseContactDTO> getContactByIdToken(@RequestHeader("Authorization") String token, @PathVariable Long id) {
        if (!isTokenValid(token)) {
            return ResponseEntity.status(401).build();
        }
        ResponseContactDTO responseContactDTO = contactService.getContactById(id);

        return ResponseEntity.ok(responseContactDTO);
    }

    @Operation(summary = "Создать новый контакт", description = "Создает новый контакт с указанными данными.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Контакт успешно создан")})
    @PostMapping
    public ResponseEntity<ResponseContactDTO> createContactToken(
            @RequestHeader("Authorization") String token, @RequestBody CreateContactDTO createContactDTO) {
        if (!isTokenValid(token)) {
            return ResponseEntity.status(401).build();
        }
        Long ownerId = authService.getUserIdFromToken(token);
        ResponseContactDTO responseContactDTO = contactService.createContact(ownerId, createContactDTO);
        return ResponseEntity.ok(responseContactDTO);
    }

    @Operation(summary = "Обновить контакт", description = "Обновляет данные существующего контакта.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Контакт успешно обновлен"),
            @ApiResponse(responseCode = "404", description = "Контакт не найден")})
    @PutMapping("/{id}")
    public ResponseEntity<ResponseContactDTO> updateContactToken(@RequestHeader("Authorization") String token, @PathVariable Long id, @RequestBody UpdateContactDTO updatedContact) {
        if (!isTokenValid(token)) {
            return ResponseEntity.status(401).build();
        }
        ResponseContactDTO responseContactDTO = contactService.updateContact(id, updatedContact);
        return
                ResponseEntity.ok(responseContactDTO);
    }

    @Operation(summary = "Удалить контакт", description = "Удаляет контакт по указанному идентификатору.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Контакт успешно удален"),
            @ApiResponse(responseCode = "404", description = "Контакт не найден")})
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteContactToken(@RequestHeader("Authorization") String token, @PathVariable Long id) {
        if (!isTokenValid(token)) {
            return ResponseEntity.status(401).build();
        }
        contactService.deleteContact(id);
        return ResponseEntity.noContent().build();
    }
}
