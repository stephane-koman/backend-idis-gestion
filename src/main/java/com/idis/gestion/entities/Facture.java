package com.idis.gestion.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

@Entity
@DiscriminatorValue("FACTURE")
public class Facture extends Mouvement {

    @Column(unique = true)
    private String numeroFacture;

    private Date dateEcheance;

    @Column(columnDefinition="double precision default 0")
    private double montantFactureRegle;

    @Column(name = "exonere", columnDefinition = "TINYINT default 0", length = 1)
    private int exonere;

    @OneToOne
    private TypeFacture typeFacture;

    @ManyToOne
    @JoinColumn(name = "code_tva")
    private Tva tva;

    @ManyToOne
    @JoinColumn(name = "reference_colis")
    private Colis colis;

    @JsonIgnore
    @OneToMany(mappedBy = "facture", fetch = FetchType.LAZY)
    private Collection<Reglement> reglements = new ArrayList<>();


    public Facture() {
    }

    public Facture(double credit, double debit, double montantFactureRegle, int exonere, Date createAt, Date updateAt, int enable, Site site, Colis colis, Devise devise, Utilisateur utilisateur, String numeroFacture, Date dateEcheance, TypeFacture typeFacture, Tva tva) {
        super(credit, debit, createAt, updateAt, enable, site, devise, utilisateur);
        this.numeroFacture = numeroFacture;
        this.dateEcheance = dateEcheance;
        this.typeFacture = typeFacture;
        this.tva = tva;
        this.colis = colis;
        this.exonere = exonere;
        this.montantFactureRegle = montantFactureRegle;
    }

    public double getMontantFactureRegle() {
        return montantFactureRegle;
    }

    public void setMontantFactureRegle(double montantFactureRegle) {
        this.montantFactureRegle = montantFactureRegle;
    }

    public String getNumeroFacture() {
        return numeroFacture;
    }

    public void setNumeroFacture(String numeroFacture) {
        this.numeroFacture = numeroFacture;
    }

    public Date getDateEcheance() {
        return dateEcheance;
    }

    public void setDateEcheance(Date dateEcheance) {
        this.dateEcheance = dateEcheance;
    }

    public TypeFacture getTypeFacture() {
        return typeFacture;
    }

    public void setTypeFacture(TypeFacture typeFacture) {
        this.typeFacture = typeFacture;
    }

    public Colis getColis() {
        return colis;
    }

    public void setColis(Colis colis) {
        this.colis = colis;
    }

    public Tva getTva() {
        return tva;
    }

    public void setTva(Tva tva) {
        this.tva = tva;
    }

    public int getExonere() {
        return exonere;
    }

    public void setExonere(int exonere) {
        this.exonere = exonere;
    }

    public Collection<Reglement> getReglements() {
        return reglements;
    }

    public void setReglements(Collection<Reglement> reglements) {
        this.reglements = reglements;
    }
}
