package com.idis.gestion.service.pagination;

import com.idis.gestion.entities.Facture;

import java.io.Serializable;
import java.util.List;

public class PageFacture implements Serializable {
    private static final long serialVersionUID = 1L;
    private List<Facture> factures;
    private int page;
    private int nombreFactures;
    private int totalFactures;
    private int totalPages;

    public List<Facture> getFactures() {
        return factures;
    }

    public void setFactures(List<Facture> factures) {
        this.factures = factures;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getNombreFactures() {
        return nombreFactures;
    }

    public void setNombreFactures(int nombreFactures) {
        this.nombreFactures = nombreFactures;
    }

    public int getTotalFactures() {
        return totalFactures;
    }

    public void setTotalFactures(int totalFactures) {
        this.totalFactures = totalFactures;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }
}
