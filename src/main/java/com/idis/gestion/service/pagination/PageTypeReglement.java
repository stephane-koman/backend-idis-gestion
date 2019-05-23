package com.idis.gestion.service.pagination;

import com.idis.gestion.entities.Pays;
import com.idis.gestion.entities.TypeReglement;

import java.io.Serializable;
import java.util.List;

public class PageTypeReglement implements Serializable {
    private static final long serialVersionUID = 1L;
    private List<TypeReglement> typesReglement;
    private int page;
    private int nombreTypesReglement;
    private int totalTypesReglement;
    private int totalPages;

    public List<TypeReglement> getTypesReglement() {
        return typesReglement;
    }

    public void setTypesReglement(List<TypeReglement> typesReglement) {
        this.typesReglement = typesReglement;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getNombreTypesReglement() {
        return nombreTypesReglement;
    }

    public void setNombreTypesReglement(int nombreTypesReglement) {
        this.nombreTypesReglement = nombreTypesReglement;
    }

    public int getTotalTypesReglement() {
        return totalTypesReglement;
    }

    public void setTotalTypesReglement(int totalTypesReglement) {
        this.totalTypesReglement = totalTypesReglement;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }
}
