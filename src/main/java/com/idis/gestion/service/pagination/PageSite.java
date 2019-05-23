package com.idis.gestion.service.pagination;

import com.idis.gestion.entities.Site;

import java.io.Serializable;
import java.util.List;

public class PageSite implements Serializable {
    private static final long serialVersionUID = 1L;
    private List<Site> sites;
    private int page;
    private int nombreSites;
    private int totalSites;
    private int totalPages;

    public List<Site> getSites() {
        return sites;
    }

    public void setSites(List<Site> sites) {
        this.sites = sites;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getNombreSites() {
        return nombreSites;
    }

    public void setNombreSites(int nombreSites) {
        this.nombreSites = nombreSites;
    }

    public int getTotalSites() {
        return totalSites;
    }

    public void setTotalSites(int totalSites) {
        this.totalSites = totalSites;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }
}
