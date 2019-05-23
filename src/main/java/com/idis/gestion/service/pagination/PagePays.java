package com.idis.gestion.service.pagination;

import com.idis.gestion.entities.Client;
import com.idis.gestion.entities.Pays;

import java.io.Serializable;
import java.util.List;

public class PagePays implements Serializable {
    private static final long serialVersionUID = 1L;
    private List<Pays> pays;
    private int page;
    private int nombrePays;
    private int totalPays;
    private int totalPages;

    public List<Pays> getPays() {
        return pays;
    }

    public void setPays(List<Pays> pays) {
        this.pays = pays;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getNombrePays() {
        return nombrePays;
    }

    public void setNombrePays(int nombrePays) {
        this.nombrePays = nombrePays;
    }

    public int getTotalPays() {
        return totalPays;
    }

    public void setTotalPays(int totalPays) {
        this.totalPays = totalPays;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }
}
