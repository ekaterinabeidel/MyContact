package com.example.demo.MyContact.repository.contact;

import com.example.demo.MyContact.entity.Contact;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

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
    Contact findById(Long id);
    Contact createContact(Contact contact);

    Contact updateContact(Long id, Contact contact);

    void deleteById(Long id);

    List<Contact> findByUserId(Long ownerId);

}
