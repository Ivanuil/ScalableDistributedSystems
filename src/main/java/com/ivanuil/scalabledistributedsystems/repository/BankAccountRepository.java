package com.ivanuil.scalabledistributedsystems.repository;

import jakarta.annotation.PostConstruct;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
public class BankAccountRepository {

    private final List<JdbcTemplate> jdbcTemplates;

    public BankAccountRepository(List<JdbcTemplate> jdbcTemplates) {
        this.jdbcTemplates = jdbcTemplates;
    }

    @PostConstruct
    public void init() {
        jdbcTemplates.forEach(jdbcTemplate -> {
            jdbcTemplate.execute("""
                CREATE TABLE IF NOT EXISTS account_balance (
                    account_id INTEGER PRIMARY KEY CHECK(account_id > 0),
                    balance INTEGER NOT NULL CHECK(balance >= 0) DEFAULT 0
                );
                """);
            jdbcTemplate.execute("""
                CREATE TABLE IF NOT EXISTS account_transactions (
                    id INTEGER PRIMARY KEY CHECK (id > 0),
                    account_id INTEGER NOT NULL CHECK(account_id > 0),
                    amount INTEGER NOT NULL
                );
                """);
            jdbcTemplate.execute("""
                CREATE INDEX ON account_transactions (account_id);
                """);
        });
    }

    private JdbcTemplate chooseShoulder(int account_id) {
        return jdbcTemplates.get(Integer.hashCode(account_id) % jdbcTemplates.size());
    }

    @Transactional
    public void deposit(int transactionId, int accountId, double amount) {
        updateBalanceInTransaction(transactionId, accountId, amount);
    }

    public void withdraw(int transactionId, int accountId, double amount) {
        updateBalanceInTransaction(transactionId, accountId, -amount);
    }

    private void updateBalanceInTransaction(int transactionId, int accountId, double amount) {
        chooseShoulder(accountId).update("""
            BEGIN TRANSACTION;
            INSERT INTO account_balance (account_id, balance)
            VAlUES (?, 0)
            ON CONFLICT DO NOTHING;
            UPDATE account_balance
                SET balance = balance + ?
                WHERE account_id = ?;
            INSERT INTO account_transactions (id, account_id, amount) VALUES (
                ?, ?, ?
            );
            COMMIT;
            """, accountId, amount, accountId, transactionId, accountId, amount);
    }

    public int balance(int accountId) {
        var resultSet = chooseShoulder(accountId).queryForRowSet("""
            SELECT balance AS balance FROM .account_balance
            WHERE account_id = ?
            """, accountId);
        if (!resultSet.next()) {
            throw new RuntimeException("No such account");
        }
        return resultSet.getInt("balance");
    }

}
