package com.idis.gestion.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Date;

@Entity
public class TypeFacture {
    @Id
    @GeneratedValue
    private Long id;

    @Column(length = 40, unique = true, nullable = false)
    private String nomTypeFacture;

    @Column(columnDefinition = "text")
    private String description;

    private Date createAt;
    private Date updateAt;
    @Column(name = "enable", columnDefinition = "TINYINT default 1", length = 1)
    private int enable = 1;

    public TypeFacture() {
    }

    public TypeFacture(String nomTypeFacture, String description, Date createAt, Date updateAt, int enable) {
        this.nomTypeFacture = nomTypeFacture;
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

    public String getNomTypeFacture() {
        return nomTypeFacture;
    }

    public void setNomTypeFacture(String nomTypeFacture) {
        this.nomTypeFacture = nomTypeFacture;
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
}
