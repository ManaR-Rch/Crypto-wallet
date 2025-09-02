package com.myname.cryptowallet.repository;

import com.myname.cryptowallet.model.FeePriority;
import com.myname.cryptowallet.model.Transaction;
import com.myname.cryptowallet.model.TransactionStatus;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Implémentation JDBC de TransactionRepository.
 * Hypothèse de tables:
 *  CREATE TABLE transactions (
 *    id UUID PRIMARY KEY,
 *    source_address TEXT NOT NULL,
 *    destination_address TEXT NOT NULL,
 *    amount NUMERIC(20,8) NOT NULL,
 *    priority TEXT NOT NULL,
 *    status TEXT NOT NULL,
 *    created_at TIMESTAMP NOT NULL
 *  );
 */
public class JdbcTransactionRepository implements TransactionRepository {

    private final DatabaseConnection db;

    public JdbcTransactionRepository(DatabaseConnection db) {
        this.db = db;
    }

    @Override
    public Transaction save(Transaction tx) {
        String upsert = "INSERT INTO transactions (id, source_address, destination_address, amount, priority, status, created_at) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?) " +
                "ON CONFLICT (id) DO UPDATE SET source_address=EXCLUDED.source_address, destination_address=EXCLUDED.destination_address, amount=EXCLUDED.amount, priority=EXCLUDED.priority, status=EXCLUDED.status, created_at=EXCLUDED.created_at";
        try (Connection c = db.getConnection(); PreparedStatement ps = c.prepareStatement(upsert)) {
            ps.setObject(1, tx.getId());
            ps.setString(2, tx.getSourceAddress());
            ps.setString(3, tx.getDestinationAddress());
            ps.setBigDecimal(4, tx.getAmount());
            ps.setString(5, tx.getPriority().name());
            ps.setString(6, tx.getStatus().name());
            ps.setTimestamp(7, Timestamp.valueOf(tx.getCreatedAt()));
            ps.executeUpdate();
            return tx;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<Transaction> findById(UUID id) {
        String sql = "SELECT id, source_address, destination_address, amount, priority, status, created_at FROM transactions WHERE id = ?";
        try (Connection c = db.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setObject(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(mapRow(rs));
                }
                return Optional.empty();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Transaction> findAll() {
        String sql = "SELECT id, source_address, destination_address, amount, priority, status, created_at FROM transactions";
        List<Transaction> list = new ArrayList<>();
        try (Connection c = db.getConnection(); PreparedStatement ps = c.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                list.add(mapRow(rs));
            }
            return list;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deleteById(UUID id) {
        String sql = "DELETE FROM transactions WHERE id = ?";
        try (Connection c = db.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setObject(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private Transaction mapRow(ResultSet rs) throws SQLException {
        UUID id = (UUID) rs.getObject("id");
        String src = rs.getString("source_address");
        String dst = rs.getString("destination_address");
        BigDecimal amount = rs.getBigDecimal("amount");
        FeePriority pr = FeePriority.valueOf(rs.getString("priority"));
        TransactionStatus st = TransactionStatus.valueOf(rs.getString("status"));
        LocalDateTime created = rs.getTimestamp("created_at").toLocalDateTime();

        Transaction t = new Transaction(src, dst, amount, pr);
        // Remplace l'id, le status et la date
        try {
            java.lang.reflect.Field idF = Transaction.class.getDeclaredField("id");
            idF.setAccessible(true);
            idF.set(t, id);
            java.lang.reflect.Field stF = Transaction.class.getDeclaredField("status");
            stF.setAccessible(true);
            stF.set(t, st);
            java.lang.reflect.Field dtF = Transaction.class.getDeclaredField("createdAt");
            dtF.setAccessible(true);
            dtF.set(t, created);
        } catch (ReflectiveOperationException ignore) {
        }
        return t;
    }
}



