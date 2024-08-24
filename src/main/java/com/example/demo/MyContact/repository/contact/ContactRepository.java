package com.example.demo.MyContact.repository.contact;

import com.example.demo.MyContact.model.contact.Contact;
import com.example.demo.MyContact.model.contact.ContactDTO;

import java.util.List;
import java.util.Optional;

/**
 * Репозиторий (Repository)
 * Управляет доступом к данным.
 * Обеспечивает взаимодействие с базой данных.
 * Spring Data JPA автоматически генерирует реализацию для интерфейса ContactRepository.
 * Контейнер Spring инжектирует этот репозиторий в нужные места,
 * например, в сервисы, такие как DatabaseContactService.
 */
public interface ContactRepository {
    List<Contact> findAll();

    Optional<Contact> findById(Long id);

    Contact createContact(Contact contact);

    Contact updateContact(Long id, ContactDTO contactDTO);

    Contact saveContact(Contact contact);

    int deleteById(Long id);

    List<Contact> findByUserId(Long ownerId);

}
