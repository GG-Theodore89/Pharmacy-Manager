package com.pharmacie.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Vente {
    private Integer id;
    private LocalDateTime dateVente;
    private BigDecimal montantTotal;
    private StatutVente statut;
    private LocalDateTime dateAnnulation;
    private String motifAnnulation;

    // Constructeur sans ID (nouvelle vente)
    public Vente(LocalDateTime dateVente, BigDecimal montantTotal,
                 StatutVente statut, LocalDateTime dateAnnulation,
                 String motifAnnulation) {

        this.dateVente = dateVente;
        this.montantTotal = montantTotal;
        this.statut = statut;
        this.dateAnnulation = dateAnnulation;
        this.motifAnnulation = motifAnnulation;
    }

    // Constructeur avec ID (vente existante)
    public Vente(Integer id, LocalDateTime dateVente, BigDecimal montantTotal,
                 StatutVente statut, LocalDateTime dateAnnulation,
                 String motifAnnulation) {

        this.id = id;
        this.dateVente = dateVente;
        this.montantTotal = montantTotal;
        this.statut = statut;
        this.dateAnnulation = dateAnnulation;
        this.motifAnnulation = motifAnnulation;
    }
}
