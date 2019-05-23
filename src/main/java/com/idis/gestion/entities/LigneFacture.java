package com.idis.gestion.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.Date;

@Entity
public class LigneFacture {
    @Id
    @GeneratedValue
    private Long id;
    private double prixUnitaire;
    private double prixTotal;
    private Date createAt;
    private Date updateAt;
    @Column(name = "enable", columnDefinition = "TINYINT default 1", length = 1)
    private int enable = 1;

    @OneToOne
    private DetailsColis detailsColis;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "code_facture")
    private Facture facture;

    public LigneFacture() {
    }

    public LigneFacture(double prixUnitaire, double prixTotal, Date createAt, Date updateAt, int enable, DetailsColis detailsColis, Facture facture) {
        this.prixUnitaire = prixUnitaire;
        this.prixTotal = prixTotal;
        this.createAt = createAt;
        this.updateAt = updateAt;
        this.enable = enable;
        this.detailsColis = detailsColis;
        this.facture = facture;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public double getPrixUnitaire() {
        return prixUnitaire;
    }

    public void setPrixUnitaire(double prixUnitaire) {
        this.prixUnitaire = prixUnitaire;
    }

    public double getPrixTotal() {
        return prixTotal;
    }

    public void setPrixTotal(double prixTotal) {
        this.prixTotal = prixTotal;
    }

    public Date getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
    }

    public Date getUpdateAt() {
        return updateAt;
    }

    public void setUpdateAt(Date updateAt) {
        this.updateAt = updateAt;
    }

    public int getEnable() {
        return enable;
    }

    public void setEnable(int enable) {
        this.enable = enable;
    }

    public DetailsColis getDetailsColis() {
        return detailsColis;
    }

    public void setDetailsColis(DetailsColis detailsColis) {
        this.detailsColis = detailsColis;
    }

    public Facture getFacture() {
        return facture;
    }

    public void setFacture(Facture facture) {
        this.facture = facture;
    }
}
