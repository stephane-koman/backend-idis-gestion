package com.idis.gestion.service.pagination;

import com.idis.gestion.entities.Employe;
import com.idis.gestion.entities.Personne;

import java.io.Serializable;
import java.util.List;

public class PageEmploye implements Serializable {
    private static final long serialVersionUID = 1L;
    private List<Employe> employes;
    private int page;
    private int nombreEmployes;
    private int totalEmployes;
    private int totalPages;

    public List<Employe> getEmployes() {
        return employes;
    }

    public void setEmployes(List<Employe> employes) {
        this.employes = employes;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getNombreEmployes() {
        return nombreEmployes;
    }

    public void setNombreEmployes(int nombreEmployes) {
        this.nombreEmployes = nombreEmployes;
    }

    public int getTotalEmployes() {
        return totalEmployes;
    }

    public void setTotalEmployes(int totalEmployes) {
        this.totalEmployes = totalEmployes;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }
}
