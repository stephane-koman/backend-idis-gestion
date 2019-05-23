package com.idis.gestion.service.pagination;

import com.idis.gestion.entities.Fonction;

import java.io.Serializable;
import java.util.List;

public class PageFonction implements Serializable {
    private static final long serialVersionUID = 1L;
    private List<Fonction> fonctions;
    private int page;
    private int nombreFonctions;
    private int totalFonctions;
    private int totalPages;

    public List<Fonction> getFonctions() {
        return fonctions;
    }

    public void setFonctions(List<Fonction> fonctions) {
        this.fonctions = fonctions;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getNombreFonctions() {
        return nombreFonctions;
    }

    public void setNombreFonctions(int nombreFonctions) {
        this.nombreFonctions = nombreFonctions;
    }

    public int getTotalFonctions() {
        return totalFonctions;
    }

    public void setTotalFonctions(int totalFonctions) {
        this.totalFonctions = totalFonctions;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }
}
