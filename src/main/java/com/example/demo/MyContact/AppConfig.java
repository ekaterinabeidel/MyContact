package com.example.demo.MyContact;
import com.example.demo.MyContact.service.ContactService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class AppConfig {
    @Bean
    @Primary
    public ContactService contactService(@Qualifier(value = "databaseContactService") ContactService contactService) {
        return contactService;
    }
}
