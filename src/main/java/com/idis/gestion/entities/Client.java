package com.idis.gestion.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

@Entity
@DiscriminatorValue("CLIENT")
public class Client extends Personne {

    @Column(unique = true)
    private String codeClient;

    @Column(columnDefinition = "text")
    private String responsable;

    @JsonIgnore
    @OneToMany(mappedBy = "client", fetch = FetchType.LAZY, cascade=CascadeType.REMOVE, orphanRemoval = true)
    private Collection<Colis> colis = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "code_domaine_activite")
    private DomaineActivite domaineActivite;

    public Client() {
    }

    public Client(String raisonSociale, String contact, String email, String adresse, Date createAt, Date updateAt, int enable, Image image) {
        super(raisonSociale, contact, email, adresse, createAt, updateAt, enable, image);
    }

    public String getCodeClient() {
        return codeClient;
    }

    public String getResponsable() {
        return responsable;
    }

    public void setResponsable(String responsable) {
        this.responsable = responsable;
    }

    public DomaineActivite getDomaineActivite() {
        return domaineActivite;
    }

    public void setDomaineActivite(DomaineActivite domaineActivite) {
        this.domaineActivite = domaineActivite;
    }

    public void setCodeClient(String codeClient) {
        this.codeClient = codeClient;
    }

    public Collection<Colis> getColis() {
        return colis;
    }

    public void setColis(Collection<Colis> colis) {
        this.colis = colis;
    }
}
