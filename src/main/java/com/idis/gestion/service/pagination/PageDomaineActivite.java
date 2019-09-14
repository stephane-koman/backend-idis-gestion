package com.idis.gestion.service.pagination;

import com.idis.gestion.entities.DomaineActivite;

import java.io.Serializable;
import java.util.List;

public class PageDomaineActivite implements Serializable {
    private static final long serialVersionUID = 1L;
    private List<DomaineActivite> domainesActivite;
    private int page;
    private int nombreDomaineActivite;
    private int totalDomaineActivite;
    private int totalPages;

    public List<DomaineActivite> getDomainesActivite() {
        return domainesActivite;
    }

    public void setDomainesActivite(List<DomaineActivite> domainesActivite) {
        this.domainesActivite = domainesActivite;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getNombreDomaineActivite() {
        return nombreDomaineActivite;
    }

    public void setNombreDomaineActivite(int nombreDomaineActivite) {
        this.nombreDomaineActivite = nombreDomaineActivite;
    }

    public int getTotalDomaineActivite() {
        return totalDomaineActivite;
    }

    public void setTotalDomaineActivite(int totalDomaineActivite) {
        this.totalDomaineActivite = totalDomaineActivite;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }
}
