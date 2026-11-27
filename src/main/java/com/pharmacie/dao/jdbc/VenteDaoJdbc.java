package com.pharmacie.dao.impl;

import com.pharmacie.dao.VenteDAO;
import com.pharmacie.model.Vente;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.time.LocalDate;

public class VenteDAOImpl implements VenteDAO {

    //objectif de la fonction : on veut enregistrer une vente dans la tab;e VENTE de la bdd
    //elle doit prendre un objet Vente contenant ces informations et les enregistrer dans la bdd 
    @Override
    public int ajouter(Vente vente) {
        //je prepare la requete SQL en java 
        //on stocke la requete dans une variable 
        String sql="INSERT INTO VENTE " +"(date_vente,montant_total_cents,statut,utilisateur_id,client_id)" + "VALUES (?,?,?,?,?)";

        try(Connection conn = DBConnection.getConnection(); //on recupere une connexion a la bdd, pour communiquer a la bdd
        PreparedStatement stmt = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)){ //on  demande a java de preparer notre requete sql , on va remplacer les ? par les vrai valeur
        //pour leur attribuer des valeurs on utilise les methodes set et preparedstatement
        stmt.setTimestamp(1, java.sql.Timestamp.valueOf(vente.getDateVente())); //stmt : notre requete preparee , setInt : on affecte une valeur entiere , 2: le deuxieme ? de la requete , vente.getMontangttotalcents() : on recupere le montant depuis notre objet vente 
        stmt.setInt(2, vente.getMontantTotalCents());
        stmt.setInt(3, vente.getStatut().ordinal()); // ordinal transforme la position de l element dans l enum en entier 
        stmt.setInt(4, vente.getUtilisateur_id());

        //dans notre app une vente peut etre realisee sans enregistrer de client , donc si il existe on utilise setInt , sinon on utilise setNull pour enregistrer une requete NULL
        if (vente.getClient_id() != null) {
            stmt.setInt(5, vente.getClient_id());
        } else {
            stmt.setNull(5, java.sql.Types.INTEGER);
        }

        stmt.executeUpdate(); //demander a la bdd d executer notre INSERT , et retourne aussi le nbr de lignes affectees 

        //
        try (ResultSet rs = stmt.getGeneratedKeys()) { //getGeneratedkey permet de recuperer les cles generees par la bdd, donc on stocke dans un tableau de resultats , 
            if (rs.next()) { //verifie qu un resultat existe 
                return rs.getInt(1); //recuperer la premiere colonne de ResultSetok, ici c est l identifiant 
            }
        }

    } catch (SQLException e) { //on peut iontercepter les exceptions SQL , et e est la variable qui contient les informations sur l erreur,
        throw new RuntimeException("Erreur lors de l'enregistrement de la vente", e);
    }

        return -1;
    }

    //objectif de la fct : recuperer une vente depuis la bdd a partir de son identifiant
    @Override
    public Vente trouverParId(int vente_id) {

        String sql = "SELECT * FROM VENTE WHERE id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            //transmettre l identifiant recherche
            stmt.setInt(1, vente_id); //remplace le premier ? par la valeur de vente_id

            //executer la requete
            try (ResultSet rs = stmt.executeQuery()) { //executeQuery execute SELECT et retourne ResultSet

                //verifie si la vente existe
                if (rs.next()) {

                    //recuperer les donnees depuis la BDD
                    int id = rs.getInt("id");
                    LocalDateTime date_vente = rs.getTimestamp("date_vente").toLocalDateTime();
                    int montant_total_cents = rs.getInt("montant_total_cents");
                    StatutVente statut = StatutVente.values()[rs.getInt("statut")];
                    int utilisateur_id = rs.getInt("utilisateur_id");
                    int clientValeur = rs.getInt("client_id");
                    Integer client_id = rs.wasNull() ? null : clientValeur;

                    //construire et retourner l objet Vente
                    Vente vente = new Vente(id, date_vente, montant_total_cents, statut, utilisateur_id, client_id);
                    return vente;
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la recherche de la vente", e);
        }

        return null;
    }

// Pour une méthode DAO qui exécute une requête SQL, on suit généralement ces étapes :
//1. Préparer la requête SQL dans une variable sql.
//2. Ouvrir la connexion à la base de données avec Connection.
//3. Préparer la requête avec PreparedStatement.
//4. Transmettre les paramètres reçus par la méthode avec setInt(), setString(), etc.
//5. Exécuter la requête :
//   - executeQuery() pour SELECT.
//   - executeUpdate() pour INSERT, UPDATE et DELETE.
//6. Traiter le résultat si nécessaire (ResultSet pour un SELECT).
//7. Gérer les erreurs avec catch.


}