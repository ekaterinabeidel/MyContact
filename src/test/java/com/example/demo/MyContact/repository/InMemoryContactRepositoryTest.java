package com.example.demo.MyContact.repository;

import com.example.demo.MyContact.model.contact.Contact;
import com.example.demo.MyContact.model.contact.ContactDTO;
import com.example.demo.MyContact.repository.contact.InMemoryContactRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class InMemoryContactRepositoryTest {
    private InMemoryContactRepository repository;

    @BeforeEach
    void setUp() {
        repository = new InMemoryContactRepository();
    }

    @Test
    void findAllTest() {
        Contact contact = new Contact();
        contact.setName("Sheldon");
        contact.setFullname("Cooper");
        contact.setEmail("sheldon.cooper@example.com");
        contact.setPhones(List.of("987654321"));

        repository.createContact(contact);

        List<Contact> contacts = repository.findAll();
        assertEquals(1, contacts.size());
        assertEquals("Sheldon", contacts.getFirst().getName());
        assertEquals("Cooper", contacts.getFirst().getFullname());
        assertEquals("sheldon.cooper@example.com", contacts.getFirst().getEmail());
        assertEquals("987654321", contacts.getFirst().getPhones());
    }

    @Test
    void findByIdTest() {
        Contact contact = new Contact();
        contact.setName("Monica");
        contact.setFullname("Geller");
        contact.setEmail("monica.geller@example.com");
        contact.setPhones(List.of("555555555"));

        Contact savedContact = repository.createContact(contact);
        Optional<Contact> foundContact = repository.findById(savedContact.getId());

        assertTrue(foundContact.isPresent());
        assertEquals("Monica", foundContact.get().getName());
        assertEquals("Geller", foundContact.get().getFullname());
        assertEquals("monica.geller@example.com", foundContact.get().getEmail());
        assertEquals("555555555", foundContact.get().getPhones());
    }

    @Test
    void createContactTest() {
        Contact contact = new Contact();
        contact.setName("Rachel");
        contact.setFullname("Green");
        contact.setEmail("rachel.green@example.com");
        contact.setPhones(List.of("123456789"));

        Contact savedContact = repository.createContact(contact);
        assertNotNull(savedContact.getId());
        assertEquals("Rachel", savedContact.getName());
        assertEquals("Green", savedContact.getFullname());
        assertEquals("rachel.green@example.com", savedContact.getEmail());
        assertEquals("123456789", savedContact.getPhones());
    }

    @Test
    void createContactIAExceptionTest() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> repository.createContact(null));
        assertEquals("Contact cannot be null", exception.getMessage());
    }

    @Test
    void updateContactTest() {
        Contact contact = new Contact();
        contact.setName("Leonard");
        contact.setFullname("Hofstadter");
        contact.setEmail("leonard.hofstadter@example.com");
        contact.setPhones(List.of("666666666"));

        Contact savedContact = repository.createContact(contact);
        savedContact.setEmail("leonard.hofstadter.updated@example.com");
        ContactDTO updatedDTO = new ContactDTO(
                "Leonard",
                "Hofstadter",
                "leonard.hofstadter.updated@example.com",
                List.of("666666666")
        );
        Contact updatedContact = repository.updateContact(savedContact.getId(), updatedDTO);

        Optional<Contact> retrievedContact = repository.findById(savedContact.getId());
        assertTrue(retrievedContact.isPresent());
        assertEquals("leonard.hofstadter.updated@example.com", retrievedContact.get().getEmail());
    }

    @Test
    void updateContactIAExceptionContactDTONullTest() {
        Contact contact = new Contact();
        contact.setName("Leonard");
        contact.setFullname("Hofstadter");
        contact.setEmail("leonard.hofstadter@example.com");
        contact.setPhone("666666666");
        Contact savedContact = repository.createContact(contact);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> repository.updateContact(savedContact.getId(), null));
        assertEquals("ID and ContactDTO cannot be null", exception.getMessage());
    }

    @Test
    void updateContactIAExceptionContactIdNullTest() {
        ContactDTO contactDTO = new ContactDTO("Leonard", "Hofstadter", "leonard.hofstadter.updated@example.com", "666666666");
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> repository.updateContact(null, contactDTO));
        assertEquals("ID and ContactDTO cannot be null", exception.getMessage());
    }

    @Test
    void updateContactIAExceptionContactDoesNotExistText() {
        Long nonExistentId = 1L; // Assume this ID does not exist in the repository
        ContactDTO contactDTO = new ContactDTO("Leonard", "Hofstadter", "leonard.hofstadter.updated@example.com", "666666666");

        NoSuchElementException exception = assertThrows(NoSuchElementException.class,
                () -> repository.updateContact(nonExistentId, contactDTO));
        assertEquals("Contact with id " + nonExistentId + " does not exist.", exception.getMessage());
    }

    @Test
    void deleteByIdTest() {
        Contact contact = new Contact();
        contact.setName("Phoebe");
        contact.setFullname("Buffay");
        contact.setEmail("phoebe.buffay@example.com");
        contact.setPhone("777777777");

        Contact savedContact = repository.createContact(contact);
        repository.deleteById(savedContact.getId());

        Optional<Contact> deletedContact = repository.findById(savedContact.getId());
        assertFalse(deletedContact.isPresent());
    }

    @Test
    void saveContactUpdateTest() {
        Contact initialContact = new Contact();
        initialContact.setName("Penny");
        initialContact.setFullname("Hofstadter");
        initialContact.setEmail("penny.hofstadter@example.com");
        initialContact.setPhone("321321321");

        Contact savedContact = repository.saveContact(initialContact);
        Long id = savedContact.getId();

        Contact updatedContact = new Contact();
        updatedContact.setId(id);
        updatedContact.setName("Jane");
        updatedContact.setFullname("Jane Smith");
        updatedContact.setEmail("jane.smith@example.com");
        updatedContact.setPhone("1122334455");

        Contact updatedSavedContact = repository.saveContact(updatedContact);

        assertNotNull(updatedSavedContact);
        assertEquals(id, updatedSavedContact.getId());
        assertEquals("Jane", updatedSavedContact.getName());
        assertEquals("Jane Smith", updatedSavedContact.getFullname());
        assertEquals("jane.smith@example.com", updatedSavedContact.getEmail());
        assertEquals("1122334455", updatedSavedContact.getPhone());
    }

    @Test
    void saveContactCreateTest() {
        Contact contact = new Contact();
        contact.setName("Joey");
        contact.setFullname("Tribbiani");
        contact.setEmail("joey.tribbiani@example.com");
        contact.setPhone("123123123");

        Contact savedContact = repository.saveContact(contact);

        assertNotNull(savedContact.getId());
        assertEquals("Joey", savedContact.getName());
    }

    @Test
    void saveContactIAExceptionContactDoesNotExistTest() {
        Contact contact = new Contact();
        contact.setId(1L);
        NoSuchElementException exception = assertThrows(NoSuchElementException.class,
                () -> repository.saveContact(contact));
        assertEquals("Contact does not exist.", exception.getMessage());
    }
}