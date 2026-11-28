package com.pharmacie.dto;

public class NouvelUtilisateur {
    private String nom;
    private String prenom;
    private String adresse;
    private String email;
    private String motDePasse;
    private boolean actif;

    public NouvelUtilisateur(String nom, String prenom, String adresse,
                             String email, String motDePasse, boolean actif) {
        this.nom = nom;
        this.prenom = prenom;
        this.adresse = adresse;
        this.email = email;
        this.motDePasse = motDePasse;
        this.actif = actif;
    }

    public NouvelUtilisateur(String nom, String prenom, String adresse,
                             String email, String motDePasse) {
        this(nom, prenom, adresse, email, motDePasse, true);
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

    public String getEmail() {
        return email;
    }

    public String getMotDePasse() {
        return motDePasse;
    }

    public boolean isActif() {
        return actif;
    }
}
