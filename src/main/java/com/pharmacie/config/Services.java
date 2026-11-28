package com.pharmacie.config;

import com.pharmacie.exception.PharmacieException;
import com.pharmacie.service.AlerteService;
import com.pharmacie.service.ApprovisionnementService;
import com.pharmacie.service.AuthService;
import com.pharmacie.service.BilanService;
import com.pharmacie.service.ClientService;
import com.pharmacie.service.ProduitService;
import com.pharmacie.service.StockService;
import com.pharmacie.service.TicketService;
import com.pharmacie.service.VenteService;

import java.lang.reflect.Proxy;

public final class Services {

    private AuthService authService = indisponible(AuthService.class);
    private ProduitService produitService = indisponible(ProduitService.class);
    private StockService stockService = indisponible(StockService.class);
    private ApprovisionnementService approvisionnementService =
        indisponible(ApprovisionnementService.class);
    private VenteService venteService = indisponible(VenteService.class);
    private TicketService ticketService = indisponible(TicketService.class);
    private ClientService clientService = indisponible(ClientService.class);
    private AlerteService alerteService = indisponible(AlerteService.class);
    private BilanService bilanService = indisponible(BilanService.class);

    public AuthService getAuthService() {
        return authService;
    }

    public void setAuthService(AuthService authService) {
        this.authService = authService;
    }

    public ProduitService getProduitService() {
        return produitService;
    }

    public void setProduitService(ProduitService produitService) {
        this.produitService = produitService;
    }

    public StockService getStockService() {
        return stockService;
    }

    public void setStockService(StockService stockService) {
        this.stockService = stockService;
    }

    public ApprovisionnementService getApprovisionnementService() {
        return approvisionnementService;
    }

    public void setApprovisionnementService(ApprovisionnementService approvisionnementService) {
        this.approvisionnementService = approvisionnementService;
    }

    public VenteService getVenteService() {
        return venteService;
    }

    public void setVenteService(VenteService venteService) {
        this.venteService = venteService;
    }

    public TicketService getTicketService() {
        return ticketService;
    }

    public void setTicketService(TicketService ticketService) {
        this.ticketService = ticketService;
    }

    public ClientService getClientService() {
        return clientService;
    }

    public void setClientService(ClientService clientService) {
        this.clientService = clientService;
    }

    public AlerteService getAlerteService() {
        return alerteService;
    }

    public void setAlerteService(AlerteService alerteService) {
        this.alerteService = alerteService;
    }

    public BilanService getBilanService() {
        return bilanService;
    }

    public void setBilanService(BilanService bilanService) {
        this.bilanService = bilanService;
    }

    private static <T> T indisponible(Class<T> type) {
        return type.cast(Proxy.newProxyInstance(
            type.getClassLoader(),
            new Class<?>[]{type},
            (proxy, method, args) -> {
                if ("toString".equals(method.getName())) {
                    return "Service " + type.getSimpleName() + " non disponible";
                }
                if ("hashCode".equals(method.getName())) {
                    return System.identityHashCode(proxy);
                }
                if ("equals".equals(method.getName())) {
                    return proxy == args[0];
                }
                throw new PharmacieException(
                    "Le service " + type.getSimpleName() + " n'est pas encore implémenté."
                );
            }
        ));
    }
}
