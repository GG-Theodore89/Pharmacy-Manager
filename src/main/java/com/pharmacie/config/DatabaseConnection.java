package com.pharmacie.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Objects;

/**
 * Point central d'ouverture des connexions vers la base de donnees SQLite.
 *
 * <p>Les implementations JDBC des DAO utilisent cette classe. Les interfaces
 * DAO, les services, les controleurs JavaFX et les DAO en memoire ne doivent
 * pas ouvrir directement de connexion a la base de donnees.</p>
 *
 * <p>Cette classe ne conserve pas une connexion globale ouverte. Chaque appel
 * a {@link #getConnection()} retourne une nouvelle connexion que l'appelant
 * doit fermer, normalement avec un bloc try-with-resources.</p>
 */
public final class DatabaseConnection {

    private static final String SQLITE_URL_PREFIX = "jdbc:sqlite:";

    private final String jdbcUrl;

    /**
     * Cree un fournisseur de connexions pour le fichier SQLite indique.
     *
     * <p>Exemple : {@code new DatabaseConnection("pharmacie.db")}.</p>
     *
     * @param databasePath chemin vers le fichier SQLite
     * @throws NullPointerException si le chemin est {@code null}
     * @throws IllegalArgumentException si le chemin est vide
     */
    public DatabaseConnection(String databasePath) {
        Objects.requireNonNull(databasePath, "Le chemin de la base ne peut pas etre null.");

        if (databasePath.isBlank()) {
            throw new IllegalArgumentException("Le chemin de la base ne peut pas etre vide.");
        }

        this.jdbcUrl = SQLITE_URL_PREFIX + databasePath;
    }

    /**
     * Ouvre et configure une nouvelle connexion SQLite.
     *
     * <p>SQLite exige l'activation des cles etrangeres pour chaque nouvelle
     * connexion. Le PRAGMA est donc execute ici afin que tous les DAO JDBC
     * utilisent le meme comportement.</p>
     *
     * @return une nouvelle connexion configuree
     * @throws SQLException si la connexion ou sa configuration echoue
     */
    public Connection getConnection() throws SQLException {
        Connection connection = DriverManager.getConnection(jdbcUrl);

        try {
            enableForeignKeys(connection);
            return connection;
        } catch (SQLException exception) {
            // Evite de laisser une connexion ouverte si sa configuration echoue.
            connection.close();
            throw exception;
        }
    }

    private void enableForeignKeys(Connection connection) throws SQLException {
        try (Statement statement = connection.createStatement()) {
            statement.execute("PRAGMA foreign_keys = ON");
        }
    }
}
