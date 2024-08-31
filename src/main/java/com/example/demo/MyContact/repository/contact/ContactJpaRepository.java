package com.example.demo.MyContact.repository.contact;

import com.example.demo.MyContact.entity.Contact;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ContactJpaRepository extends JpaRepository<Contact, Long> {
    List<Contact> findByOwnerId(Long ownerId);
}
