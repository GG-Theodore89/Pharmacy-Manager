package com.pharmacie.model;
import java.math.BigDecimal;

public abstract class Produit {
    private int id;
    private String nom;
    private String description;
    private BigDecimal prixVente;
    private int seuilAlerte;
    private boolean actif;

    //Constructeur sans ID pour construction d'un nouveau produit
    protected Produit(String nom, String description, BigDecimal prixVente, int seuilAlerte){
        setNom(nom);
        setDescription(description);
        setPrixVente(prixVente);
        setSeuilAlerte(seuilAlerte);
        this.actif = true;
    }

    //Constructeur avec ID pour reconstruire un produit existant depuis la base de données
    protected Produit(int id, String nom, String description, BigDecimal prixVente, int seuilAlerte, boolean actif){
        this(nom,description,prixVente,seuilAlerte);
        if (id <= 0){
            throw new IllegalArgumentException("Id doit etre strictement positif");
        }
        this.id = id;
        this.actif = actif;
    }

    //Getters
    public int getId() {
        return id;
    }
    public String getNom(){
        return nom;
    }
    public String getDescription(){
        return description;
    }
    public BigDecimal getPrixVente(){
        return prixVente;
    }
    public int getSeuilAlerte(){
        return seuilAlerte;
    }
    public boolean isActif(){
        return actif;
    }

    //Setters (Les setters sont final pour empêcher leur redéfinition dans les classes filles. Comme ils sont appelés dans le constructeur de Produit, cela évite d'exécuter des méthodes redéfinies avant l'initialisation complète des objets Medicament ou Parapharmacie.).
    final public void setNom(String nom){
        if (nom == null || nom.isBlank()){
            throw new IllegalArgumentException("Le nom du produit est obligatoire");
        }
        this.nom=nom.trim();
    }
    final public void setDescription(String description){
        //Si il y a pas de description on l'accepte sinon on supprime les espaces inutiles lorsqu'une description est fournie
        this.description = description == null ? null : description.trim();
    }
    final public void setPrixVente(BigDecimal prixVente){
        if (prixVente == null || prixVente.compareTo(BigDecimal.ZERO)<0){
            throw new IllegalArgumentException("Le prix de vente ne peut pas être null ou négatif");
        }
        this.prixVente = prixVente;
    }
    final public void setSeuilAlerte(int seuilAlerte){
        if (seuilAlerte < 0) {
            throw new IllegalArgumentException("Le seuil d'alerte ne peut pas être négatif");
        }
        this.seuilAlerte = seuilAlerte;
    }
    final public void setActif(boolean actif) {
        this.actif = actif;
    }
}
