package com.ivanuil.scalabledistributedsystems.repository;

import com.ivanuil.scalabledistributedsystems.exception.BankAccountException;
import jakarta.annotation.PostConstruct;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
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

    public void deposit(int transactionId, int accountId, double amount) {
        chooseShoulder(accountId).update("""
            INSERT INTO account_transactions (id, account_id, amount) VALUES (
                ?, ?, ?
            );
            """, transactionId, accountId, amount);
    }

    public int balance(int accountId) {
        var resultSet = chooseShoulder(accountId).queryForRowSet("""
            SELECT sum(amount) AS sum FROM account_transactions
            WHERE account_id = ?
            """, accountId);
        if (!resultSet.next()) {
            throw new RuntimeException("No such account");
        }
        return resultSet.getInt("sum");
    }

    public void withdraw(int transactionId, int accountId, double amount) {
        Connection conn = null;
        try {
            conn = chooseShoulder(accountId).getDataSource().getConnection(); // Получаем соединение
            conn.setAutoCommit(false); // Отключаем авто-коммит

            withdrawAsPrepareStatement(transactionId, accountId, amount, conn);
            checkBalanceAsPrepareStatement(accountId, conn);

            conn.commit();
        } catch (SQLException | DataAccessException e) {
            if (conn != null) {
                try {
                    conn.rollback();
                } catch (SQLException ignore) {}
            }
            throw new RuntimeException("Ошибка при переводе средств.", e);
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException ignore) {}
            }
        }
    }

    private static void checkBalanceAsPrepareStatement(int accountId, Connection conn) throws SQLException {
        var psBalance = conn.prepareStatement("SELECT sum(amount) AS sum FROM account_transactions WHERE account_id = ?");
        psBalance.setInt(1, accountId);
        psBalance.execute();
        var resultSet = psBalance.getResultSet();
        if (!resultSet.next()) {
            conn.rollback();
            throw new RuntimeException("No such account");
        }
        if (resultSet.getInt("sum") < 0) {
            conn.rollback();
            throw new BankAccountException("Insufficient balance");
        }
    }

    private static void withdrawAsPrepareStatement(int transactionId, int accountId, double amount, Connection conn) throws SQLException {
        var psWithdrawal = conn.prepareStatement("INSERT INTO account_transactions (id, account_id, amount) VALUES (?, ?, ?);");
        psWithdrawal.setInt(1, transactionId);
        psWithdrawal.setInt(2, accountId);
        psWithdrawal.setDouble(3, -amount);
        psWithdrawal.executeUpdate();
    }

}
