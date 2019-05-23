package com.idis.gestion.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.Date;

@Entity
public class DetailsColis {

    @Id
    @GeneratedValue
    private Long id;
    private int quantite;
    private int poids;
    private String designation;
    @Column(columnDefinition = "text")
    private String description;
    private Date createAt;
    private Date updateAt;
    @Column(name = "enable", columnDefinition = "TINYINT default 1", length = 1)
    private int enable = 1;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "code_colis")
    private Colis colis;

    public DetailsColis() {
    }

    public DetailsColis(int quantite, int poids, String designation, String description, Date createAt, Date updateAt, int enable) {
        this.quantite = quantite;
        this.poids = poids;
        this.designation = designation;
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

    public int getQuantite() {
        return quantite;
    }

    public void setQuantite(int quantite) {
        this.quantite = quantite;
    }

    public int getPoids() {
        return poids;
    }

    public void setPoids(int poids) {
        this.poids = poids;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
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

    public Colis getColis() {
        return colis;
    }

    public void setColis(Colis colis) {
        this.colis = colis;
    }
}
