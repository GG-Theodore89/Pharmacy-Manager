package com.pharmacie.service;

import com.pharmacie.dto.ClientForm;
import com.pharmacie.model.Client;
import com.pharmacie.model.Vente;
import java.util.List;

public interface ClientService {

    Client ajouter(ClientForm f);

    Client modifier(int id, ClientForm f);

    void desactiver(int id);

    void supprimer(int id);

    List<Client> rechercher(String texte);

    List<Vente> historiqueVentes(int clientId);
}
