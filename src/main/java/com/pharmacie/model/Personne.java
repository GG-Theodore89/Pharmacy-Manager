package com.pharmacie.model;

public abstract class Personne {
    protected int id;
    protected String nom
    protected String prenom
    protected String adresse

    public Personne(int id, String nom, String prenom, String adresse){
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.adresse = adresse;
    }
}
