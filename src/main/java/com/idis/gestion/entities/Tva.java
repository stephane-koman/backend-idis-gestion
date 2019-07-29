package com.idis.gestion.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Collection;
import java.util.Date;

@Entity
public class Tva {
    @Id
    @GeneratedValue
    private Long id;
    @Column(nullable = false)
    private double valeurTva;
    @Column(columnDefinition = "text")
    private String description;

    private Date createAt;
    private Date updateAt;
    @Column(name = "enable", columnDefinition = "TINYINT default 1", length = 1)
    private int enable = 1;

    @JsonIgnore
    @OneToMany(mappedBy = "tva", fetch = FetchType.LAZY, cascade=CascadeType.ALL, orphanRemoval = true)
    private Collection<Facture> factures;

    public Tva() {
    }

    public Tva(double valeurTva, String description, Date createAt, Date updateAt, int enable) {
        this.valeurTva = valeurTva;
        this.description = description;
        this.createAt = createAt;
        this.updateAt = updateAt;
        this.enable = enable;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public double getValeurTva() {
        return valeurTva;
    }

    public void setValeurTva(double valeurTva) {
        this.valeurTva = valeurTva;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public Collection<Facture> getFactures() {
        return factures;
    }

    public void setFactures(Collection<Facture> factures) {
        this.factures = factures;
    }
}
