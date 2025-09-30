package com.myname.cryptowallet.app;

import com.myname.cryptowallet.repository.DatabaseConnection;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Point d'entrée de l'application.
 */
public class Main {

    /**
     * Méthode main de démarrage.
     * @param args arguments de la  ligne de commande
     */
    public static void main(String[] args) {
        // --- Test simple de connexion JDBC PostgreSQL ---
        // Renseignez ici vos paramètres de connexion PostgreSQL
        String url = "jdbc:postgresql://localhost:5432/cryptowallet"; // URL de la base
        String user = "postgres"; // utilisateur
        String password = "postgres"; // mot de passe

        // Astuce: assurez-vous d'avoir le driver JDBC PostgreSQL dans le classpath (org.postgresql:postgresql)
        DatabaseConnection db = new DatabaseConnection(url, user, password);
        try (Connection conn = db.getConnection()) {
            // Si aucune exception, la connexion est OK
            System.out.println("Connexion réussie");
        } catch (SQLException e) {
            // Affiche l'erreur si la connexion échoue
            System.out.println("Erreur de connexion: " + e.getMessage());
        }

        // --- Lancement de l'application console ---
        new ConsoleApp().run();
    }
}


