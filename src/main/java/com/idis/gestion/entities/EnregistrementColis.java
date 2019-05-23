package com.idis.gestion.entities;

import javax.persistence.*;
import java.util.Date;

@Entity
public class EnregistrementColis {
    @Id
    @GeneratedValue
    private Long id;
    private Date dateEnregistrement;
    @Column(columnDefinition = "text")
    private String description;

    @ManyToOne
    @JoinColumn(name = "code_utilisateur")
    private Utilisateur utilisateur;

    public EnregistrementColis() {
    }

    public EnregistrementColis(Date dateEnregistrement, String description, Utilisateur utilisateur) {
        this.dateEnregistrement = dateEnregistrement;
        this.description = description;
        this.utilisateur = utilisateur;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getDateEnregistrement() {
        return dateEnregistrement;
    }

    public void setDateEnregistrement(Date dateEnregistrement) {
        this.dateEnregistrement = dateEnregistrement;
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
