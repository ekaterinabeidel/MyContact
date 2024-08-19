package com.example.demo.MyContact.repository;

import com.example.demo.MyContact.model.Contact;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DatabaseContactRepository extends JpaRepository<Contact, Long> {

}
