package com.pharmacie.service;
import com.pharmacie.model.*;
import com.pharmacie.dto.ClientForm;
import java.util.ArrayList;
import java.util.List;

public interface ClientService {
    public Client ajouter(ClientForm f);
    public Client modifier(Integer id,ClientForm f);
    /*public void desactiver(Integer id);
    public void supprimer(Integer id);
    public List<Client> rechercher(String text);
    public List<Vente> historiqueVentes(Integer clientId);*/
}
