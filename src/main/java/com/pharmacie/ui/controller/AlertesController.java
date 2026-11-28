package com.pharmacie.ui.controller;

import com.pharmacie.dto.Alerte;
import com.pharmacie.service.AlerteService;
import com.pharmacie.service.TypeAlerte;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;

import java.util.List;

public class AlertesController {

    private final AlerteService alerteService;

    @FXML private ComboBox<String> typeComboBox;
    @FXML private Label messageLabel;
    @FXML private Label countLabel;
    @FXML private ListView<Alerte> alertesList;

    public AlertesController(AlerteService alerteService) {
        this.alerteService = alerteService;
    }

    @FXML
    private void initialize() {
        typeComboBox.setItems(FXCollections.observableArrayList(
            "TOUTES",
            TypeAlerte.STOCK_BAS.name(),
            TypeAlerte.RUPTURE.name(),
            TypeAlerte.PEREMPTION_PROCHE.name(),
            TypeAlerte.PERIME.name()
        ));
        typeComboBox.getSelectionModel().selectFirst();

        alertesList.setCellFactory(liste -> new ListCell<>() {
            @Override
            protected void updateItem(Alerte alerte, boolean vide) {
                super.updateItem(alerte, vide);
                setText(vide || alerte == null ? null : alerte.toString());
            }
        });
    }

    @FXML
    private void handleConsulter() {
        String selection = typeComboBox.getValue();
        if (selection == null || "TOUTES".equals(selection)) {
            handleToutes();
            return;
        }

        TypeAlerte type = TypeAlerte.valueOf(selection);
        UiFeedback.executer(
            () -> afficher(alerteService.consulter(type)),
            () -> messageLabel.setText("Alertes « " + type + " » actualisées.")
        );
    }

    @FXML
    private void handleToutes() {
        UiFeedback.executer(
            () -> afficher(alerteService.toutesLesAlertes()),
            () -> messageLabel.setText("Toutes les alertes ont été actualisées.")
        );
    }

    private void afficher(List<Alerte> alertes) {
        alertesList.setItems(FXCollections.observableArrayList(alertes));
        countLabel.setText(alertes.size() + (alertes.size() == 1 ? " alerte" : " alertes"));
    }
}
