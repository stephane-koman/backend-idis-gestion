package com.idis.gestion.service.pagination;

import com.idis.gestion.entities.Reglement;

import java.io.Serializable;
import java.util.List;

public class PageReglement implements Serializable {
    private static final long serialVersionUID = 1L;
    private List<Reglement> reglements;
    private int page;
    private int nombreReglements;
    private int totalReglements;
    private int totalPages;

    public List<Reglement> getReglements() {
        return reglements;
    }

    public void setReglements(List<Reglement> reglements) {
        this.reglements = reglements;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getNombreReglements() {
        return nombreReglements;
    }

    public void setNombreReglements(int nombreReglements) {
        this.nombreReglements = nombreReglements;
    }

    public int getTotalReglements() {
        return totalReglements;
    }

    public void setTotalReglements(int totalReglements) {
        this.totalReglements = totalReglements;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }
}
