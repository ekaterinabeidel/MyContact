package com.example.demo.MyContact.service.contact;

import com.example.demo.MyContact.dto.ContactDTO;
import com.example.demo.MyContact.entity.Contact;

import java.util.List;
import java.util.Optional;

/**
 * Сервис (Service)
 * Обрабатывает бизнес-логику приложения.
 * Поддерживает переключение между различными реализациями:
 * Сохранение контактов в памяти приложения.
 * Сохранение контактов в базе данных.
 * Использует аннотации для управления реализациями.
 */

public interface ContactService {
    List<Contact> getAllContacts();

    Optional<Contact> getContactById(Long id);

    Contact createContact(Long ownerId, ContactDTO contactDTO);

    Contact updateContact(Long id, ContactDTO updatedContact);

    boolean deleteContact(Long id);

    List<Contact> getContactsByUserId(Long ownerId);

}
