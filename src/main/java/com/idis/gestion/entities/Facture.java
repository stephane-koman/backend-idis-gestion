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

    @OneToOne
    private TypeFacture typeFacture;

    @ManyToOne
    @JoinColumn(name = "code_tva")
    private Tva tva;

    @OneToMany(mappedBy = "facture", fetch = FetchType.LAZY)
    private Collection<LigneFacture> ligneFactures = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "facture", fetch = FetchType.LAZY)
    private Collection<Reglement> reglements = new ArrayList<>();


    public Facture() {
    }

    public Facture(double credit, double debit, Date createAt, Date updateAt, int enable, Site site, Colis colis, Devise devise, Utilisateur utilisateur, String numeroFacture, Date dateEcheance, TypeFacture typeFacture, Tva tva) {
        super(credit, debit, createAt, updateAt, enable, site, colis, devise, utilisateur);
        this.numeroFacture = numeroFacture;
        this.dateEcheance = dateEcheance;
        this.typeFacture = typeFacture;
        this.tva = tva;
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

    public Collection<LigneFacture> getLigneFactures() {
        return ligneFactures;
    }

    public void setLigneFactures(Collection<LigneFacture> ligneFactures) {
        this.ligneFactures = ligneFactures;
    }

    public TypeFacture getTypeFacture() {
        return typeFacture;
    }

    public void setTypeFacture(TypeFacture typeFacture) {
        this.typeFacture = typeFacture;
    }

    public Tva getTva() {
        return tva;
    }

    public void setTva(Tva tva) {
        this.tva = tva;
    }

    public Collection<Reglement> getReglements() {
        return reglements;
    }

    public void setReglements(Collection<Reglement> reglements) {
        this.reglements = reglements;
    }
}
