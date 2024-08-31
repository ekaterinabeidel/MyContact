package com.example.demo.MyContact.repository.contact;

import com.example.demo.MyContact.dto.ContactDTO;
import com.example.demo.MyContact.entity.Contact;
import com.example.demo.MyContact.entity.Phone;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Repository
public class DatabaseContactRepository implements ContactRepository {

    private final ContactJpaRepository contactJpaRepository;
    @Autowired
    public DatabaseContactRepository(ContactJpaRepository contactJpaRepository) {
        this.contactJpaRepository = contactJpaRepository;
    }

    //    @Autowired
//    private JdbcTemplate jdbcTemplate;


//    private final RowMapper<Contact> contactRowMapper = (ResultSet rs, int rowNum) -> {
//        Contact contact = new Contact();
//        contact.setId(rs.getLong("id"));
//        contact.setName(rs.getString("name"));
//        contact.setFullname(rs.getString("fullname"));
//        contact.setEmail(rs.getString("email"));
//        contact.setOwnerId(rs.getLong("owner_id"));
//
//        String phoneSql = "SELECT phone FROM phones WHERE contact_id = ?";
//        List<String> phones = jdbcTemplate.query(phoneSql,
//                (rs1, rowNum1) -> rs1.getString("phone"), contact.getId());
//        contact.setPhones(phones);
//        return contact;
//    };

    @Override
    public List<Contact> findAll() {
//        String sql = "SELECT * FROM contacts ORDER BY created_at DESC";
//        return jdbcTemplate.query(sql, contactRowMapper);
        return contactJpaRepository.findAll();
    }

    @Override
    public Optional<Contact> findById(Long id) {
//        String sql = "SELECT * FROM contacts WHERE id = ?";
//        try {
//            Contact contact = jdbcTemplate.queryForObject(sql, new Object[]{id}, contactRowMapper);
//            return Optional.ofNullable(contact);
//        } catch (EmptyResultDataAccessException e) {
//            return Optional.empty();
//        }
        return contactJpaRepository.findById(id);
    }

    @Override
    public Contact createContact(Contact contact) {
//        String sql = "INSERT INTO contacts (name, fullname, email, owner_id) VALUES (?, ?, ?, ?)";
//        KeyHolder keyHolder = new GeneratedKeyHolder();
//        jdbcTemplate.update(connection -> {
//            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
//            ps.setString(1, contact.getName());
//            ps.setString(2, contact.getFullname());
//            ps.setString(3, contact.getEmail());
//            ps.setLong(4, contact.getOwnerId());
//            return ps;
//        }, keyHolder);
//        Number key = keyHolder.getKey();
//        if (key != null) {
//            contact.setId(key.longValue());
//            String insertPhoneSql = "INSERT INTO phones (contact_id, phone) VALUES (?, ?)";
//            for (String phone : contact.getPhones()) {
//                jdbcTemplate.update(insertPhoneSql, contact.getId(), phone);
//            }
//            return contact;
//        }
//        throw new RuntimeException("Failed to generate ID for new contact.");
        return contactJpaRepository.save(contact);
    }

    @Override
    public Contact updateContact(Long id, ContactDTO contactDTO) {
//        String selectContactSql = "SELECT * FROM contacts WHERE id = ?";
//        Contact existingContact = jdbcTemplate.queryForObject(selectContactSql,
//                new BeanPropertyRowMapper<>(Contact.class), id);
//
//        if (existingContact == null) {
//            throw new NoSuchElementException("Contact with id " + id + " does not exist.");
//        }
//
//        existingContact.setName(contactDTO.getName() != null ? contactDTO.getName() : existingContact.getName());
//        existingContact.setFullname(contactDTO.getFullname() != null ? contactDTO.getFullname() : existingContact.getFullname());
//        existingContact.setEmail(contactDTO.getEmail() != null ? contactDTO.getEmail() : existingContact.getEmail());
//
//        String sql = "UPDATE contacts SET name = ?, fullname = ?, email = ? WHERE id = ?";
//        int rowsAffected = jdbcTemplate.update(sql,
//                contactDTO.getName(),
//                contactDTO.getFullname(),
//                contactDTO.getEmail(),
//                id);
//        if (rowsAffected == 0) {
//            throw new RuntimeException("Failed to update contact with id " + id);
//        }
//
//        String selectPhonesSql = "SELECT phone FROM phones WHERE contact_id = ?";
//        List<String> existingPhones = jdbcTemplate.queryForList(selectPhonesSql, String.class, id);
//
//        String insertPhoneSql = "INSERT INTO phones (contact_id, phone) VALUES (?, ?)";
//        for (String phone : contactDTO.getPhones()) {
//            if (!existingPhones.contains(phone)) {
//                jdbcTemplate.update(insertPhoneSql, id, phone);
//            }
//        }
//
//        Contact contact = new Contact();
//        contact.setId(id);
//        contact.setName(contactDTO.getName());
//        contact.setFullname(contactDTO.getFullname());
//        contact.setEmail(contactDTO.getEmail());
//
//        return contact;
        return contactJpaRepository.findById(id)
                .map(existingContact -> {
                    existingContact.setName(contactDTO.getName());
                    existingContact.setEmail(contactDTO.getEmail());
                    return contactJpaRepository.save(existingContact);
                })
                .orElseThrow(() -> new RuntimeException("Contact not found with id: " + id));
    }

    @Override
    public int deleteById(Long id) {
//        String deletePhonesSql = "DELETE FROM phones WHERE contact_id = ?";
//        jdbcTemplate.update(deletePhonesSql, id);
//
//        String deleteContactSql = "DELETE FROM contacts WHERE id = ?";
//        return jdbcTemplate.update(deleteContactSql, id);
        if (contactJpaRepository.existsById(id)) {
            contactJpaRepository.deleteById(id);
            return 1;
        }
        return 0;
    }

    @Override
    public List<Contact> findByUserId(Long ownerId) {
//        String sql = "SELECT * FROM contacts WHERE owner_id = ?";
//        try {
//            return jdbcTemplate.query(sql, new Object[]{ownerId}, contactRowMapper);
//        } catch (EmptyResultDataAccessException e) {
//            return List.of();
//        }
        return contactJpaRepository.findByOwnerId(ownerId);
    }

    @Override
    public Contact saveContact(Contact contact) {
//        if (contact.getId() != null) {
//            Optional<Contact> existingContact = findById(contact.getId());
//            if (existingContact.isPresent()) {
//                return updateContact(contact.getId(), new ContactDTO(contact));
//            } else {
//                throw new RuntimeException("Contact with id " + contact.getId() + " does not exist.");
//            }
//        } else {
//            return createContact(contact);
//        }
        return contactJpaRepository.save(contact);
    }
}
