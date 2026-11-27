package com.pharmacie.dao;
import java.util.List;

import com.pharmacie.model.Client;
import com.pharmacie.model.Vente;

public interface ClientDao {
    public Boolean enregister(Client client);
    public Client trouverParId(Integer id);
    public Boolean mettreAJour(Integer id,Client client);
    public Boolean supprimer(Integer id);
    public List<Client> rechercher(String text);
    public List<Vente> aDesVentes(int clientId);
}
