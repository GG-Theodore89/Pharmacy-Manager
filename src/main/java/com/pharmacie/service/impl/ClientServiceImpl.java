package com.pharmacie.service.impl;

import com.pharmacie.service.*;
import com.pharmacie.dto.ClientForm;
import com.pharmacie.exception.PharmacieException;
import com.pharmacie.model.Client;
import com.pharmacie.model.Vente;
import java.util.List;

public class ClientServiceImpl implements ClientService {

    @Override
    public Client ajouter(ClientForm f) {
        throw new PharmacieException("Le service ClientServiceImpl n'est pas encore implémenté.");
    }

    @Override
    public Client modifier(int id, ClientForm f) {
        throw new PharmacieException("Le service ClientServiceImpl n'est pas encore implémenté.");
    }

    @Override
    public void desactiver(int id) {
        throw new PharmacieException("Le service ClientServiceImpl n'est pas encore implémenté.");
    }

    @Override
    public void supprimer(int id) {
        throw new PharmacieException("Le service ClientServiceImpl n'est pas encore implémenté.");
    }

    @Override
    public List<Client> rechercher(String texte) {
        throw new PharmacieException("Le service ClientServiceImpl n'est pas encore implémenté.");
    }

    @Override
    public List<Vente> historiqueVentes(int clientId) {
        throw new PharmacieException("Le service ClientServiceImpl n'est pas encore implémenté.");
    }
}
