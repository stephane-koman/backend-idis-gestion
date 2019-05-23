package com.idis.gestion.service.pagination;

import com.idis.gestion.entities.Utilisateur;

import java.io.Serializable;
import java.util.List;

public class PageUtilisateur implements Serializable {

    private static final long serialVersionUID = 1L;
    private List<Utilisateur> utilisateurs;
    private int page;
    private int nombreUtilisateurs;
    private int totalUtilisateurs;
    private int totalPages;

    public List<Utilisateur> getUtilisateurs() {
        return utilisateurs;
    }

    public void setUtilisateurs(List<Utilisateur> utilisateurs) {
        this.utilisateurs = utilisateurs;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getNombreUtilisateurs() {
        return nombreUtilisateurs;
    }

    public void setNombreUtilisateurs(int nombreUtilisateurs) {
        this.nombreUtilisateurs = nombreUtilisateurs;
    }

    public int getTotalUtilisateurs() {
        return totalUtilisateurs;
    }

    public void setTotalUtilisateurs(int totalUtilisateurs) {
        this.totalUtilisateurs = totalUtilisateurs;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }
}
