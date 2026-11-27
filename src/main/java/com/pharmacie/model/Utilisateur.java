package com.pharmacie.model;

import java.util.List;

public class Utilisateur extends Personne {

    private String email;
    private String motDePasseHash;
    pri     vate boolean actif;

    private List<Vente> ventes; // Could be removed and satisfy just with DB joins

    public Utilisateur(int id, String nom, String prenom, String adresse,
                       String email, String motDePasseHash, boolean actif) {

        super(id, nom, prenom, adresse);

        this.email = email;
        this.motDePasseHash = motDePasseHash;
        this.actif = actif;
    }
}