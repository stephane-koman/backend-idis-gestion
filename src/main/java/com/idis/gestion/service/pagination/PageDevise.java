package com.idis.gestion.service.pagination;

import com.idis.gestion.entities.Devise;

import java.io.Serializable;
import java.util.List;

public class PageDevise implements Serializable {
    private static final long serialVersionUID = 1L;
    private List<Devise> devises;
    private int page;
    private int nombreDevises;
    private int totalDevises;
    private int totalPages;

    public List<Devise> getDevises() {
        return devises;
    }

    public void setDevises(List<Devise> devises) {
        this.devises = devises;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getNombreDevises() {
        return nombreDevises;
    }

    public void setNombreDevises(int nombreDevises) {
        this.nombreDevises = nombreDevises;
    }

    public int getTotalDevises() {
        return totalDevises;
    }

    public void setTotalDevises(int totalDevises) {
        this.totalDevises = totalDevises;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }
}
