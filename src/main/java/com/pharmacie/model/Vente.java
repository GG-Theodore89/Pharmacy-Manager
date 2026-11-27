package com.pharmacie.model;

import java.time.LocalDateTime;
import java.util.List;
import java.util.ArrayList;

public class Vente {

    private int id;

    // Informations sur la vente
    private LocalDateTime date_vente;
    private BigDecimal montant_total_cents;
    private StatutVente statut;

    // Utilisateur ayant effectué la vente
    private int utilisateur_id;

    // Client facultatif
    private Integer client_id;

    // Informations d'annulation
    private Integer annule_par_id;
    private LocalDateTime date_annulation;
    private String motif_annulation;

    // Lignes composant la vente
    private List<LigneVente> lignes;

    // Constructeur pour créer une nouvelle vente
    public Vente(LocalDateTime date_vente, BigDecimal montant_total_cents,
                 int utilisateur_id, Integer client_id) {

        this.date_vente = date_vente;
        this.montant_total_cents = montant_total_cents;
        this.utilisateur_id = utilisateur_id;
        this.client_id = client_id;
        this.statut = StatutVente.VALIDEE;
        this.lignes = new ArrayList<>();
    }

    // Constructeur vide, utile notamment pour la récupération depuis la BDD
    public Vente() {
        this.lignes = new ArrayList<>();
    }

    // Getters et Setters

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDateTime getDateVente() {
        return date_vente;
    }

    public void setDateVente(LocalDateTime date_vente) {
        this.date_vente = date_vente;
    }

    public BigDecimal getMontantTotalCents() {
        return montant_total_cents;
    }

    public void setMontantTotalCents(Integer montant_total_cents) {
        this.montant_total_cents = montant_total_cents;
    }

    public StatutVente getStatut() {
        return statut;
    }

    public void setStatut(StatutVente statut) {
        this.statut = statut;
    }

    public int getUtilisateurId() {
        return utilisateur_id;
    }

    public void setUtilisateurId(int utilisateur_id) {
        this.utilisateur_id = utilisateur_id;
    }

    public Integer getClientId() {
        return client_id;
    }

    public void setClientId(Integer client_id) {
        this.client_id = client_id;
    }

    public Integer getAnnuleParId() {
        return annule_par_id;
    }

    public void setAnnuleParId(Integer annule_par_id) {
        this.annule_par_id = annule_par_id;
    }

    public LocalDateTime getDateAnnulation() {
        return date_annulation;
    }

    public void setDateAnnulation(LocalDateTime date_annulation) {
        this.date_annulation = date_annulation;
    }

    public String getMotifAnnulation() {
        return motif_annulation;
    }

    public void setMotifAnnulation(String motif_annulation) {
        this.motif_annulation = motif_annulation;
    }

    public List<LigneVente> getLignes() {
        return lignes;
    }

    public void setLignes(List<LigneVente> lignes) {
        this.lignes = lignes;
    }
}