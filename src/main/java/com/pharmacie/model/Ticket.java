package com.pharmacie.model;

import java.time.LocalDateTime;

public class Ticket {

    private int id; //identifiant technique
    private String numero; //numero visible unique sur le ticket 
    private int vente_id; //identifiant vente associe 
    //private LocalDateTime dateGeneration; dategeneration initiale , je supprime cet attribut pour eviter une information redondante car j ai deja dateVente dans Vente , le ticket generer immediatement apres la validation de la vente 

    // Constructeur vide, utile notamment pour la récupération depuis la BDD.
    public Ticket() {
    }

    // Constructeur pour créer un ticket associé à une vente.
    public Ticket(String numero, int vente_id) {
        this.numero = numero;
        this.vente_id = vente_id;
    }

    // Getters et Setters

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public int getVenteId() {
        return vente_id;
    }

    public void setVenteId(int vente_id) {
        this.vente_idd = vente_id;
    }
}