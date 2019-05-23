package com.idis.gestion.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Collection;
import java.util.Date;

@Entity
public class Devise {
    @Id
    @GeneratedValue
    private Long id;
    @Column(length = 40, unique = true, nullable = false)
        private String nomDevise;

    private double cout;

    @Column(columnDefinition = "text")
    private String description;

    private Date createAt;
    private Date updateAt;
    @Column(name = "enable", columnDefinition = "TINYINT default 1", length = 1)
    private int enable = 1;

    @JsonIgnore
    @OneToMany(mappedBy = "devise", fetch = FetchType.LAZY, cascade=CascadeType.ALL, orphanRemoval = true)
    private Collection<Mouvement> mouvements;

    @JsonIgnore
    @OneToMany(mappedBy = "devise", fetch = FetchType.LAZY, cascade=CascadeType.ALL, orphanRemoval = true)
    private Collection<Colis> colis;

    public Devise() {
    }

    public Devise(String nomDevise, double cout, String description, Date createAt, Date updateAt, int enable) {
        this.nomDevise = nomDevise;
        this.cout = cout;
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

    public String getNomDevise() {
        return nomDevise;
    }

    public void setNomDevise(String nomDevise) {
        this.nomDevise = nomDevise;
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

    public double getCout() {
        return cout;
    }

    public void setCout(double cout) {
        this.cout = cout;
    }

    public Collection<Mouvement> getMouvements() {
        return mouvements;
    }

    public void setMouvements(Collection<Mouvement> mouvements) {
        this.mouvements = mouvements;
    }

    public Collection<Colis> getColis() {
        return colis;
    }

    public void setColis(Collection<Colis> colis) {
        this.colis = colis;
    }
}
