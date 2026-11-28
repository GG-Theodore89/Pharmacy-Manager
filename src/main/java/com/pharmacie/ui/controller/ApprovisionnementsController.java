package com.pharmacie.ui.controller;

import com.pharmacie.dto.ApproForm;
import com.pharmacie.exception.DonneeInvalideException;
import com.pharmacie.model.Approvisionnement;
import com.pharmacie.model.Produit;
import com.pharmacie.model.Utilisateur;
import com.pharmacie.security.SessionManager;
import com.pharmacie.service.ApprovisionnementService;
import com.pharmacie.service.ProduitService;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeView;
import javafx.scene.control.cell.PropertyValueFactory;

public class ApprovisionnementsController {

    private final ApprovisionnementService approvisionnementService;
    private final ProduitService produitService;
    private final SessionManager sessionManager;

    @FXML private TextField rechercheField;
    @FXML private TreeView<Object> arbreSelection;
    @FXML private TextField produitIdField;
    @FXML private TextField numeroLotField;
    @FXML private DatePicker dateReceptionPicker;
    @FXML private DatePicker datePeremptionPicker;
    @FXML private TextField quantiteField;
    @FXML private TextField fournisseurField;
    @FXML private Label utilisateurLabel;
    @FXML private Label messageLabel;
    @FXML private TableView<Approvisionnement> historiqueTable;
    @FXML private TableColumn<Approvisionnement, Integer> idCol;
    @FXML private TableColumn<Approvisionnement, Integer> produitCol;
    @FXML private TableColumn<Approvisionnement, String> lotCol;
    @FXML private TableColumn<Approvisionnement, Object> dateCol;
    @FXML private TableColumn<Approvisionnement, Integer> quantiteCol;
    @FXML private TableColumn<Approvisionnement, String> fournisseurCol;

    public ApprovisionnementsController(ApprovisionnementService approvisionnementService,
                                        ProduitService produitService,
                                        SessionManager sessionManager) {
        this.approvisionnementService = approvisionnementService;
        this.produitService = produitService;
        this.sessionManager = sessionManager;
    }

    @FXML
    private void initialize() {
        Utilisateur utilisateur = sessionManager.getUtilisateurConnecte();
        utilisateurLabel.setText(utilisateur == null
            ? "Aucun utilisateur connecté."
            : "Enregistré par " + utilisateur.getPrenom() + " " + utilisateur.getNom());

        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        produitCol.setCellValueFactory(new PropertyValueFactory<>("produitId"));
        lotCol.setCellValueFactory(new PropertyValueFactory<>("numeroLot"));
        dateCol.setCellValueFactory(new PropertyValueFactory<>("date"));
        quantiteCol.setCellValueFactory(new PropertyValueFactory<>("quantiteRecue"));
        fournisseurCol.setCellValueFactory(new PropertyValueFactory<>("fournisseur"));

        ArbreSelection.configurer(
            arbreSelection,
            valeur -> valeur instanceof Produit produit
                ? produit.getNom()
                : valeur instanceof Approvisionnement appro
                  ? "Lot " + appro.getNumeroLot() + " — " + appro.getDate()
                  : String.valueOf(valeur),
            this::selectionner
        );
    }

    @FXML
    private void handleRechercher() {
        String texte = UiFeedback.texte(rechercheField);
        UiFeedback.executer(
            () -> {
                java.util.List<Produit> produits = produitService.rechercher(texte);
                ArbreSelection.remplir(
                    arbreSelection,
                    "Résultats",
                    java.util.List.of(ArbreSelection.categorie("Produits", produits))
                );
            },
            () -> messageLabel.setText("Recherche terminée.")
        );
    }

    private void selectionner(Object valeur) {
        if (valeur instanceof Produit produit) {
            produitIdField.setText(String.valueOf(produit.getId()));
            messageLabel.setText("Produit sélectionné : " + produit.getNom());
        } else if (valeur instanceof Approvisionnement appro) {
            produitIdField.setText(String.valueOf(appro.getProduitId()));
            numeroLotField.setText(appro.getNumeroLot());
            quantiteField.setText(String.valueOf(appro.getQuantiteRecue()));
            fournisseurField.setText(appro.getFournisseur() == null ? "" : appro.getFournisseur());
            messageLabel.setText("Approvisionnement sélectionné : lot " + appro.getNumeroLot());
        }
    }

    @FXML
    private void handleEnregistrer() {
        Utilisateur utilisateur = sessionManager.getUtilisateurConnecte();
        if (utilisateur == null) {
            UiFeedback.erreur(new DonneeInvalideException(
                "Connectez-vous avant d'enregistrer un approvisionnement."
            ));
            return;
        }

        ApproForm formulaire = new ApproForm(
            UiFeedback.entier(produitIdField, "L'identifiant produit"),
            UiFeedback.texte(numeroLotField),
            UiFeedback.date(dateReceptionPicker, "La date de réception"),
            UiFeedback.date(datePeremptionPicker, "La date de péremption"),
            UiFeedback.entier(quantiteField, "La quantité reçue"),
            UiFeedback.texte(fournisseurField)
        );

        UiFeedback.executer(
            () -> approvisionnementService.enregistrer(formulaire, utilisateur.getId()),
            () -> {
                messageLabel.setText("Approvisionnement enregistré : lot "
                    + formulaire.getNumeroLot() + ".");
                handleHistorique();
            }
        );
    }

    @FXML
    private void handleHistorique() {
        UiFeedback.executer(
            () -> {
                java.util.List<Approvisionnement> appros = approvisionnementService.historique();
                historiqueTable.setItems(FXCollections.observableArrayList(appros));
                ArbreSelection.remplir(
                    arbreSelection,
                    "Résultats",
                    java.util.List.of(ArbreSelection.categorie("Approvisionnements", appros))
                );
            },
            () -> messageLabel.setText("Historique actualisé.")
        );
    }

    @FXML
    private void handleEffacer() {
        produitIdField.clear();
        numeroLotField.clear();
        dateReceptionPicker.setValue(null);
        datePeremptionPicker.setValue(null);
        quantiteField.clear();
        fournisseurField.clear();
        messageLabel.setText("");
    }
}
