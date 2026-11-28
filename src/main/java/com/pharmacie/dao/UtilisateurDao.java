package com.pharmacie.dao;

import com.pharmacie.model.Utilisateur;

public interface UtilisateurDao {

    void enregistrer(Utilisateur utilisateur);

    Utilisateur trouverParEmail(String email);
}