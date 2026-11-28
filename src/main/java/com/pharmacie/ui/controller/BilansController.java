package com.pharmacie.ui.controller;

import com.pharmacie.dto.Bilan;
import com.pharmacie.model.Vente;
import com.pharmacie.service.BilanService;
import com.pharmacie.ui.AppState;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeView;

import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class BilansController {

    private final BilanService bilanService;
    private final AppState appState;

    @FXML private DatePicker debutPicker;
    @FXML private DatePicker finPicker;
    @FXML private TextField venteIdField;
    @FXML private TreeView<Object> arbreVentes;
    @FXML private Label messageLabel;
    @FXML private Label detailLabel;
    @FXML private Label caLabel;
    @FXML private Label nombreVentesLabel;
    @FXML private Label annulationsLabel;
    @FXML private Label panierMoyenLabel;

    public BilansController(BilanService bilanService, AppState appState) {
        this.bilanService = bilanService;
        this.appState = appState;
    }

    @FXML
    private void initialize() {
        debutPicker.setValue(LocalDate.now().withDayOfMonth(1));
        finPicker.setValue(LocalDate.now());

        ArbreSelection.configurer(
            arbreVentes,
            valeur -> valeur instanceof Vente vente
                ? "Vente n° " + vente.getId() + " — " + vente.getDateVente()
                : String.valueOf(valeur),
            valeur -> {
                if (valeur instanceof Vente vente) {
                    selectionnerVente(vente);
                }
            }
        );
    }

    private void selectionnerVente(Vente vente) {
        appState.setVenteSelectionnee(vente);
        venteIdField.setText(String.valueOf(vente.getId()));
        afficherDetail(vente);
        messageLabel.setText("Vente n° " + vente.getId() + " sélectionnée.");
    }

    @FXML
    private void handleHistorique() {
        LocalDate debut = UiFeedback.date(debutPicker, "La date de début");
        LocalDate fin = UiFeedback.date(finPicker, "La date de fin");
        UiFeedback.executer(
            () -> {
                List<Vente> ventes = bilanService.historique(debut, fin);
                remplirArbre(ventes);
            },
            () -> messageLabel.setText("Historique chargé.")
        );
    }

    @FXML
    private void handleDetailVente() {
        int id = UiFeedback.entier(venteIdField, "L'identifiant de la vente");
        UiFeedback.executer(
            () -> afficherDetail(bilanService.detailVente(id)),
            () -> messageLabel.setText("Détail de la vente n° " + id + " affiché.")
        );
    }

    @FXML
    private void handleGenererBilan() {
        LocalDate debut = UiFeedback.date(debutPicker, "La date de début");
        LocalDate fin = UiFeedback.date(finPicker, "La date de fin");
        UiFeedback.executer(
            () -> afficherBilan(bilanService.genererBilan(debut, fin)),
            () -> messageLabel.setText("Bilan généré.")
        );
    }

    private void remplirArbre(List<Vente> ventes) {
        Map<String, List<Vente>> groupes = ventes.stream().collect(
            Collectors.groupingBy(
                vente -> vente.getStatut() == null ? "Autres" : vente.getStatut().name(),
                LinkedHashMap::new,
                Collectors.toList()
            )
        );
        List<ArbreSelection.Categorie> categories = groupes.entrySet().stream()
            .map(groupe -> ArbreSelection.categorie(groupe.getKey(), groupe.getValue()))
            .toList();
        ArbreSelection.remplir(arbreVentes, "Ventes", categories);
    }

    private void afficherDetail(Vente vente) {
        String lignes = vente.getLignes() == null
            ? "—"
            : vente.getLignes().stream()
                .map(ligne -> "produit n° " + ligne.getProduitId()
                    + " × " + ligne.getQuantite()
                    + " à " + ligne.getPrixUnitaire().toPlainString())
                .collect(Collectors.joining(" | "));

        detailLabel.setText(
            "Vente n° " + vente.getId()
                + " — " + vente.getDateVente()
                + " — statut : " + vente.getStatut()
                + " — montant : " + vente.getMontantTotal().toPlainString()
                + " — " + (vente.getClient() == null
                    ? "client anonyme"
                    : vente.getClient().getPrenom() + " " + vente.getClient().getNom())
                + " — lignes : " + lignes
        );
    }

    private void afficherBilan(Bilan bilan) {
        caLabel.setText(bilan.getChiffreAffaires().toPlainString());
        nombreVentesLabel.setText(String.valueOf(bilan.getNombreVentes()));
        annulationsLabel.setText(String.valueOf(bilan.getNombreAnnulations()));
        panierMoyenLabel.setText(bilan.getPanierMoyen().toPlainString());
    }
}
