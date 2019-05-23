package com.idis.gestion.entities;

import javax.persistence.*;
import java.util.Date;

@Entity
@DiscriminatorValue("EMPLOYE")
public class Employe extends Personne {

    @Column(unique = true)
    private String matricule;

    @ManyToOne
    @JoinColumn(name = "code_site")
    private Site site;

    @ManyToOne
    @JoinColumn(name = "code_fonction")
    private Fonction fonction;

    public Employe() {
    }

    public Employe(String raisonSociale, String contact, String email, String adresse, Date createAt, Date updateAt, int enable, Image image, Fonction fonction, Site site) {
        super(raisonSociale, contact, email, adresse, createAt, updateAt, enable, image);
        this.fonction = fonction;
        this.site = site;
    }

    public String getMatricule() {
        return matricule;
    }

    public void setMatricule(String matricule) {
        this.matricule = matricule;
    }

    public Site getSite() {
        return site;
    }

    public void setSite(Site site) {
        this.site = site;
    }

    public Fonction getFonction() {
        return fonction;
    }

    public void setFonction(Fonction fonction) {
        this.fonction = fonction;
    }
}
