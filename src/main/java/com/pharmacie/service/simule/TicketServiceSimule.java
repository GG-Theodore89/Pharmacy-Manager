package com.pharmacie.service.simule;

import com.pharmacie.service.*;
import com.pharmacie.dto.Ticket;
import com.pharmacie.exception.PharmacieException;
import com.pharmacie.model.Vente;

public class TicketServiceSimule implements TicketService {

    @Override
    public Ticket genererTicket(Vente v) {
        throw new PharmacieException("Le service TicketServiceSimule n'est pas encore implémenté.");
    }
}
