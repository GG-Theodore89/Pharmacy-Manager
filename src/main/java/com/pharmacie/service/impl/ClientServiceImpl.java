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
import com.pharmacie.exception.EchecOperationException;
import com.pharmacie.exception.EntiteIntrouvableException;
import com.pharmacie.model.Client;
import com.pharmacie.model.StatutVente;
import com.pharmacie.model.Vente;
import com.pharmacie.service.*;
import com.pharmacie.dao.jdbc.ClientDaoJdbc;
import com.pharmacie.dao.ClientDao;

public class ClientServiceImpl implements ClientService {

    private final ClientDao clientDao;
    public ClientServiceImpl(ClientDao clientDao){
        this.clientDao=clientDao;
    }
    // Ajouter un nouveau client à partir des données du formulaire
    @Override 
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
    Boolean effectuer= clientDao.enregister(current_client);
    if(!effectuer) return null;
    return current_client;
    
}



    // Modifier les informations d'un client existant à partir de son ID

        @Override
        public Client modifier(Integer id, ClientForm f) {

            // Rechercher le client dans la base de données
            Client currentClient = clientDao.trouverParId(id);

            // Vérifier si le client existe
            if (currentClient == null) {
                throw new EntiteIntrouvableException("L'identifiant du client est introuvable.");
            }

            // Mettre à jour les informations du client
            currentClient.setNom(f.getNom());
            currentClient.setPrenom(f.getPrenom());
            currentClient.setAdresse(f.getAdresse());
            currentClient.setTelephone(f.getTelephone());
            currentClient.setEmail(f.getEmail());
            currentClient.setActif(f.isActif());
            currentClient.setOrdonence(f.getOrdonence());

            // Enregistrer les modifications dans la base de données
            boolean effectuer = clientDao.mettreAJour(id, currentClient);

            if (!effectuer) {
                throw new EchecOperationException(
            "La modification du client a échoué.");
            }

            return currentClient;
        }


        public void desactiver(Integer id) {
            Client currentClient = clientDao.trouverParId(id);
            if (currentClient == null) {
                throw new EntiteIntrouvableException("L'identifiant du client est introuvable.");
            }
            currentClient.setActif(false);
            boolean effectuer = clientDao.mettreAJour(id, currentClient);

            if (!effectuer) {
                throw new EchecOperationException(
            "La desactivation du client a échoué.");
            }
        }
    
        public void supprimer(Integer id) {

        // Vérifier si le client existe
        Client currentClient = clientDao.trouverParId(id);

        if (currentClient == null) {
            throw new EntiteIntrouvableException(
                "L'identifiant du client est introuvable."
            );
        }

        // Supprimer le client
        boolean effectuer = clientDao.supprimer(id);

        if (!effectuer) {
            throw new EchecOperationException(
                "La suppression du client a échoué."
            );
        }
    }


    public List<Client> rechercher(String text) {

        return clientDao.rechercher(text);
    }


    
public List<Vente> historiqueVentes(Integer clientId){
    return clientDao.aDesVentes(clientId);
}


}