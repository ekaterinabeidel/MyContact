package com.example.demo.MyContact.repository.user;

import com.example.demo.MyContact.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.Optional;

@Repository
public class DataBaseUserRepository implements UserRepository {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public DataBaseUserRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Optional<User> findByEmail(String email) {
        String sql = "SELECT * FROM users WHERE email = ?";
        return jdbcTemplate.query(sql, new Object[]{email}, new UserRowMapper())
                .stream().findFirst();
    }

    @Override
    public Optional<User> findByEmailAndPassword(String email, String password) {
        String sql = "SELECT * FROM users WHERE email = ? AND password = ?";
        return jdbcTemplate.query(sql, new Object[]{email, password}, new UserRowMapper())
                .stream().findFirst();
    }

    @Override
    public void save(User user) {
        String sql = "INSERT INTO users (email, password, role, name) VALUES (?, ?, ?, ?)";
        jdbcTemplate.update(sql, user.getEmail(), user.getPassword(), user.getRole(), user.getName());
    }

    private static class UserRowMapper implements RowMapper<User> {
        @Override
        public User mapRow(ResultSet rs, int rowNum) throws SQLException {
            User user = new User();
            user.setId(rs.getLong("id"));
            user.setEmail(rs.getString("email"));
            user.setPassword(rs.getString("password"));
            user.setRole(rs.getString("role"));
            user.setName(rs.getString("name"));
            return user;
        }
    }
}
