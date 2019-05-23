package com.idis.gestion.entities;

import javax.persistence.*;
import java.util.Date;

@Entity
public class ReceptionColis {
    @Id
    @GeneratedValue
    private Long id;
    private Date dateReception;
    @Column(columnDefinition = "text")
    private String description;

    @ManyToOne
    @JoinColumn(name = "code_utilisateur")
    private Utilisateur utilisateur;

    public ReceptionColis() {
    }

    public ReceptionColis(Date dateReception, String description, Utilisateur utilisateur) {
        this.dateReception = dateReception;
        this.description = description;
        this.utilisateur = utilisateur;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getDateReception() {
        return dateReception;
    }

    public void setDateReception(Date dateReception) {
        this.dateReception = dateReception;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Utilisateur getUtilisateur() {
        return utilisateur;
    }

    public void setUtilisateur(Utilisateur utilisateur) {
        this.utilisateur = utilisateur;
    }
}
