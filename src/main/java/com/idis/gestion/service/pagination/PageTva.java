package com.idis.gestion.service.pagination;

import com.idis.gestion.entities.Tva;

import java.io.Serializable;
import java.util.List;

public class PageTva implements Serializable {
    private static final long serialVersionUID = 1L;
    private List<Tva> tvas;
    private int page;
    private int nombreTvas;
    private int totalTvas;
    private int totalPages;

    public List<Tva> getTvas() {
        return tvas;
    }

    public void setTvas(List<Tva> tvas) {
        this.tvas = tvas;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getNombreTvas() {
        return nombreTvas;
    }

    public void setNombreTvas(int nombreTvas) {
        this.nombreTvas = nombreTvas;
    }

    public int getTotalTvas() {
        return totalTvas;
    }

    public void setTotalTvas(int totalTvas) {
        this.totalTvas = totalTvas;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }
}
