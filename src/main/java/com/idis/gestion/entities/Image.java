package com.idis.gestion.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.Date;

@Entity
public class Image {
    @Id
    @GeneratedValue
    private Long id;

    private String nomImage;
    
    @Lob
    private byte[] file;

    private Date createAt;
    private Date updateAt;
    @Column(name = "enable", columnDefinition = "TINYINT default 1", length = 1)
    private int enable = 1;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "code_colis")
    private Colis colis;

    public Image() {
    }

    public Image(String nomImage, byte[] file, Date createAt, Date updateAt, int enable, Colis colis) {
        this.nomImage = nomImage;
        this.file = file;
        this.createAt = createAt;
        this.updateAt = updateAt;
        this.enable = enable;
        this.colis = colis;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNomImage() {
        return nomImage;
    }

    public void setNomImage(String nomImage) {
        this.nomImage = nomImage;
    }

    public byte[] getFile() {
        return file;
    }

    public void setFile(byte[] file) {
        this.file = file;
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
