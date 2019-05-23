package com.idis.gestion.entities;

import javax.persistence.*;
import java.util.Date;

@Entity
@DiscriminatorValue("REGLEMENT")
public class Reglement extends Mouvement {

    @Column(columnDefinition="double precision default 0")
    private double montantRegle;

    @OneToOne
    private TypeReglement typeReglement;

    @ManyToOne
    @JoinColumn(name = "code_facture")
    private Facture facture;

    public Reglement() {
    }

    public Reglement(double credit, double debit, Date createAt, Date updateAt, int enable, Site site, Colis colis, Devise devise, Utilisateur utilisateur, double montantRegle, TypeReglement typeReglement, Facture facture) {
        super(credit, debit, createAt, updateAt, enable, site, colis, devise, utilisateur);
        this.montantRegle = montantRegle;
        this.typeReglement = typeReglement;
        this.facture = facture;
    }

    public double getMontantRegle() {
        return montantRegle;
    }

    public void setMontantRegle(double montantRegle) {
        this.montantRegle = montantRegle;
    }

    public TypeReglement getTypeReglement() {
        return typeReglement;
    }

    public void setTypeReglement(TypeReglement typeReglement) {
        this.typeReglement = typeReglement;
    }

    public Facture getFacture() {
        return facture;
    }

    public void setFacture(Facture facture) {
        this.facture = facture;
    }
}
