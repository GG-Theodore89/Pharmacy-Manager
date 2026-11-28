package com.pharmacie.service;

import com.pharmacie.dto.Ticket;
import com.pharmacie.model.Vente;

public interface TicketService {

    Ticket genererTicket(Vente v);
}
