package com.idis.gestion.service.pagination;

import com.idis.gestion.entities.TypeFacture;

import java.io.Serializable;
import java.util.List;

public class PageTypeFacture implements Serializable {
    private static final long serialVersionUID = 1L;
    private List<TypeFacture> typesFacture;
    private int page;
    private int nombreTypesFacture;
    private int totalTypesFacture;
    private int totalPages;

    public List<TypeFacture> getTypesFacture() {
        return typesFacture;
    }

    public void setTypesFacture(List<TypeFacture> typesFacture) {
        this.typesFacture = typesFacture;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getNombreTypesFacture() {
        return nombreTypesFacture;
    }

    public void setNombreTypesFacture(int nombreTypesFacture) {
        this.nombreTypesFacture = nombreTypesFacture;
    }

    public int getTotalTypesFacture() {
        return totalTypesFacture;
    }

    public void setTotalTypesFacture(int totalTypesFacture) {
        this.totalTypesFacture = totalTypesFacture;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }
}
