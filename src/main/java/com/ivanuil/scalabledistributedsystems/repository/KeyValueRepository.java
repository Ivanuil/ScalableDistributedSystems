package com.ivanuil.scalabledistributedsystems.repository;

import jakarta.annotation.PostConstruct;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class KeyValueRepository {

    private final List<JdbcTemplate> jdbcTemplates;

    public KeyValueRepository(List<JdbcTemplate> jdbcTemplates) {
        this.jdbcTemplates = jdbcTemplates;
    }

    private JdbcTemplate chooseShoulder(String key) {
        return jdbcTemplates.get(key.hashCode() % jdbcTemplates.size());
    }

    @PostConstruct
    public void init() {
        jdbcTemplates
                .forEach(jdbcTemplate -> jdbcTemplate.execute("""
                   CREATE TABLE IF NOT EXISTS key_value (
                   key VARCHAR PRIMARY KEY,
                   value VARCHAR NOT NULL
                   );
                """));
    }

    public void put(String key, String value) {
        chooseShoulder(key).update("""
                INSERT INTO key_value (
                key, value) VALUES (
                ?, ?)
                ON CONFLICT(key) DO UPDATE SET value = ?;
                """, key, value, value);
    }

    public String get(String key) {
        var resultSet = chooseShoulder(key).queryForRowSet("""
             SELECT value FROM key_value WHERE key = ?;
         """, key);
        if (resultSet.next())
            return resultSet.getString("value");
        return null;
    }

}
