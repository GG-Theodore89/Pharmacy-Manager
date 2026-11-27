package com.pharmacie.service.simule;

import java.util.List;

import com.pharmacie.dto.ClientForm;
import com.pharmacie.model.Client;
import com.pharmacie.model.Vente;
import com.pharmacie.service.*;

public class ClientServiceSimule implements ClientService {
    public Client ajouter(ClientForm f){return null;}
    public Client modifier(Integer id,ClientForm f){return null;};
    public void desactiver(Integer id){return;}
    public void supprimer(Integer id){return; }
    public List<Client> rechercher(String text){return null;}
    public List<Vente> historiqueVentes(Integer clientId){return null;}
}
