package com.pharmacie.model;
import lombok.Data;

@Data 
public abstract class Personne {
    private Integer id;
    private String nom;
    private String prenom;
    private String adresse;
    public Personne(Integer id,String nom,String prenom,String adresse){
        this.id=id;
        this.nom=nom;
        this.prenom=prenom;
        this.adresse=adresse;
    }

    public Personne(String nom,String prenom,String adresse){
        this.nom=nom;
        this.prenom=prenom;
        this.adresse=adresse;
    }
}
