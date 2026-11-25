package com.pharmacie.service.impl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.pharmacie.dto.ClientForm;
import com.pharmacie.model.Client;
import com.pharmacie.service.*;

public class ClientServiceImpl implements ClientService {

    // Ajouter un nouveau client à partir des données du formulaire
    public Client ajouter(ClientForm f) {

    // Création d'un nouvel objet Client à partir des informations saisies dans le formulaire
    Client current_client = new Client(
        f.getNom(),
        f.getPrenom(),
        f.getAdresse(),
        f.getTelephone(),
        f.getEmail(),
        f.isActif(),
        f.getOrdonence()
    );

    // Déclaration des variables pour gérer la connexion à la base de données
    // et l'exécution de la requête SQL
    Connection connection = null;
    PreparedStatement ps = null;

    try {
        // Établir une connexion avec la base de données SQLite
        connection = DriverManager.getConnection("jbdc:sqlite:pharmacie.db");

        // Préparer la requête SQL pour insérer un nouveau client dans la table Client
        // Les points d'interrogation (?) seront remplacés par les valeurs du client
        ps = connection.prepareStatement(
            "INSERT INTO Client (nom, prenom, adresse, telephone, email, actif, Ordonence)" +
            "VALUES (?,?,?,?,?,?,?)"
        );

        // Affecter les valeurs du client aux paramètres de la requête SQL
        ps.setString(1, current_client.getNom());
        ps.setString(2, current_client.getPrenom());
        ps.setString(3, current_client.getAdresse());
        ps.setString(4, current_client.getTelephone());
        ps.setString(5, current_client.getEmail());
        ps.setBoolean(6, current_client.isActif());
        ps.setString(7, current_client.getOrdonence());

        // Exécuter la requête INSERT et récupérer le nombre de lignes ajoutées
        int added = ps.executeUpdate();

        // Vérifier si exactement un client a été ajouté
        // Si ce n'est pas le cas, retourner null
        if (added != 1) return null;

        // Retourner l'objet Client après son insertion dans la base de données
        return current_client;
    }
    catch(SQLException e) {
        // Afficher les détails de l'erreur SQL en cas de problème
        e.printStackTrace();

        // Retourner null pour signaler que l'ajout a échoué
        return null;
    }
    finally {
        // Fermer les ressources dans l'ordre inverse de leur création
        try {
            // Fermer le PreparedStatement s'il a été initialisé
            if (ps != null) ps.close();

            // Fermer la connexion à la base de données si elle a été établie
            if (connection != null) connection.close();
        }
        catch(SQLException e) {
            // Afficher les détails d'une éventuelle erreur lors de la fermeture
            e.printStackTrace();
        }
    }
}



    // Modifier les informations d'un client existant à partir de son ID
    public Client modifier(Integer id, ClientForm f) {

        // Initialisation des ressources JDBC à null
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        PreparedStatement psn = null;

        try {
            // Établir une connexion avec la base de données SQLite
            connection = DriverManager.getConnection("jdbc:sqlite:pharmacie.db");

            // Préparer la requête SQL pour rechercher le client par son ID
            ps = connection.prepareStatement(
                "SELECT * FROM Client WHERE id = ?"
            );

            // Remplacer le paramètre ? par l'ID du client
            ps.setInt(1, id);

            // Exécuter la requête SELECT et récupérer les résultats
            rs = ps.executeQuery();

            // Vérifier si le client existe dans la base de données
            boolean exist = rs.next();

            // Si aucun client n'est trouvé, retourner null
            if (!exist) {
                return null;
            }

            // Récupérer les informations du client depuis la base de données
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

            // Mettre à jour les attributs de l'objet avec les données du formulaire
            current_client.setNom(f.getNom());
            current_client.setPrenom(f.getPrenom());
            current_client.setAdresse(f.getAdresse());
            current_client.setTelephone(f.getTelephone());
            current_client.setEmail(f.getEmail());
            current_client.setActif(f.isActif());
            current_client.setOrdonence(f.getOrdonence());

            // Préparer la requête SQL UPDATE pour enregistrer les modifications
            psn = connection.prepareStatement(
                "UPDATE Client SET nom = ?, prenom = ?, adresse = ?, " +
                "telephone = ?, email = ?, actif = ?, Ordonence = ? WHERE id = ?"
            );

            // Affecter les nouvelles valeurs aux paramètres de la requête
            psn.setString(1, current_client.getNom());
            psn.setString(2, current_client.getPrenom());
            psn.setString(3, current_client.getAdresse());
            psn.setString(4, current_client.getTelephone());
            psn.setString(5, current_client.getEmail());
            psn.setBoolean(6, current_client.isActif());
            psn.setString(7, current_client.getOrdonence());
            psn.setInt(8, id);

            // Exécuter la requête UPDATE et récupérer le nombre de lignes affectées
            int rowsAffected = psn.executeUpdate();

            // Vérifier si la modification a affecté au moins une ligne
            if (rowsAffected == 0) {
                return null;
            }

            // Retourner l'objet Client après la modification
            return current_client;
        }

        // Gérer les éventuelles erreurs liées à l'accès à la base de données
        catch (SQLException e) {
            e.printStackTrace();
            return null;
        }

        // Fermer toutes les ressources JDBC pour libérer les ressources utilisées
        finally {
            try {
                // Fermer les ressources dans l'ordre inverse de leur création
                if (psn != null) psn.close();
                if (rs != null) rs.close();
                if (ps != null) ps.close();
                if (connection != null) connection.close();

            } catch (SQLException e) {
                // Afficher les éventuelles erreurs lors de la fermeture des ressources
                e.printStackTrace();
            }
        }
    }

    public void desactiver(Integer id){
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        PreparedStatement psn = null;
        try{
        connection = DriverManager.getConnection("jdbc:sqlite:pharmacie.db");
        ps = connection.prepareStatement("SELECT * FROM Client WHERE id= ? ");
        ps.setInt(1,id);
        rs= ps.executeQuery();
        boolean exist=rs.next();
        if(!exist){
            return;
        }

        psn= connection.prepareStatement("UPDATE Client SET actif= ? WHERE id= ?");
        psn.setBoolean(1, false);
        psn.setInt(2, id);
        int rowsAffected = psn.executeUpdate();
        if(rowsAffected==0){
            return;
        }
        }
        catch (SQLException e) {
        e.printStackTrace();
        }

        finally {
            try {
                if (psn != null) psn.close();
                if (rs != null) rs.close();
                if (ps != null) ps.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    
    }
}