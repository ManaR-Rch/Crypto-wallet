package com.myname.cryptowallet.repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Gère la connexion JDBC à PostgreSQL.
 * Remplir l'URL, l'utilisateur et le mot de passe adaptés à votre environnement.
 */
public class DatabaseConnection {

    // Exemple: jdbc:postgresql://localhost:5432/cryptowallet
    private final String url;
    private final String user;
    private final String password;

    /**
     * Crée un gestionnaire de connexion.
     * @param url JDBC URL PostgreSQL
     * @param user utilisateur
     * @param password mot de passe
     */
    public DatabaseConnection(String url, String user, String password) {
        this.url = "jdbc:postgresql://localhost:5432/java1";
        this.user = "postgres";
        this.password = "12345";
    }

    /**
     * Retourne une nouvelle connexion JDBC.
     */
    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, user, password);
    }
}



