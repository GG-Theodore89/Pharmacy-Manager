package com.pharmacie.ui.controller;

import com.pharmacie.dto.ProduitForm;
import com.pharmacie.model.Produit;
import com.pharmacie.service.ProduitService;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ProduitsController {

    private final ProduitService produitService;

    @FXML private ComboBox<String> typeComboBox;
    @FXML private TextField nomField;
    @FXML private TextField descriptionField;
    @FXML private TextField prixField;
    @FXML private TextField seuilField;
    @FXML private CheckBox actifCheckBox;
    @FXML private CheckBox surOrdonnanceCheckBox;
    @FXML private TextField idField;
    @FXML private TextField nouveauSeuilField;
    @FXML private TextField rechercheField;
    @FXML private TreeView<Object> arbreProduits;
    @FXML private Label messageLabel;
    @FXML private TableView<Produit> resultatsTable;
    @FXML private TableColumn<Produit, Integer> idCol;
    @FXML private TableColumn<Produit, String> nomCol;
    @FXML private TableColumn<Produit, String> typeCol;
    @FXML private TableColumn<Produit, Object> prixCol;
    @FXML private TableColumn<Produit, Integer> seuilCol;
    @FXML private TableColumn<Produit, Boolean> actifCol;

    public ProduitsController(ProduitService produitService) {
        this.produitService = produitService;
    }

    @FXML
    private void initialize() {
        typeComboBox.setItems(FXCollections.observableArrayList("MEDICAMENT", "PARAPHARMACIE"));
        typeComboBox.getSelectionModel().selectFirst();
        actifCheckBox.setSelected(true);

        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        nomCol.setCellValueFactory(new PropertyValueFactory<>("nom"));
        typeCol.setCellValueFactory(new PropertyValueFactory<>("type"));
        prixCol.setCellValueFactory(new PropertyValueFactory<>("prixVente"));
        seuilCol.setCellValueFactory(new PropertyValueFactory<>("seuilAlerte"));
        actifCol.setCellValueFactory(new PropertyValueFactory<>("actif"));

        resultatsTable.getSelectionModel().selectedItemProperty().addListener(
            (observable, ancien, produit) -> {
                if (produit != null) {
                    remplirFormulaire(produit);
                }
            }
        );

        ArbreSelection.configurer(
            arbreProduits,
            valeur -> valeur instanceof Produit produit
                ? produit.getNom() + "  ·  " + produit.getType()
                : String.valueOf(valeur),
            valeur -> {
                if (valeur instanceof Produit produit) {
                    remplirFormulaire(produit);
                    messageLabel.setText("Produit sélectionné : " + produit.getNom());
                }
            }
        );
    }

    @FXML
    private void handleAjouter() {
        ProduitForm formulaire = formulaire();
        UiFeedback.executer(
            () -> produitService.ajouter(formulaire),
            () -> {
                messageLabel.setText("Produit ajouté : " + formulaire.getNom());
                handleRechercher();
            }
        );
    }

    @FXML
    private void handleModifier() {
        int id = UiFeedback.entier(idField, "L'identifiant");
        ProduitForm formulaire = formulaire();
        UiFeedback.executer(
            () -> produitService.modifier(id, formulaire),
            () -> {
                messageLabel.setText("Produit n° " + id + " modifié.");
                handleRechercher();
            }
        );
    }

    @FXML
    private void handleDesactiver() {
        int id = UiFeedback.entier(idField, "L'identifiant");
        UiFeedback.executer(
            () -> produitService.desactiver(id),
            () -> {
                messageLabel.setText("Produit n° " + id + " désactivé.");
                handleRechercher();
            }
        );
    }

    @FXML
    private void handleRechercher() {
        String texte = UiFeedback.texte(rechercheField);
        UiFeedback.executer(
            () -> {
                List<Produit> produits = produitService.rechercher(texte);
                afficher(produits);
                remplirArbre(produits);
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
    private void handleDefinirSeuil() {
        int id = UiFeedback.entier(idField, "L'identifiant");
        int seuil = UiFeedback.entier(nouveauSeuilField, "Le seuil");
        UiFeedback.executer(
            () -> produitService.definirSeuil(id, seuil),
            () -> {
                messageLabel.setText("Seuil du produit n° " + id + " défini à " + seuil + ".");
                handleRechercher();
            }
        );
    }

    @FXML
    private void handleEffacerFormulaire() {
        nomField.clear();
        descriptionField.clear();
        prixField.clear();
        seuilField.clear();
        idField.clear();
        nouveauSeuilField.clear();
        actifCheckBox.setSelected(true);
        surOrdonnanceCheckBox.setSelected(false);
        resultatsTable.getSelectionModel().clearSelection();
        messageLabel.setText("");
    }

    private ProduitForm formulaire() {
        return new ProduitForm(
            typeComboBox.getValue(),
            UiFeedback.texte(nomField),
            UiFeedback.texte(descriptionField),
            UiFeedback.montant(prixField, "Le prix de vente"),
            UiFeedback.entier(seuilField, "Le seuil d'alerte"),
            actifCheckBox.isSelected(),
            surOrdonnanceCheckBox.isSelected()
        );
    }

    private void afficher(List<Produit> produits) {
        resultatsTable.setItems(FXCollections.observableArrayList(produits));
    }

    private void remplirArbre(List<Produit> produits) {
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
    }

    private void remplirFormulaire(Produit produit) {
        idField.setText(String.valueOf(produit.getId()));
        typeComboBox.setValue(produit.getType());
        nomField.setText(produit.getNom());
        descriptionField.setText(produit.getDescription());
        prixField.setText(produit.getPrixVente() == null ? "" : produit.getPrixVente().toPlainString());
        seuilField.setText(String.valueOf(produit.getSeuilAlerte()));
        nouveauSeuilField.setText(String.valueOf(produit.getSeuilAlerte()));
        actifCheckBox.setSelected(produit.isActif());
        surOrdonnanceCheckBox.setSelected(produit.isSurOrdonnance());
    }
}
