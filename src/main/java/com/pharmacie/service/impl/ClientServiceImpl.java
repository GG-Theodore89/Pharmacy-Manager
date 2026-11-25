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

    public Client ajouter(ClientForm f){
        Client current_client= new Client(f.getNom(),f.getPrenom(),f.getAdresse(), f.getTelephone(), f.getEmail(),f.isActif(),f.getOrdonence());
        return current_client;
    }

    public Client modifier(Integer id,ClientForm f){
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
            return null;
        }
        Client current_client= new Client(rs.getInt("id"),rs.getString("nom"),rs.getString("prenom"),rs.getString("adresse"),rs.getString("telephone"),rs.getString("email"),rs.getBoolean("actif"),rs.getString("Ordonence"));
        current_client.setNom(f.getNom());
        current_client.setPrenom(f.getPrenom());
        current_client.setAdresse(f.getAdresse());
        current_client.setTelephone(f.getTelephone());
        current_client.setEmail(f.getEmail());
        current_client.setActif(f.isActif());
        current_client.setOrdonence(f.getOrdonence());

        psn= connection.prepareStatement("UPDATE Client SET nom= ?,prenom= ?,adresse= ?,telephone= ?,email= ?,actif= ?,Ordonence= ? WHERE id= ?");
        psn.setString(1,current_client.getNom());
        psn.setString(2,current_client.getPrenom());
        psn.setString(3,current_client.getAdresse());
        psn.setString(4,current_client.getTelephone());
        psn.setString(5,current_client.getEmail());
        psn.setBoolean(6,current_client.isActif());
        psn.setString(7,current_client.getOrdonence());
        psn.setInt(8, id);

        int rowsAffected = psn.executeUpdate();
        if(rowsAffected==0){
            return null;
        }
        return current_client;
        }
        catch (SQLException e) {
        e.printStackTrace();
        return null;
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