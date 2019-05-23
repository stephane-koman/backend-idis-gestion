package com.idis.gestion.entities;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import javax.persistence.*;
import java.util.Date;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "TYPE_MVT", discriminatorType = DiscriminatorType.STRING, length = 10)
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(name = "FACTURE", value = Facture.class),
        @JsonSubTypes.Type(name = "REGLEMENT", value = Reglement.class)
})
public abstract class Mouvement {
    @Id
    @GeneratedValue
    private Long id;

    private double credit;
    private double debit;

    private Date createAt;
    private Date updateAt;
    @Column(name = "enable", columnDefinition = "TINYINT default 1", length = 1)
    private int enable = 1;

    @OneToOne
    @JoinColumn(name = "code_site", nullable = false)
    private Site site;

    @ManyToOne
    @JoinColumn(name = "reference_colis")
    private Colis colis;

    @ManyToOne
    @JoinColumn(name = "code_devise")
    private Devise devise;

    @ManyToOne
    @JoinColumn(name = "code_utilisateur")
    private Utilisateur utilisateur;

    public Mouvement() {
    }

    public Mouvement(double credit, double debit, Date createAt, Date updateAt, int enable, Site site, Colis colis, Devise devise, Utilisateur utilisateur) {
        this.credit = credit;
        this.debit = debit;
        this.createAt = createAt;
        this.updateAt = updateAt;
        this.enable = enable;
        this.site = site;
        this.colis = colis;
        this.devise = devise;
        this.utilisateur = utilisateur;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Colis getColis() {
        return colis;
    }

    public void setColis(Colis colis) {
        this.colis = colis;
    }

    public Utilisateur getUtilisateur() {
        return utilisateur;
    }

    public void setUtilisateur(Utilisateur utilisateur) {
        this.utilisateur = utilisateur;
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

    public double getCredit() {
        return credit;
    }

    public void setCredit(double credit) {
        this.credit = credit;
    }

    public double getDebit() {
        return debit;
    }

    public void setDebit(double debit) {
        this.debit = debit;
    }

    public Devise getDevise() {
        return devise;
    }

    public void setDevise(Devise devise) {
        this.devise = devise;
    }

    public Site getSite() {
        return site;
    }

    public void setSite(Site site) {
        this.site = site;
    }
}
