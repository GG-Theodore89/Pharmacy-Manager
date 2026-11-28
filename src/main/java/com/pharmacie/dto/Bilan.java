package com.pharmacie.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Bilan {
    private LocalDate dateDebut;
    private LocalDate dateFin;

    private BigDecimal chiffreAffaires; // Revenue from validated sales
    private int nombreVentes; // Number of validated sales
    private int nombreAnnulations; // Number of cancelled sales
    private BigDecimal panierMoyen; // Average value per validated sale

    public Bilan(LocalDate dateDebut, LocalDate dateFin,
                 BigDecimal chiffreAffaires,
                 int nombreVentes, int nombreAnnulations,
                 BigDecimal panierMoyen) {

        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
        this.chiffreAffaires = chiffreAffaires;
        this.nombreVentes = nombreVentes;
        this.nombreAnnulations = nombreAnnulations;
        this.panierMoyen = panierMoyen;
    }

    public LocalDate getDateDebut() {
        return dateDebut;
    }

    public LocalDate getDateFin() {
        return dateFin;
    }

    public BigDecimal getChiffreAffaires() {
        return chiffreAffaires;
    }

    public int getNombreVentes() {
        return nombreVentes;
    }

    public int getNombreAnnulations() {
        return nombreAnnulations;
    }

    public BigDecimal getPanierMoyen() {
        return panierMoyen;
    }
}
