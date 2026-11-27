package com.pharmacie.service.impl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.pharmacie.dto.ClientForm;
import com.pharmacie.model.Client;
import com.pharmacie.model.StatutVente;
import com.pharmacie.model.Vente;
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

        public void desactiver(Integer id) {

        // Déclaration des variables nécessaires à la connexion et à l'exécution des requêtes SQL
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        PreparedStatement psn = null;

        try {
            // Établir une connexion avec la base de données SQLite
            connection = DriverManager.getConnection("jdbc:sqlite:pharmacie.db");

            // Préparer une requête pour rechercher un client à partir de son identifiant
            ps = connection.prepareStatement("SELECT * FROM Client WHERE id= ? ");

            // Affecter l'identifiant du client au paramètre de la requête
            ps.setInt(1, id);

            // Exécuter la requête SELECT et récupérer le résultat
            rs = ps.executeQuery();

            // Vérifier si le résultat contient au moins un client
            boolean exist = rs.next();

            // Si le client n'existe pas, quitter immédiatement la méthode
            if (!exist) {
                return;
            }

            // Préparer une requête SQL pour modifier l'état du client
            psn = connection.prepareStatement("UPDATE Client SET actif= ? WHERE id= ?");

            // Définir la valeur de l'attribut actif à false pour désactiver le client
            psn.setBoolean(1, false);

            // Indiquer l'identifiant du client à désactiver
            psn.setInt(2, id);

            // Exécuter la requête UPDATE et récupérer le nombre de lignes modifiées
            int rowsAffected = psn.executeUpdate();

            // Si aucune ligne n'a été modifiée, quitter la méthode
            if (rowsAffected == 0) {
                return;
            }
        }
        catch (SQLException e) {
            // Afficher les informations de l'erreur SQL
            e.printStackTrace();
        }
        finally {
            // Fermer les ressources dans l'ordre inverse de leur création
            try {
                if (psn != null) psn.close();
                if (rs != null) rs.close();
                if (ps != null) ps.close();
                if (connection != null) connection.close();
            }
            catch (SQLException e) {
                // Gérer les erreurs pouvant survenir lors de la fermeture des ressources
                e.printStackTrace();
            }
        }
    }
    public void supprimer(Integer id) {

    // Déclaration des variables nécessaires à la connexion
    // et à l'exécution de la requête SQL
    Connection connection = null;
    PreparedStatement ps = null;

    try {
        // Établir une connexion avec la base de données SQLite
        connection = DriverManager.getConnection("jdbc:sqlite:pharmacie.db");

        // Préparer la requête SQL pour supprimer un client selon son identifiant
        // Le point d'interrogation (?) sera remplacé par l'identifiant fourni
        ps = connection.prepareStatement("DELETE FROM Client WHERE id = ?");

        // Affecter l'identifiant du client au paramètre de la requête
        ps.setInt(1, id);

        // Exécuter la requête DELETE et récupérer le nombre de lignes supprimées
        int rowsAffected = ps.executeUpdate();

        // Vérifier si aucune ligne n'a été supprimée
        if (rowsAffected == 0) {
            // Quitter immédiatement la méthode si aucun client ne correspond à cet ID
            return;
        }
    }
    catch (SQLException e) {
        // Afficher les détails de l'erreur SQL en cas de problème
        e.printStackTrace();
    }

    finally {
        // Fermer les ressources dans l'ordre inverse de leur création
        try {
            // Fermer le PreparedStatement s'il a été initialisé
            if (ps != null) ps.close();

            // Fermer la connexion à la base de données si elle a été établie
            if (connection != null) connection.close();
        }
        catch (SQLException e) {
            // Afficher les détails d'une éventuelle erreur lors de la fermeture
            e.printStackTrace();
        }
    }
}
    public List<Client> rechercher(String text) {

    // Créer une liste vide pour stocker les clients trouvés
    List<Client> current_List = new ArrayList<>();

    // Déclaration des variables nécessaires à la connexion
    // et à l'exécution de la requête SQL
    Connection connection = null;
    PreparedStatement ps = null;
    ResultSet rs = null;

    try {
        // Établir une connexion avec la base de données SQLite
        connection = DriverManager.getConnection("jdbc:sqlite:pharmacie.db");

        // Préparer une requête SQL pour rechercher les clients
        // dont le nom ou le prénom contient le texte recherché
        ps = connection.prepareStatement(
            "SELECT * FROM Client WHERE NOM LIKE ? OR PRENOM LIKE ?"
        );

        // Affecter le texte de recherche aux paramètres de la requête
        // Le symbole % permet de rechercher une correspondance partielle
        ps.setString(1, "%" + text + "%");
        ps.setString(2, "%" + text + "%");

        // Exécuter la requête SELECT et récupérer les résultats
        rs = ps.executeQuery();

        // Vérifier si au moins une ligne existe dans le résultat
        boolean exist = rs.next();

        // Si aucun client n'est trouvé, retourner la liste vide
        if (!exist) {
            return current_List;
        }

        // Parcourir les résultats tant qu'une ligne existe
        while (exist) {

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

            // Ajouter le client créé à la liste des résultats
            current_List.add(current_client);

            // Passer à la ligne suivante du ResultSet
            exist = rs.next();
        }

        // Retourner la liste contenant tous les clients trouvés
        return current_List;
    }
    catch (SQLException e) {
        // Afficher les détails de l'erreur SQL en cas de problème
        e.printStackTrace();

        // Retourner null pour signaler qu'une erreur SQL est survenue
        return null;
    }

    finally {
        // Fermer les ressources dans l'ordre inverse de leur création
        try {
            // Fermer le ResultSet s'il a été initialisé
            if (rs != null) rs.close();

            // Fermer le PreparedStatement s'il a été initialisé
            if (ps != null) ps.close();

            // Fermer la connexion à la base de données si elle a été établie
            if (connection != null) connection.close();
        }
        catch (SQLException e) {
            // Afficher les détails d'une éventuelle erreur lors de la fermeture
            e.printStackTrace();
        }
    }
    }


    public List<Vente> historiqueVentes(Integer clientId){
        List<Vente> current_List = new ArrayList<>();
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try{
        connection = DriverManager.getConnection("jdbc:sqlite:pharmacie.db");
        ps = connection.prepareStatement("SELECT * " + 
                        "FROM Vente " +
                        "INNER JOIN Client ON Vente.clientId = Client.id " +
                        "WHERE Client.id = ?");
        ps.setInt(1, clientId);
        rs= ps.executeQuery();
        boolean exist=rs.next();
        if(!exist){
            return current_List;
        }
        while(exist){
            LocalDateTime dateAnnulation =null;
            if(rs.getString("dateAnnulation")!=null) dateAnnulation=LocalDateTime.parse(rs.getString("dateAnnulation"));
                

            Vente current_VenteClient = new Vente(
                rs.getInt("id"),
                LocalDateTime.parse(rs.getString("dateVente")),
                rs.getBigDecimal("montantTotal"),
                StatutVente.valueOf(rs.getString("statutVente")),
                dateAnnulation,
                rs.getString("motifAnnulation")
            );
            current_List.add(current_VenteClient);
            exist=rs.next();
        }
        return current_List;
        }
        catch (SQLException e) {
        e.printStackTrace();
        return null;
        }

        finally {
            try {
                if (rs != null) rs.close();
                if (ps != null) ps.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    
    }

}