package com.ivanuil.scalabledistributedsystems.repository;

import jakarta.annotation.PostConstruct;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class KeyValueRepository {

    public KeyValueRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private final JdbcTemplate jdbcTemplate;

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
        var resultSet =  jdbcTemplate.queryForRowSet("""
             SELECT value FROM key_value WHERE key = ?;
         """, key);
        if (resultSet.next())
            return resultSet.getString("value");
        return null;
    }

}
