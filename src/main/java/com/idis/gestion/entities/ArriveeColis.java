package com.idis.gestion.entities;

import javax.persistence.*;
import java.util.Date;

@Entity
public class ArriveeColis {
    @Id
    @GeneratedValue
    private Long id;
    private Date dateArrivee;
    @Column(columnDefinition = "text")
    private String description;

    @ManyToOne
    @JoinColumn(name = "code_utilisateur")
    private Utilisateur utilisateur;

    public ArriveeColis() {
    }

    public ArriveeColis(Date dateArrivee, String description, Utilisateur utilisateur) {
        this.dateArrivee = dateArrivee;
        this.description = description;
        this.utilisateur = utilisateur;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getDateArrivee() {
        return dateArrivee;
    }

    public void setDateArrivee(Date dateArrivee) {
        this.dateArrivee = dateArrivee;
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
