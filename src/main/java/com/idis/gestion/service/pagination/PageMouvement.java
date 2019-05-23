package com.idis.gestion.service.pagination;

import com.idis.gestion.entities.Mouvement;

import java.io.Serializable;
import java.util.List;

public class PageMouvement implements Serializable {
    private static final long serialVersionUID = 1L;
    private List<Mouvement> mouvements;
    private int page;
    private int nombreMouvements;
    private int totalMouvements;
    private int totalPages;

    public List<Mouvement> getMouvements() {
        return mouvements;
    }

    public void setMouvements(List<Mouvement> mouvements) {
        this.mouvements = mouvements;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getNombreMouvements() {
        return nombreMouvements;
    }

    public void setNombreMouvements(int nombreMouvements) {
        this.nombreMouvements = nombreMouvements;
    }

    public int getTotalMouvements() {
        return totalMouvements;
    }

    public void setTotalMouvements(int totalMouvements) {
        this.totalMouvements = totalMouvements;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }
}
