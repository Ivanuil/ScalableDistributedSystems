package com.ivanuil.scalabledistributedsystems.repository;

import jakarta.annotation.PostConstruct;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class KeyValueRepository {

    private final JdbcTemplate jdbcTemplate;

    public KeyValueRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @PostConstruct
    public void init() {
        jdbcTemplate.execute("""
           CREATE TABLE IF NOT EXISTS key_value (
                key VARCHAR PRIMARY KEY,
                value VARCHAR NOT NULL
           );
           """);
    }

    public void put(String key, String value) {
        jdbcTemplate.update("""
                INSERT INTO key_value (
                key, value) VALUES (
                ?, ?)
                ON CONFLICT(key) DO UPDATE SET value = ?;
                """, key, value, value);
    }

    public String get(String key) {
        try {
            return jdbcTemplate.queryForObject("""
                SELECT value FROM key_value WHERE key = ?;
                """, String.class, key);
        } catch (EmptyResultDataAccessException e) {
            throw new IllegalStateException("No value found for key '" + key + "'");
        }
    }

}
