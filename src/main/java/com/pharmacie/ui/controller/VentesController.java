package com.pharmacie.ui.controller;

import com.pharmacie.dto.Panier;
import com.pharmacie.dto.Ticket;
import com.pharmacie.exception.DonneeInvalideException;
import com.pharmacie.model.Client;
import com.pharmacie.model.Utilisateur;
import com.pharmacie.model.Vente;
import com.pharmacie.security.SessionManager;
import com.pharmacie.service.BilanService;
import com.pharmacie.service.ClientService;
import com.pharmacie.service.TicketService;
import com.pharmacie.service.VenteService;
import com.pharmacie.ui.AppState;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeView;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

public class VentesController {

    private final VenteService venteService;
    private final TicketService ticketService;
    private final ClientService clientService;
    private final BilanService bilanService;
    private final AppState appState;
    private final SessionManager sessionManager;

    @FXML private Label utilisateurLabel;
    @FXML private Label panierResumeLabel;
    @FXML private Label montantLabel;
    @FXML private Label resultatLabel;
    @FXML private Label ticketLabel;
    @FXML private TextField rechercheField;
    @FXML private TreeView<Object> arbreSelection;
    @FXML private TextField clientIdField;
    @FXML private TextField venteIdField;
    @FXML private TextField motifField;

    public VentesController(VenteService venteService,
                            TicketService ticketService,
                            ClientService clientService,
                            BilanService bilanService,
                            AppState appState,
                            SessionManager sessionManager) {
        this.venteService = venteService;
        this.ticketService = ticketService;
        this.clientService = clientService;
        this.bilanService = bilanService;
        this.appState = appState;
        this.sessionManager = sessionManager;
    }

    @FXML
    private void initialize() {
        Utilisateur utilisateur = sessionManager.getUtilisateurConnecte();
        utilisateurLabel.setText(utilisateur == null
            ? "Aucun utilisateur connecté."
            : "Vendeur : " + utilisateur.getPrenom() + " " + utilisateur.getNom());
        ticketLabel.setText("Aucun ticket généré.");
        actualiserPanier();

        ArbreSelection.configurer(
            arbreSelection,
            valeur -> valeur instanceof Vente vente
                ? "Vente n° " + vente.getId() + " — " + vente.getMontantTotal().toPlainString()
                : valeur instanceof Client client
                  ? client.getPrenom() + " " + client.getNom()
                  : String.valueOf(valeur),
            this::selectionner
        );
    }

    @FXML
    private void handleRechercher() {
        String texte = UiFeedback.texte(rechercheField);
        LocalDate fin = LocalDate.now();
        LocalDate debut = fin.minusDays(30);
        UiFeedback.executer(
            () -> {
                List<Vente> ventes = bilanService.historique(debut, fin);
                List<Client> clients = clientService.rechercher(texte);
                ArbreSelection.remplir(
                    arbreSelection,
                    "Résultats",
                    List.of(
                        ArbreSelection.categorie("Ventes récentes", ventes),
                        ArbreSelection.categorie("Clients", clients)
                    )
                );
            },
            () -> resultatLabel.setText("Recherche terminée.")
        );
    }

    private void selectionner(Object valeur) {
        if (valeur instanceof Vente vente) {
            venteIdField.setText(String.valueOf(vente.getId()));
            appState.setVenteSelectionnee(vente);
            resultatLabel.setText("Vente n° " + vente.getId() + " sélectionnée pour annulation.");
        } else if (valeur instanceof Client client) {
            clientIdField.setText(String.valueOf(client.getId()));
            resultatLabel.setText("Client " + client.getPrenom() + " " + client.getNom()
                + " sélectionné pour la vente.");
        }
    }

    @FXML
    private void handleActualiserPanier() {
        actualiserPanier();
        resultatLabel.setText("Panier actualisé.");
    }

    @FXML
    private void handleValiderVente() {
        Utilisateur utilisateur = sessionManager.getUtilisateurConnecte();
        if (utilisateur == null) {
            UiFeedback.erreur(new DonneeInvalideException(
                "Connectez-vous avant de valider une vente."
            ));
            return;
        }
        if (appState.getPanier().estVide()) {
            UiFeedback.erreur(new DonneeInvalideException("Le panier est vide."));
            return;
        }

        Integer clientId = UiFeedback.entierOptionnel(clientIdField);
        Panier panier = appState.getPanier();
        UiFeedback.executer(
            () -> {
                Vente vente = venteService.validerVente(panier, utilisateur.getId(), clientId);
                appState.setDerniereVente(vente);
                appState.setVenteSelectionnee(vente);
                Ticket ticket = ticketService.genererTicket(vente);
                ticketLabel.setText(formatTicket(ticket));
            },
            () -> {
                panier.vider();
                resultatLabel.setText("Vente validée. Le panier a été vidé.");
                actualiserPanier();
            }
        );
    }

    @FXML
    private void handleAnnulerVente() {
        Utilisateur utilisateur = sessionManager.getUtilisateurConnecte();
        if (utilisateur == null) {
            UiFeedback.erreur(new DonneeInvalideException(
                "Connectez-vous avant d'annuler une vente."
            ));
            return;
        }

        int venteId = UiFeedback.entier(venteIdField, "L'identifiant de la vente");
        String motif = UiFeedback.texte(motifField);
        UiFeedback.executer(
            () -> venteService.annulerVente(venteId, utilisateur.getId(), motif),
            () -> resultatLabel.setText("Vente n° " + venteId + " annulée.")
        );
    }

    @FXML
    private void handleGenererTicket() {
        Vente vente = appState.getDerniereVente();
        if (vente == null) {
            UiFeedback.erreur(new DonneeInvalideException(
                "Validez d'abord une vente pour générer un ticket."
            ));
            return;
        }

        UiFeedback.executer(
            () -> ticketLabel.setText(formatTicket(ticketService.genererTicket(vente))),
            () -> resultatLabel.setText("Ticket généré.")
        );
    }

    private void actualiserPanier() {
        Panier panier = appState.getPanier();
        panierResumeLabel.setText(panier.getLignes().stream()
            .map(ligne -> ligne.produit().getNom() + " × " + ligne.quantite())
            .collect(Collectors.joining(" | ", "[ ", " ]")));
        montantLabel.setText(panier.calculerTotal().toPlainString());
    }

    private String formatTicket(Ticket ticket) {
        return "Ticket n° " + ticket.getVenteId()
            + " — " + ticket.getDate()
            + " — " + ticket.getLignes()
            + " — total : " + ticket.getTotal().toPlainString();
    }
}
