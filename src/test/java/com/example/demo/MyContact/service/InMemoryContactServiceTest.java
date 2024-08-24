package com.example.demo.MyContact.service;

import com.example.demo.MyContact.model.contact.Contact;
import com.example.demo.MyContact.model.contact.ContactDTO;
import com.example.demo.MyContact.repository.contact.InMemoryContactRepository;
import com.example.demo.MyContact.service.contact.InMemoryContactService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

class InMemoryContactServiceTest {

    @Mock
    private InMemoryContactRepository contactRepository;

    @InjectMocks
    private InMemoryContactService contactService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllContactTest() {
        Contact contact = new Contact();
        contact.setName("Joey");
        contact.setFullname("Tribbiani");
        contact.setEmail("joey.tribbiani@example.com");
        contact.setPhone("123456789");

        when(contactRepository.findAll()).thenReturn(List.of(contact));

        List<Contact> contacts = contactService.getAllContacts();
        assertEquals(1, contacts.size());
        assertEquals("Joey", contacts.getFirst().getName());
    }

    @Test
    void getContactById() {
        Contact contact = new Contact();
        contact.setId(1L);
        contact.setName("Chandler");
        contact.setFullname("Bing");
        contact.setEmail("chandler.bing@example.com");

        when(contactRepository.findById(anyLong())).thenReturn(Optional.of(contact));

        Optional<Contact> foundContact = contactService.getContactById(contact.getId());
        assertTrue(foundContact.isPresent());
        assertEquals("Chandler", foundContact.get().getName());
    }

    @Test
    void createContactTest() {
        ContactDTO contactDTO =
                new ContactDTO("Penny", "Hofstadter",
                        "penny.hofstadter@example.com", List.of("987654321", "784375398457"));
        Contact contact = new Contact();
        contact.setName(contactDTO.getName());
        contact.setFullname(contactDTO.getFullname());
        contact.setEmail(contactDTO.getEmail());
        contact.setPhones(contactDTO.getPhones());
        contact.setOwnerId(1L);

        when(contactRepository.createContact(any(Contact.class))).thenReturn(contact);

        Contact result = contactService.createContact(1L, contactDTO);
        assertNotNull(result);
        assertEquals("Penny", result.getName());
        assertEquals("Hofstadter", result.getFullname());
        assertEquals("penny.hofstadter@example.com", result.getEmail());
        assertEquals("987654321", result.getPhones());
        verify(contactRepository, times(1)).createContact(any(Contact.class));
    }

    @Test
    void updateContactTest() {
        ContactDTO contactDTO =
                new ContactDTO("Howard", "Wolowitz", "howard.wolowitz@example.com", "666666666");
        Contact contact = new Contact();
        contact.setId(1L);
        contact.setName(contactDTO.getName());
        contact.setFullname(contactDTO.getFullname());
        contact.setEmail(contactDTO.getEmail());
        contact.setPhone(contactDTO.getPhone());

        when(contactRepository.findById(1L)).thenReturn(Optional.of(contact));
        when(contactRepository.updateContact(eq(1L), any(ContactDTO.class))).thenReturn(contact);

        Contact updatedContact = contactService.updateContact(1L, contactDTO);
        assertEquals("howard.wolowitz@example.com", updatedContact.getEmail());
    }

    @Test
    void deleteContactTest() {
        doNothing().when(contactRepository).deleteById(anyLong());

        contactService.deleteContact(1L);
        verify(contactRepository, times(1)).deleteById(anyLong());
    }
}