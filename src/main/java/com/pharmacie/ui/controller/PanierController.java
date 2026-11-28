package com.pharmacie.ui.controller;

import com.pharmacie.dto.Panier;
import com.pharmacie.exception.DonneeInvalideException;
import com.pharmacie.model.Produit;
import com.pharmacie.security.SessionManager;
import com.pharmacie.service.ProduitService;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.beans.property.ReadOnlyObjectWrapper;

import java.util.List;

public class PanierController {

    private final Panier panier;
    private final ProduitService produitService;
    private final SessionManager sessionManager;

    @FXML private TextField rechercheField;
    @FXML private ComboBox<Produit> produitsCombo;
    @FXML private TextField quantiteField;
    @FXML private TextField nouvelleQuantiteField;
    @FXML private Label utilisateurLabel;
    @FXML private Label totalLabel;
    @FXML private Label messageLabel;
    @FXML private TableView<Panier.LignePanier> panierTable;
    @FXML private TableColumn<Panier.LignePanier, String> produitCol;
    @FXML private TableColumn<Panier.LignePanier, Integer> quantiteCol;
    @FXML private TableColumn<Panier.LignePanier, Object> prixCol;
    @FXML private TableColumn<Panier.LignePanier, Object> sousTotalCol;

    public PanierController(Panier panier,
                            ProduitService produitService,
                            SessionManager sessionManager) {
        this.panier = panier;
        this.produitService = produitService;
        this.sessionManager = sessionManager;
    }

    @FXML
    private void initialize() {
        var utilisateur = sessionManager.getUtilisateurConnecte();
        utilisateurLabel.setText(utilisateur == null
            ? "Aucun utilisateur connecté."
            : "Préparateur : " + utilisateur.getPrenom() + " " + utilisateur.getNom());

        produitCol.setCellValueFactory(données ->
            new ReadOnlyObjectWrapper<>(données.getValue().produit().getNom()));
        quantiteCol.setCellValueFactory(données ->
            new ReadOnlyObjectWrapper<>(données.getValue().quantite()));
        prixCol.setCellValueFactory(données ->
            new ReadOnlyObjectWrapper<>(données.getValue().produit().getPrixVente()));
        sousTotalCol.setCellValueFactory(données ->
            new ReadOnlyObjectWrapper<>(données.getValue().produit().getPrixVente()
                .multiply(java.math.BigDecimal.valueOf(données.getValue().quantite()))));

        panierTable.getSelectionModel().selectedItemProperty().addListener(
            (observable, ancien, ligne) -> {
                if (ligne != null) {
                    nouvelleQuantiteField.setText(String.valueOf(ligne.quantite()));
                }
            }
        );
        miseAJour();
    }

    @FXML
    private void handleRechercher() {
        UiFeedback.executer(
            () -> {
                List<Produit> produits = produitService.rechercher(
                    UiFeedback.texte(rechercheField)
                );
                produitsCombo.setItems(FXCollections.observableArrayList(produits));
                produitsCombo.getSelectionModel().selectFirst();
            },
            () -> messageLabel.setText("Produits disponibles chargés.")
        );
    }

    @FXML
    private void handleAjouter() {
        Produit produit = produitsCombo.getValue();
        if (produit == null) {
            UiFeedback.erreur(new DonneeInvalideException(
                "Sélectionnez un produit dans la liste."
            ));
            return;
        }

        int quantite = UiFeedback.entier(quantiteField, "La quantité");
        UiFeedback.executer(
            () -> panier.ajouterProduit(produit, quantite),
            () -> {
                messageLabel.setText(quantite + " × " + produit.getNom() + " ajouté(s).");
                miseAJour();
            }
        );
    }

    @FXML
    private void handleModifierQuantite() {
        Panier.LignePanier ligne = panierTable.getSelectionModel().getSelectedItem();
        if (ligne == null) {
            UiFeedback.erreur(new DonneeInvalideException(
                "Sélectionnez une ligne du panier."
            ));
            return;
        }

        int quantite = UiFeedback.entier(nouvelleQuantiteField, "La quantité");
        UiFeedback.executer(
            () -> panier.modifierQuantite(ligne.produit().getId(), quantite),
            () -> {
                messageLabel.setText("Quantité mise à jour.");
                miseAJour();
            }
        );
    }

    @FXML
    private void handleRetirer() {
        Panier.LignePanier ligne = panierTable.getSelectionModel().getSelectedItem();
        if (ligne == null) {
            UiFeedback.erreur(new DonneeInvalideException(
                "Sélectionnez une ligne du panier."
            ));
            return;
        }

        UiFeedback.executer(
            () -> panier.retirerProduit(ligne.produit().getId()),
            () -> {
                messageLabel.setText("Produit retiré du panier.");
                miseAJour();
            }
        );
    }

    @FXML
    private void handleVider() {
        UiFeedback.executer(
            panier::vider,
            () -> {
                messageLabel.setText("Panier vidé.");
                miseAJour();
            }
        );
    }

    private void miseAJour() {
        panierTable.setItems(FXCollections.observableArrayList(panier.getLignes()));
        totalLabel.setText(panier.calculerTotal().toPlainString());
    }
}
