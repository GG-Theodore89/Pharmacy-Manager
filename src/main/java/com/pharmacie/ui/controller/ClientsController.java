package com.pharmacie.ui.controller;

import com.pharmacie.dto.ClientForm;
import com.pharmacie.exception.DonneeInvalideException;
import com.pharmacie.model.Client;
import com.pharmacie.model.Vente;
import com.pharmacie.service.ClientService;
import com.pharmacie.ui.AppState;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
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

public class ClientsController {

    private final ClientService clientService;
    private final AppState appState;

    @FXML private TextField nomField;
    @FXML private TextField prenomField;
    @FXML private TextField adresseField;
    @FXML private TextField telephoneField;
    @FXML private TextField emailField;
    @FXML private TextField ordonanceField;
    @FXML private CheckBox actifCheckBox;
    @FXML private TextField idField;
    @FXML private TextField rechercheField;
    @FXML private TreeView<Object> arbreClients;
    @FXML private Label messageLabel;
    @FXML private TableView<Client> clientsTable;
    @FXML private TableColumn<Client, Integer> idCol;
    @FXML private TableColumn<Client, String> nomCol;
    @FXML private TableColumn<Client, String> prenomCol;
    @FXML private TableColumn<Client, String> adresseCol;
    @FXML private ListView<Vente> historiqueList;

    public ClientsController(ClientService clientService, AppState appState) {
        this.clientService = clientService;
        this.appState = appState;
    }

    @FXML
    private void initialize() {
        actifCheckBox.setSelected(true);
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        nomCol.setCellValueFactory(new PropertyValueFactory<>("nom"));
        prenomCol.setCellValueFactory(new PropertyValueFactory<>("prenom"));
        adresseCol.setCellValueFactory(new PropertyValueFactory<>("adresse"));

        clientsTable.getSelectionModel().selectedItemProperty().addListener(
            (observable, ancien, client) -> {
                if (client != null) {
                    remplirFormulaire(client);
                }
            }
        );

        historiqueList.setCellFactory(liste -> new ListCell<>() {
            @Override
            protected void updateItem(Vente vente, boolean vide) {
                super.updateItem(vente, vide);
                setText(vide || vente == null
                    ? null
                    : "Vente n° " + vente.getId() + " — " + vente.getDateVente()
                      + " — " + vente.getMontantTotal().toPlainString()
                      + " — " + vente.getStatut());
            }
        });

        historiqueList.getSelectionModel().selectedItemProperty().addListener(
            (observable, ancien, vente) -> {
                if (vente != null) {
                    appState.setVenteSelectionnee(vente);
                }
            }
        );

        ArbreSelection.configurer(
            arbreClients,
            valeur -> valeur instanceof Client client
                ? client.getPrenom() + " " + client.getNom()
                : String.valueOf(valeur),
            valeur -> {
                if (valeur instanceof Client client) {
                    remplirFormulaire(client);
                    messageLabel.setText("Client sélectionné : "
                        + client.getPrenom() + " " + client.getNom());
                }
            }
        );
    }

    @FXML
    private void handleAjouter() {
        ClientForm formulaire = formulaire();
        UiFeedback.executer(
            () -> clientService.ajouter(formulaire),
            () -> {
                messageLabel.setText("Client ajouté : " + formulaire.getPrenom()
                    + " " + formulaire.getNom());
                handleRechercher();
            }
        );
    }

    @FXML
    private void handleModifier() {
        int id = UiFeedback.entier(idField, "L'identifiant");
        ClientForm formulaire = formulaire();
        UiFeedback.executer(
            () -> clientService.modifier(id, formulaire),
            () -> {
                messageLabel.setText("Client n° " + id + " modifié.");
                handleRechercher();
            }
        );
    }

    @FXML
    private void handleDesactiver() {
        int id = UiFeedback.entier(idField, "L'identifiant");
        UiFeedback.executer(
            () -> clientService.desactiver(id),
            () -> {
                messageLabel.setText("Client n° " + id + " désactivé.");
                handleRechercher();
            }
        );
    }

    @FXML
    private void handleSupprimer() {
        int id = UiFeedback.entier(idField, "L'identifiant");
        UiFeedback.executer(
            () -> clientService.supprimer(id),
            () -> {
                messageLabel.setText("Client n° " + id + " supprimé.");
                handleRechercher();
            }
        );
    }

    @FXML
    private void handleRechercher() {
        UiFeedback.executer(
            () -> {
                List<Client> clients = clientService.rechercher(UiFeedback.texte(rechercheField));
                clientsTable.setItems(FXCollections.observableArrayList(clients));
                remplirArbre(clients);
            },
            () -> messageLabel.setText("Recherche terminée.")
        );
    }

    @FXML
    private void handleHistoriqueVentes() {
        int id = UiFeedback.entier(idField, "L'identifiant");
        UiFeedback.executer(
            () -> afficherHistorique(clientService.historiqueVentes(id)),
            () -> messageLabel.setText("Historique du client n° " + id + " chargé.")
        );
    }

    @FXML
    private void handleEffacer() {
        nomField.clear();
        prenomField.clear();
        adresseField.clear();
        telephoneField.clear();
        emailField.clear();
        ordonanceField.clear();
        actifCheckBox.setSelected(true);
        idField.clear();
        historiqueList.setItems(FXCollections.emptyObservableList());
        messageLabel.setText("");
    }

    private ClientForm formulaire() {
        return new ClientForm(
            UiFeedback.texte(nomField),
            UiFeedback.texte(prenomField),
            UiFeedback.texte(adresseField),
            UiFeedback.texte(telephoneField),
            UiFeedback.texte(emailField),
            UiFeedback.texte(ordonanceField),
            actifCheckBox.isSelected()
        );
    }

    private void afficherHistorique(List<Vente> ventes) {
        historiqueList.setItems(FXCollections.observableArrayList(ventes));
    }

    private void remplirArbre(List<Client> clients) {
        Map<String, List<Client>> groupes = clients.stream().collect(
            Collectors.groupingBy(
                client -> client.getNom() == null || client.getNom().isBlank()
                    ? "Sans nom"
                    : client.getNom().substring(0, 1).toUpperCase(),
                LinkedHashMap::new,
                Collectors.toList()
            )
        );
        List<ArbreSelection.Categorie> categories = groupes.entrySet().stream()
            .map(groupe -> ArbreSelection.categorie(groupe.getKey(), groupe.getValue()))
            .toList();
        ArbreSelection.remplir(arbreClients, "Clients", categories);
    }

    private void remplirFormulaire(Client client) {
        idField.setText(String.valueOf(client.getId()));
        nomField.setText(client.getNom());
        prenomField.setText(client.getPrenom());
        adresseField.setText(client.getAdresse());
    }
}
