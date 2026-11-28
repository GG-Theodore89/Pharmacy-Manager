package com.pharmacie.service.impl;

import com.pharmacie.service.*;
import com.pharmacie.dto.Ticket;
import com.pharmacie.exception.PharmacieException;
import com.pharmacie.model.Vente;

public class TicketServiceImpl implements TicketService {

    @Override
    public Ticket genererTicket(Vente v) {
        throw new PharmacieException("Le service TicketServiceImpl n'est pas encore implémenté.");
    }
}
