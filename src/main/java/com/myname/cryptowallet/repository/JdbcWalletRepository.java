package com.myname.cryptowallet.repository;

import com.myname.cryptowallet.model.TypeCrypto;
import com.myname.cryptowallet.model.Wallet;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Implémentation JDBC de WalletRepository.
 * Hypothèse de tables:
 *  CREATE TABLE wallets (
 *    id UUID PRIMARY KEY,
 *    address TEXT NOT NULL,
 *    balance NUMERIC(20,8) NOT NULL,
 *    crypto_type TEXT NOT NULL
 *  );
 */
public class JdbcWalletRepository implements WalletRepository {

    private final DatabaseConnection db;

    public JdbcWalletRepository(DatabaseConnection db) {
        this.db = db;
    }

    @Override
    public Wallet save(Wallet wallet) {
        String upsert = "INSERT INTO wallets (id, address, balance, crypto_type) VALUES (?, ?, ?, ?) " +
                "ON CONFLICT (id) DO UPDATE SET address = EXCLUDED.address, balance = EXCLUDED.balance, crypto_type = EXCLUDED.crypto_type";
        try (Connection c = db.getConnection(); PreparedStatement ps = c.prepareStatement(upsert)) {
            ps.setObject(1, wallet.getId());
            ps.setString(2, wallet.getAddress());
            ps.setBigDecimal(3, wallet.getBalance());
            ps.setString(4, wallet.getCryptoType().name());
            ps.executeUpdate();
            return wallet;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<Wallet> findById(UUID id) {
        String sql = "SELECT id, address, balance, crypto_type FROM wallets WHERE id = ?";
        try (Connection c = db.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setObject(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Wallet w = mapRow(rs);
                    return Optional.of(w);
                }
                return Optional.empty();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Wallet> findAll() {
        String sql = "SELECT id, address, balance, crypto_type FROM wallets";
        List<Wallet> list = new ArrayList<>();
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
        String sql = "DELETE FROM wallets WHERE id = ?";
        try (Connection c = db.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setObject(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private Wallet mapRow(ResultSet rs) throws SQLException {
        UUID id = (UUID) rs.getObject("id");
        String address = rs.getString("address");
        BigDecimal balance = rs.getBigDecimal("balance");
        TypeCrypto type = TypeCrypto.valueOf(rs.getString("crypto_type"));
        Wallet w = new Wallet(address, type);
        // Remplacer l'id et le solde générés par ceux de la DB
        try {
            java.lang.reflect.Field idField = Wallet.class.getDeclaredField("id");
            idField.setAccessible(true);
            idField.set(w, id);
            java.lang.reflect.Field balField = Wallet.class.getDeclaredField("balance");
            balField.setAccessible(true);
            balField.set(w, balance);
        } catch (ReflectiveOperationException ignore) {
        }
        return w;
    }
}



