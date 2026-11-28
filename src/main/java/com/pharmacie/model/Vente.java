package com.pharmacie.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public class Vente {

    private int id;
    private LocalDateTime dateVente;
    private BigDecimal montantTotal;
    private StatutVente statut;
    private LocalDateTime dateAnnulation;
    private String motifAnnulation;

    private Utilisateur utilisateur;
    private Client client;
    private List<LigneVente> lignes;

    public Vente(int id, LocalDateTime dateVente,
                 BigDecimal montantTotal, StatutVente statut,
                 LocalDateTime dateAnnulation, String motifAnnulation,
                 Utilisateur utilisateur, Client client,
                 List<LigneVente> lignes) {

        this.id = id;
        this.dateVente = dateVente;
        this.montantTotal = montantTotal;
        this.statut = statut;
        this.dateAnnulation = dateAnnulation;
        this.motifAnnulation = motifAnnulation;
        this.utilisateur = utilisateur;
        this.client = client;
        this.lignes = lignes;
    }

    public int getId() {
        return id;
    }

    public LocalDateTime getDateVente() {
        return dateVente;
    }

    public StatutVente getStatut() {
        return statut;
    }

    public LocalDateTime getDateAnnulation() {
        return dateAnnulation;
    }

    public String getMotifAnnulation() {
        return motifAnnulation;
    }

    public Utilisateur getUtilisateur() {
        return utilisateur;
    }

    public Client getClient() {
        return client;
    }

    public List<LigneVente> getLignes() {
        return lignes;
    }

    public BigDecimal getMontantTotal() {
        return montantTotal;
    }
}
