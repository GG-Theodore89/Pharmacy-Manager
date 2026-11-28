package com.pharmacie.dto;

public class ClientForm {

    private final String nom;
    private final String prenom;
    private final String adresse;
    private final String telephone;
    private final String email;
    private final String ordonance;
    private final boolean actif;

    public ClientForm(String nom, String prenom, String adresse,
                      String telephone, String email, String ordonance,
                      boolean actif) {
        this.nom = nom;
        this.prenom = prenom;
        this.adresse = adresse;
        this.telephone = telephone;
        this.email = email;
        this.ordonance = ordonance;
        this.actif = actif;
    }

    public ClientForm(String nom, String prenom, String adresse) {
        this(nom, prenom, adresse, null, null, null, true);
    }

    public String getNom() {
        return nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public String getAdresse() {
        return adresse;
    }

    public String getTelephone() {
        return telephone;
    }

    public String getEmail() {
        return email;
    }

    public String getOrdonance() {
        return ordonance;
    }

    public boolean isActif() {
        return actif;
    }
}
