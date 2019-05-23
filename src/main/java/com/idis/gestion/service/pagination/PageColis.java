package com.idis.gestion.service.pagination;

import com.idis.gestion.entities.Colis;

import java.io.Serializable;
import java.util.List;

public class PageColis implements Serializable {
    private static final long serialVersionUID = 1L;
    private List<Colis> colis;
    private int page;
    private int nombreColis;
    private int totalColis;
    private int totalPages;

    public List<Colis> getColis() {
        return colis;
    }

    public void setColis(List<Colis> colis) {
        this.colis = colis;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getNombreColis() {
        return nombreColis;
    }

    public void setNombreColis(int nombreColis) {
        this.nombreColis = nombreColis;
    }

    public int getTotalColis() {
        return totalColis;
    }

    public void setTotalColis(int totalColis) {
        this.totalColis = totalColis;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }
}
