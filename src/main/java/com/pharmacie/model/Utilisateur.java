package com.pharmacie.model;

import java.util.List;

public class Utilisateur extends Personne {

    private String email;
    private String motDePasseHash;
    private boolean actif;

    private List<Vente> ventes; // Could be removed and satisfy just with DB joins

    public Utilisateur(int id, String nom, String prenom, String adresse,
                       String email, String motDePasseHash, boolean actif) {

        super(id, nom, prenom, adresse);

        this.email = email;
        this.motDePasseHash = motDePasseHash;
        this.actif = actif;
    }

    public String getEmail() {
        return email;
    }

    public String getMotDePasseHash() {
        return motDePasseHash;
    }

    public boolean isActif() {
        return actif;
    }
}