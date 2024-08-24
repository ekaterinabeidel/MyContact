package com.example.demo.MyContact.repository;

import com.example.demo.MyContact.model.Contact;
import com.example.demo.MyContact.model.ContactDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;
import java.util.Optional;

@Repository
public class DatabaseContactRepository implements ContactRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private final RowMapper<Contact> contactRowMapper = (ResultSet rs, int rowNum) -> {
        Contact contact = new Contact();
        contact.setId(rs.getLong("id"));
        contact.setName(rs.getString("name"));
        contact.setFullname(rs.getString("fullname"));
        contact.setEmail(rs.getString("email"));

        String phoneSql = "SELECT phone FROM phones WHERE contact_id = ?";
        List<String> phones = jdbcTemplate.query(phoneSql,
                (rs1, rowNum1) -> rs1.getString("phone"), contact.getId());
        contact.setPhones(phones);
        return contact;
    };

    @Override
    public List<Contact> findAll() {
        String sql = "SELECT * FROM contacts ORDER BY created_at DESC";
        return jdbcTemplate.query(sql, contactRowMapper);
    }

    @Override
    public Optional<Contact> findById(Long id) {
        String sql = "SELECT * FROM contacts WHERE id = ?";
        try {
            Contact contact = jdbcTemplate.queryForObject(sql, new Object[]{id}, contactRowMapper);
            return Optional.ofNullable(contact);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public Contact createContact(Contact contact) {
        String sql = "INSERT INTO contacts (name, fullname, email) VALUES (?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, contact.getName());
            ps.setString(2, contact.getFullname());
            ps.setString(3, contact.getEmail());
            return ps;
        }, keyHolder);
        Number key = keyHolder.getKey();
        if (key != null) {
            contact.setId(key.longValue());
            String insertPhoneSql = "INSERT INTO phones (contact_id, phone) VALUES (?, ?)";
            for (String phone : contact.getPhones()) {
                jdbcTemplate.update(insertPhoneSql, contact.getId(), phone);
            }
            return contact;
        }
        throw new RuntimeException("Failed to generate ID for new contact.");
    }

    @Override
    public Contact updateContact(Long id, ContactDTO contactDTO) {
        String sql = "UPDATE contacts SET name = ?, fullname = ?, email = ? WHERE id = ?";
        int rowsAffected = jdbcTemplate.update(sql,
                contactDTO.getName(),
                contactDTO.getFullname(),
                contactDTO.getEmail(),
                id);
        if (rowsAffected == 0) {
            throw new RuntimeException("Failed to update contact with id " + id);
        }

        String selectPhonesSql = "SELECT phone FROM phones WHERE contact_id = ?";
        List<String> existingPhones = jdbcTemplate.queryForList(selectPhonesSql, String.class, id);

        String insertPhoneSql = "INSERT INTO phones (contact_id, phone) VALUES (?, ?)";
        for (String phone : contactDTO.getPhones()) {
            if (!existingPhones.contains(phone)) {
                jdbcTemplate.update(insertPhoneSql, id, phone);
            }
        }

        Contact contact = new Contact();
        contact.setId(id);
        contact.setName(contactDTO.getName());
        contact.setFullname(contactDTO.getFullname());
        contact.setEmail(contactDTO.getEmail());
        contact.setPhones(contactDTO.getPhones());
        return contact;
    }

    @Override
    public int deleteById(Long id) {
        String deletePhonesSql = "DELETE FROM phones WHERE contact_id = ?";
        jdbcTemplate.update(deletePhonesSql, id);

        String deleteContactSql = "DELETE FROM contacts WHERE id = ?";
        return jdbcTemplate.update(deleteContactSql, id);
    }

    @Override
    public Contact saveContact(Contact contact) {
        if (contact.getId() != null) {
            Optional<Contact> existingContact = findById(contact.getId());
            if (existingContact.isPresent()) {
                return updateContact(contact.getId(), new ContactDTO(contact));
            } else {
                throw new RuntimeException("Contact with id " + contact.getId() + " does not exist.");
            }
        } else {
            return createContact(contact);
        }
    }
}
