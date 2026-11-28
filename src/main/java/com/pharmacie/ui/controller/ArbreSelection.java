package com.pharmacie.ui.controller;

import javafx.scene.control.TreeCell;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

public final class ArbreSelection {

    private ArbreSelection() {
    }

    public record Categorie(String nom, List<?> elements) {
    }

    public static Categorie categorie(String nom, List<?> elements) {
        return new Categorie(nom, elements);
    }

    public static void remplir(TreeView<Object> arbre,
                               String racine,
                               List<Categorie> categories) {
        TreeItem<Object> racineItem = new TreeItem<>(racine);
        for (Categorie categorie : categories) {
            TreeItem<Object> groupe = new TreeItem<>(categorie.nom());
            for (Object element : categorie.elements()) {
                groupe.getChildren().add(new TreeItem<>(element));
            }
            racineItem.getChildren().add(groupe);
        }
        arbre.setRoot(racineItem);
    }

    public static void configurer(TreeView<Object> arbre,
                                  Function<Object, String> formateur,
                                  Consumer<Object> action) {
        arbre.setShowRoot(false);
        arbre.setCellFactory(liste -> new TreeCell<>() {
            @Override
            protected void updateItem(Object valeur, boolean vide) {
                super.updateItem(valeur, vide);
                if (vide || valeur == null) {
                    setText(null);
                } else {
                    setText(valeur instanceof String
                        ? (String) valeur
                        : formateur.apply(valeur));
                }
            }
        });
        arbre.getSelectionModel().selectedItemProperty().addListener(
            (observable, ancien, item) -> {
                if (item != null && item.isLeaf() && item.getValue() != null) {
                    action.accept(item.getValue());
                }
            }
        );
    }
}
