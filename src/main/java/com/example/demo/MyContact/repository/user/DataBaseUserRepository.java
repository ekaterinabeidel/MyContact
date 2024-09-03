package com.example.demo.MyContact.repository.user;

import com.example.demo.MyContact.entity.User;
import com.example.demo.MyContact.repository.contact.ContactRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DataBaseUserRepository extends UserRepository {


//    User findByEmailAndPassword(String email, String password) {
//    }
//
//
//    User findByEmail(String email) {
//        return Optional.empty();
//    }


}
