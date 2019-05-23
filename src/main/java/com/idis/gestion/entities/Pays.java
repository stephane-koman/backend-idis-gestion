package com.idis.gestion.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.Collection;
import java.util.Date;

@Entity
public class Pays {
    @Id
    @GeneratedValue
    private Long id;
    @Column(length = 40, unique = true, nullable = false)
    private String nomPays;
    @Column(columnDefinition = "text")
    private String description;

    private Date createAt;
    private Date updateAt;
    @Column(name = "enable", columnDefinition = "TINYINT default 1", length = 1)
    private int enable = 1;

    @JsonIgnore
    @OneToMany(mappedBy = "pays", fetch = FetchType.LAZY, cascade=CascadeType.ALL, orphanRemoval = true)
    private Collection<Site> sites;

    public Pays() {
    }

    public Pays(String nomPays, String description, Date createAt, Date updateAt, int enable) {
        this.nomPays = nomPays;
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

    public String getNomPays() {
        return nomPays;
    }

    public void setNomPays(String nomPays) {
        this.nomPays = nomPays;
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

    public Collection<Site> getSites() {
        return sites;
    }

    public void setSites(Collection<Site> sites) {
        this.sites = sites;
    }
}
