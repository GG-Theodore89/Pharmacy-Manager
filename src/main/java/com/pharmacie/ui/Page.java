package com.pharmacie.ui;

public enum Page {

    COMPTE("Compte et accès", "/fxml/CompteView.fxml"),
    PRODUITS("Catalogue des produits", "/fxml/ProduitsView.fxml"),
    STOCK("Stock et lots", "/fxml/StockView.fxml"),
    APPROVISIONNEMENTS("Approvisionnements", "/fxml/ApprovisionnementsView.fxml"),
    PANIER("Panier", "/fxml/PanierView.fxml"),
    VENTES("Ventes", "/fxml/VentesView.fxml"),
    TICKETS("Tickets", "/fxml/TicketsView.fxml"),
    CLIENTS("Clients", "/fxml/ClientsView.fxml"),
    ALERTES("Alertes", "/fxml/AlertesView.fxml"),
    BILANS("Historique et bilans", "/fxml/BilansView.fxml");

    private final String titre;
    private final String ressource;

    Page(String titre, String ressource) {
        this.titre = titre;
        this.ressource = ressource;
    }

    public String getTitre() {
        return titre;
    }

    public String getRessource() {
        return ressource;
    }
}
