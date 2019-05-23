package com.idis.gestion.entities;

import javax.persistence.*;
import java.util.Date;

@Entity
public class ExpeditionColis {
    @Id
    @GeneratedValue
    private Long id;
    private Date dateExpedition;
    @Column(columnDefinition = "text")
    private String description;

    @ManyToOne
    @JoinColumn(name = "code_utilisateur")
    private Utilisateur utilisateur;

    public ExpeditionColis() {
    }

    public ExpeditionColis(Date dateExpedition, String description, Utilisateur utilisateur) {
        this.dateExpedition = dateExpedition;
        this.description = description;
        this.utilisateur = utilisateur;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getDateExpedition() {
        return dateExpedition;
    }

    public void setDateExpedition(Date dateExpedition) {
        this.dateExpedition = dateExpedition;
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
