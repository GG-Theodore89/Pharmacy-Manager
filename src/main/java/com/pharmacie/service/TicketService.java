package com.pharmacie.service;

import com.pharmacie.model.Ticket;

public interface TicketService {

    // Créer et enregistrer un ticket, puis générer son PDF
    Ticket genererTicket(int vente_id);

    // Régénérer le PDF d'un ticket existant
    void reimprimerTicket(int vente_id);
}