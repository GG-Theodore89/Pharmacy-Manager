package com.pharmacie.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Ticket {

    private final int venteId;
    private final LocalDateTime date;
    private final String lignes;
    private final BigDecimal total;

    public Ticket(int venteId, LocalDateTime date, String lignes, BigDecimal total) {
        this.venteId = venteId;
        this.date = date;
        this.lignes = lignes;
        this.total = total;
    }

    public int getVenteId() {
        return venteId;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public String getLignes() {
        return lignes;
    }

    public BigDecimal getTotal() {
        return total;
    }

    @Override
    public String toString() {
        return "Ticket #" + venteId + " — " + total;
    }
}
