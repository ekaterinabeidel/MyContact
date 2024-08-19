package com.example.demo.MyContact.repository;

import com.example.demo.MyContact.model.Contact;

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
public interface ContactRepository  {
    List<Contact> findAll();
    Optional<Contact> findById(Long id);
    Contact save(Contact contact); //update, create
    void deleteById(Long id);

}
