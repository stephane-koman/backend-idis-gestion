package com.idis.gestion.entities;

import javax.persistence.*;
import java.util.Date;

@Entity
public class LivraisonColis {
    @Id
    @GeneratedValue
    private Long id;
    private Date dateLivraison;
    @Column(columnDefinition = "text")
    private String description;

    @ManyToOne
    @JoinColumn(name = "code_utilisateur")
    private Utilisateur utilisateur;

    public LivraisonColis() {
    }

    public LivraisonColis(Date dateLivraison, String description, Utilisateur utilisateur) {
        this.dateLivraison = dateLivraison;
        this.description = description;
        this.utilisateur = utilisateur;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getDateLivraison() {
        return dateLivraison;
    }

    public void setDateLivraison(Date dateLivraison) {
        this.dateLivraison = dateLivraison;
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
