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
}
