package com.example.demo.MyContact.service.user;

import com.example.demo.MyContact.model.contact.Contact;
import com.example.demo.MyContact.model.user.User;
import com.example.demo.MyContact.repository.contact.ContactRepository;
import com.example.demo.MyContact.repository.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
//
//@Service
//public class UserService {
//    @Autowired
//    private UserRepository userRepository;
//
//    @Autowired
//    private ContactRepository contactRepository;
//
//    public void createUser(User user) {
//         userRepository.save(user);
//    }
//
//    public Contact addContactToUser(Long userId, Contact contact) {
//        contact.setOwnerId(userId);
//        return contactRepository.saveContact(contact);
//    }
//}
