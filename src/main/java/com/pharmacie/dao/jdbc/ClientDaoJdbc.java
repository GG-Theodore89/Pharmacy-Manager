
package com.pharmacie.dao.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.pharmacie.config.DatabaseConnection;
import com.pharmacie.dao.*;
import com.pharmacie.model.Client;
import com.pharmacie.model.StatutVente;
import com.pharmacie.model.Vente;

public class ClientDaoJdbc implements ClientDao {

    // Fournisseur centralisé de connexions à la base de données SQLite
    private final DatabaseConnection databaseConnection;

    // Injecter le fournisseur de connexions lors de la création du DAO
    public ClientDaoJdbc(DatabaseConnection databaseConnection) {
        this.databaseConnection = databaseConnection;
    }

    @Override
    public Boolean enregister(Client client) {

        try (
            // Ouvrir une nouvelle connexion SQLite configurée
            Connection connection = databaseConnection.getConnection();

            // Préparer la requête SQL pour insérer un nouveau client
            PreparedStatement ps = connection.prepareStatement(
                "INSERT INTO Client (nom, prenom, adresse, telephone, email, actif, Ordonence) " +
                "VALUES (?,?,?,?,?,?,?)"
            )
        ) {

            // Affecter les valeurs du client aux paramètres de la requête SQL
            ps.setString(1, client.getNom());
            ps.setString(2, client.getPrenom());
            ps.setString(3, client.getAdresse());
            ps.setString(4, client.getTelephone());
            ps.setString(5, client.getEmail());
            ps.setBoolean(6, client.isActif());
            ps.setString(7, client.getOrdonence());

            // Exécuter la requête INSERT et récupérer le nombre de lignes ajoutées
            int added = ps.executeUpdate();

            // Vérifier si exactement un client a été ajouté
            if (added != 1) {
                return false;
            }

            // Confirmer que l'insertion a réussi
            return true;
        }
        catch (SQLException e) {

            // Afficher les détails de l'erreur SQL en cas de problème
            e.printStackTrace();

            // Signaler l'échec de l'insertion
            return false;
        }
    }

    public Client trouverParId(Integer id) {

        try (
            // Ouvrir une nouvelle connexion à la base de données
            Connection connection = databaseConnection.getConnection();

            // Préparer la requête SQL pour rechercher un client par son ID
            PreparedStatement ps = connection.prepareStatement(
                "SELECT * FROM Client WHERE id = ?"
            )
        ) {

            // Remplacer le paramètre ? par l'identifiant du client
            ps.setInt(1, id);

            // Exécuter la requête SELECT et récupérer les résultats
            try (ResultSet rs = ps.executeQuery()) {

                // Vérifier si un client existe avec cet identifiant
                boolean exist = rs.next();

                // Si aucun client n'est trouvé, retourner null
                if (!exist) {
                    return null;
                }

                // Construire un objet Client à partir des données récupérées
                Client current_client = new Client(
                    rs.getInt("id"),
                    rs.getString("nom"),
                    rs.getString("prenom"),
                    rs.getString("adresse"),
                    rs.getString("telephone"),
                    rs.getString("email"),
                    rs.getBoolean("actif"),
                    rs.getString("Ordonence")
                );

                // Retourner le client trouvé
                return current_client;
            }
        }
        catch (SQLException e) {

            // Afficher les détails de l'erreur SQL
            e.printStackTrace();

            // Signaler qu'aucun résultat n'a pu être récupéré
            return null;
        }
    }

    @Override
    public Boolean mettreAJour(Integer id, Client client) {

        try (
            // Ouvrir une nouvelle connexion à la base de données
            Connection connection = databaseConnection.getConnection();

            // Préparer la requête SQL UPDATE pour modifier les informations du client
            PreparedStatement psn = connection.prepareStatement(
                "UPDATE Client SET nom = ?, prenom = ?, adresse = ?, " +
                "telephone = ?, email = ?, actif = ?, Ordonence = ? WHERE id = ?"
            )
        ) {

            // Affecter les nouvelles valeurs aux paramètres de la requête
            psn.setString(1, client.getNom());
            psn.setString(2, client.getPrenom());
            psn.setString(3, client.getAdresse());
            psn.setString(4, client.getTelephone());
            psn.setString(5, client.getEmail());
            psn.setBoolean(6, client.isActif());
            psn.setString(7, client.getOrdonence());
            psn.setInt(8, id);

            // Exécuter la requête UPDATE et récupérer le nombre de lignes modifiées
            int rowsAffected = psn.executeUpdate();

            // Vérifier si aucune ligne n'a été modifiée
            if (rowsAffected == 0) {
                return false;
            }

            // Confirmer que la modification a réussi
            return true;
        }
        catch (SQLException e) {

            // Afficher les détails de l'erreur SQL
            e.printStackTrace();

            // Signaler l'échec de la modification
            return false;
        }
    }

    @Override
    public Boolean supprimer(Integer id) {

        try (
            // Ouvrir une nouvelle connexion à la base de données
            Connection connection = databaseConnection.getConnection();

            // Préparer la requête SQL DELETE pour supprimer le client selon son ID
            PreparedStatement ps = connection.prepareStatement(
                "DELETE FROM Client WHERE id = ?"
            )
        ) {

            // Affecter l'identifiant du client au paramètre de la requête
            ps.setInt(1, id);

            // Exécuter la requête DELETE et récupérer le nombre de lignes supprimées
            int rowsAffected = ps.executeUpdate();

            // Vérifier si aucun client ne correspond à cet identifiant
            if (rowsAffected == 0) {
                return false;
            }

            // Confirmer que la suppression a réussi
            return true;
        }
        catch (SQLException e) {

            // Afficher les détails de l'erreur SQL
            e.printStackTrace();

            // Signaler l'échec de la suppression
            return false;
        }
    }

    public List<Client> rechercher(String text) {

        // Créer une liste vide pour stocker les clients trouvés
        List<Client> current_List = new ArrayList<>();

        try (
            // Ouvrir une nouvelle connexion à la base de données
            Connection connection = databaseConnection.getConnection();

            // Préparer la requête SQL pour rechercher les clients
            // dont le nom ou le prénom contient le texte recherché
            PreparedStatement ps = connection.prepareStatement(
                "SELECT * FROM Client WHERE NOM LIKE ? OR PRENOM LIKE ?"
            )
        ) {

            // Affecter le texte de recherche aux paramètres de la requête
            // Le symbole % permet de rechercher une correspondance partielle
            ps.setString(1, "%" + text + "%");
            ps.setString(2, "%" + text + "%");

            // Exécuter la requête SELECT et récupérer les résultats
            try (ResultSet rs = ps.executeQuery()) {

                // Parcourir les résultats tant qu'une ligne existe
                while (rs.next()) {

                    // Créer un objet Client à partir des informations
                    // récupérées depuis la ligne actuelle du ResultSet
                    Client current_client = new Client(
                        rs.getInt("id"),
                        rs.getString("nom"),
                        rs.getString("prenom"),
                        rs.getString("adresse"),
                        rs.getString("telephone"),
                        rs.getString("email"),
                        rs.getBoolean("actif"),
                        rs.getString("Ordonence")
                    );

                    // Ajouter le client à la liste des résultats
                    current_List.add(current_client);
                }
            }

            // Retourner la liste des clients trouvés
            // Si aucun client ne correspond, la liste reste vide
            return current_List;
        }
        catch (SQLException e) {

            // Afficher les détails de l'erreur SQL en cas de problème
            e.printStackTrace();

            // Signaler qu'une erreur est survenue pendant la recherche
            return null;
        }
    }

    public List<Vente> aDesVentes(int clientId) {

        // Initialiser une liste vide pour stocker l'historique des ventes du client
        List<Vente> current_List = new ArrayList<>();

        try (
            // Ouvrir une nouvelle connexion à la base de données
            Connection connection = databaseConnection.getConnection();

            // Préparer la requête SQL pour récupérer les ventes associées au client
            PreparedStatement ps = connection.prepareStatement(
                "SELECT * " +
                "FROM Vente " +
                "INNER JOIN Client ON Vente.clientId = Client.id " +
                "WHERE Client.id = ?"
            )
        ) {

            // Affecter l'identifiant du client au paramètre de la requête
            ps.setInt(1, clientId);

            // Exécuter la requête SELECT et récupérer les résultats
            try (ResultSet rs = ps.executeQuery()) {

                // Parcourir les ventes récupérées depuis la base de données
                while (rs.next()) {

                    // Initialiser la date d'annulation à null
                    LocalDateTime dateAnnulation = null;

                    // Vérifier si une date d'annulation existe avant de la convertir
                    if (rs.getString("dateAnnulation") != null) {
                        dateAnnulation = LocalDateTime.parse(
                            rs.getString("dateAnnulation")
                        );
                    }

                    // Créer un objet Vente à partir des données récupérées
                    Vente current_VenteClient = new Vente(
                        rs.getInt("id"),
                        LocalDateTime.parse(rs.getString("dateVente")),
                        rs.getBigDecimal("montantTotal"),
                        StatutVente.valueOf(rs.getString("statutVente")),
                        dateAnnulation,
                        rs.getString("motifAnnulation")
                    );

                    // Ajouter la vente à la liste de l'historique
                    current_List.add(current_VenteClient);
                }
            }

            // Retourner la liste contenant l'historique des ventes
            // Si le client n'a aucune vente, la liste reste vide
            return current_List;
        }
        catch (SQLException e) {

            // Afficher les détails de l'erreur SQL
            e.printStackTrace();

            // Signaler qu'une erreur est survenue lors de la récupération des ventes
            return null;
        }
    }
}
