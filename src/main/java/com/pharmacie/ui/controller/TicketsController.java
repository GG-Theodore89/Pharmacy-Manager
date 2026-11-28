package com.pharmacie.ui.controller;

import com.pharmacie.dto.Ticket;
import com.pharmacie.exception.DonneeInvalideException;
import com.pharmacie.model.Vente;
import com.pharmacie.service.TicketService;
import com.pharmacie.ui.AppState;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class TicketsController {

    private final TicketService ticketService;
    private final AppState appState;

    @FXML private Label venteLabel;
    @FXML private Label messageLabel;
    @FXML private Label numeroLabel;
    @FXML private Label dateLabel;
    @FXML private Label lignesLabel;
    @FXML private Label totalLabel;

    public TicketsController(TicketService ticketService, AppState appState) {
        this.ticketService = ticketService;
        this.appState = appState;
    }

    @FXML
    private void initialize() {
        Vente vente = venteSelectionnee();
        venteLabel.setText(vente == null
            ? "Aucune vente sélectionnée. Validez ou sélectionnez une vente."
            : "Vente n° " + vente.getId() + " — " + vente.getStatut()
              + " — " + vente.getMontantTotal().toPlainString());
    }

    @FXML
    private void handleGenererTicket() {
        Vente vente = venteSelectionnee();
        if (vente == null) {
            UiFeedback.erreur(new DonneeInvalideException(
                "Aucune vente sélectionnée. Validez ou sélectionnez une vente."
            ));
            return;
        }

        UiFeedback.executer(
            () -> afficher(ticketService.genererTicket(vente)),
            () -> messageLabel.setText("Ticket généré pour la vente n° " + vente.getId() + ".")
        );
    }

    private Vente venteSelectionnee() {
        return appState.getVenteSelectionnee() != null
            ? appState.getVenteSelectionnee()
            : appState.getDerniereVente();
    }

    private void afficher(Ticket ticket) {
        numeroLabel.setText(String.valueOf(ticket.getVenteId()));
        dateLabel.setText(String.valueOf(ticket.getDate()));
        lignesLabel.setText(ticket.getLignes());
        totalLabel.setText(ticket.getTotal().toPlainString());
    }
}
