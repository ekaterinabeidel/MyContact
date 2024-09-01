package com.example.demo.MyContact.service.contact;

import com.example.demo.MyContact.dto.CreateContactDTO;
import com.example.demo.MyContact.dto.ResponseContactDTO;
import com.example.demo.MyContact.dto.UpdateContactDTO;

import java.util.List;

/**
 * Сервис (Service)
 * Обрабатывает бизнес-логику приложения.
 * Поддерживает переключение между различными реализациями:
 * Сохранение контактов в памяти приложения.
 * Сохранение контактов в базе данных.
 * Использует аннотации для управления реализациями.
 */

public interface ContactService {
    List<ResponseContactDTO> getAllContacts();

    ResponseContactDTO getContactById(Long id);

    ResponseContactDTO createContact(Long ownerId, CreateContactDTO createContactDTO);

    ResponseContactDTO updateContact(Long id, UpdateContactDTO updateContactDTO);

    void deleteContact(Long id);

    List<ResponseContactDTO> getContactsByUserId(Long ownerId);

}
