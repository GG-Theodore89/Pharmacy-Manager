package com.pharmacie.dao;
import com.pharmacie.model.Client;

public interface ClientDao {
    public Boolean insert(Client client);
    public Client findById(Integer id);
    public Boolean update(Client client);
}
