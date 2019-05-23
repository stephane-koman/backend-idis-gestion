package com.idis.gestion.service.pagination;

import com.idis.gestion.entities.Client;
import com.idis.gestion.entities.Employe;

import java.io.Serializable;
import java.util.List;

public class PageClient implements Serializable {
    private static final long serialVersionUID = 1L;
    private List<Client> clients;
    private int page;
    private int nombreClients;
    private int totalClients;
    private int totalPages;

    public List<Client> getClients() {
        return clients;
    }

    public void setClients(List<Client> clients) {
        this.clients = clients;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getNombreClients() {
        return nombreClients;
    }

    public void setNombreClients(int nombreClients) {
        this.nombreClients = nombreClients;
    }

    public int getTotalClients() {
        return totalClients;
    }

    public void setTotalClients(int totalClients) {
        this.totalClients = totalClients;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }
}
