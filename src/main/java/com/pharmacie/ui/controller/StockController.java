package com.pharmacie.ui.controller;

import com.pharmacie.exception.DonneeInvalideException;
import com.pharmacie.model.Lot;
import com.pharmacie.model.Produit;
import com.pharmacie.model.Vente;
import com.pharmacie.model.VenteLot;
import com.pharmacie.service.ProduitService;
import com.pharmacie.service.StockService;
import com.pharmacie.ui.AppState;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class StockController {

    private final StockService stockService;
    private final ProduitService produitService;
    private final AppState appState;

    @FXML private TextField rechercheField;
    @FXML private TreeView<Object> arbreProduits;
    @FXML private TextField produitIdField;
    @FXML private TextField quantiteField;
    @FXML private Label stockLabel;
    @FXML private Label disponibiliteLabel;
    @FXML private Label messageLabel;
    @FXML private Label venteLabel;
    @FXML private ListView<VenteLot> prelevementList;
    @FXML private TableView<Lot> lotsTable;
    @FXML private TableColumn<Lot, Integer> lotIdCol;
    @FXML private TableColumn<Lot, String> numeroLotCol;
    @FXML private TableColumn<Lot, Object> receptionCol;
    @FXML private TableColumn<Lot, Object> peremptionCol;
    @FXML private TableColumn<Lot, Integer> initialeCol;
    @FXML private TableColumn<Lot, Integer> disponibleCol;

    public StockController(StockService stockService,
                           ProduitService produitService,
                           AppState appState) {
        this.stockService = stockService;
        this.produitService = produitService;
        this.appState = appState;
    }

    @FXML
    private void initialize() {
        lotIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        numeroLotCol.setCellValueFactory(new PropertyValueFactory<>("numeroLot"));
        receptionCol.setCellValueFactory(new PropertyValueFactory<>("dateReception"));
        peremptionCol.setCellValueFactory(new PropertyValueFactory<>("datePeremption"));
        initialeCol.setCellValueFactory(new PropertyValueFactory<>("quantiteInitiale"));
        disponibleCol.setCellValueFactory(new PropertyValueFactory<>("quantiteDisponible"));
        venteLabel.setText("Aucune vente sélectionnée.");

        ArbreSelection.configurer(
            arbreProduits,
            valeur -> valeur instanceof Produit produit ? produit.getNom() : String.valueOf(valeur),
            valeur -> {
                if (valeur instanceof Produit produit) {
                    produitIdField.setText(String.valueOf(produit.getId()));
                    messageLabel.setText("Produit sélectionné : " + produit.getNom());
                }
            }
        );
    }

    @FXML
    private void handleRechercher() {
        String texte = UiFeedback.texte(rechercheField);
        UiFeedback.executer(
            () -> {
                List<Produit> produits = produitService.rechercher(texte);
                Map<String, List<Produit>> groupes = produits.stream().collect(
                    Collectors.groupingBy(
                        produit -> produit.getType() == null ? "Autres" : produit.getType(),
                        LinkedHashMap::new,
                        Collectors.toList()
                    )
                );
                List<ArbreSelection.Categorie> categories = groupes.entrySet().stream()
                    .map(groupe -> ArbreSelection.categorie(groupe.getKey(), groupe.getValue()))
                    .toList();
                ArbreSelection.remplir(arbreProduits, "Produits", categories);
            },
            () -> messageLabel.setText("Recherche terminée.")
        );
    }

    @FXML
    private void handleAfficherTous() {
        rechercheField.clear();
        handleRechercher();
    }

    @FXML
    private void handleCalculerStock() {
        int produitId = UiFeedback.entier(produitIdField, "L'identifiant produit");
        UiFeedback.executer(
            () -> {
                int stock = stockService.calculerStock(produitId);
                stockLabel.setText(String.valueOf(stock));
            },
            () -> messageLabel.setText("Stock calculé pour le produit n° " + produitId + ".")
        );
    }

    @FXML
    private void handleVerifierDisponibilite() {
        int produitId = UiFeedback.entier(produitIdField, "L'identifiant produit");
        int quantite = UiFeedback.entier(quantiteField, "La quantité");
        UiFeedback.executer(
            () -> {
                boolean disponible = stockService.verifierDisponibilite(produitId, quantite);
                disponibiliteLabel.setText(disponible ? "Disponible" : "Insuffisant");
            },
            () -> messageLabel.setText("Vérification terminée.")
        );
    }

    @FXML
    private void handleChargerLots() {
        int produitId = UiFeedback.entier(produitIdField, "L'identifiant produit");
        UiFeedback.executer(
            () -> afficherLots(stockService.lotsDuProduit(produitId)),
            () -> messageLabel.setText("Lots du produit n° " + produitId + " chargés.")
        );
    }

    @FXML
    private void handlePrelever() {
        int produitId = UiFeedback.entier(produitIdField, "L'identifiant produit");
        int quantite = UiFeedback.entier(quantiteField, "La quantité");
        UiFeedback.executer(
            () -> {
                List<VenteLot> lots = stockService.prelever(produitId, quantite);
                prelevementList.setItems(FXCollections.observableArrayList(lots));
            },
            () -> messageLabel.setText("Prélèvement effectué.")
        );
    }

    private void afficherLots(List<Lot> lots) {
        lotsTable.setItems(FXCollections.observableArrayList(lots));
    }

    @FXML
    private void handleRestituer() {
        Vente vente = appState.getVenteSelectionnee();
        if (vente == null) {
            UiFeedback.erreur(new DonneeInvalideException(
                "Sélectionnez d'abord une vente dans le module Ventes ou Historique et bilans."
            ));
            return;
        }

        UiFeedback.executer(
            () -> stockService.restituer(vente),
            () -> {
                messageLabel.setText("Stock restitué pour la vente n° " + vente.getId() + ".");
                handleChargerLots();
            }
        );
    }
}
