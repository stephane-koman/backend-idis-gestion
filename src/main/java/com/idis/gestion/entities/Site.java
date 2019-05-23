package com.idis.gestion.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

@Entity
public class Site {
    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false, length = 60, unique = true)
    private String nomSite;
    @Column(nullable = false)
    private String codeSite;
    @Column(length = 30)
    private String contact;
    @Column(columnDefinition = "text")
    private String adresse;
    @Column(columnDefinition = "text")
    private String description;

    private Date createAt;
    private Date updateAt;
    @Column(name = "enable", columnDefinition = "TINYINT default 1", length = 1)
    private int enable = 1;

    @OneToOne
    @JoinColumn(name = "logo")
    private Image image;

    @OneToOne
    @JoinColumn(name = "code_tva", nullable = false)
    private Tva tva;

    @OneToOne
    @JoinColumn(name = "code_devise", nullable = false)
    private Devise devise;

    @ManyToOne
    @JoinColumn(name = "code_pays")
    private Pays pays;

    @JsonIgnore
    @OneToMany(mappedBy = "site", fetch = FetchType.LAZY, cascade=CascadeType.ALL, orphanRemoval = true)
    private Collection<Employe> employes = new ArrayList<>();

    public Site() {
    }

    public Site(String nomSite, String codeSite, String contact, String adresse, String description, Date createAt, Date updateAt, int enable, Tva tva, Devise devise, Pays pays, Image image) {
        this.nomSite = nomSite;
        this.codeSite = codeSite;
        this.contact = contact;
        this.adresse = adresse;
        this.description = description;
        this.createAt = createAt;
        this.updateAt = updateAt;
        this.enable = enable;
        this.tva = tva;
        this.devise = devise;
        this.pays = pays;
        this.image = image;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNomSite() {
        return nomSite;
    }

    public void setNomSite(String nomSite) {
        this.nomSite = nomSite;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
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

    public Pays getPays() {
        return pays;
    }

    public void setPays(Pays pays) {
        this.pays = pays;
    }

    public String getCodeSite() {
        return codeSite;
    }

    public void setCodeSite(String codeSite) {
        this.codeSite = codeSite;
    }

    public Tva getTva() {
        return tva;
    }

    public void setTva(Tva tva) {
        this.tva = tva;
    }

    public Devise getDevise() {
        return devise;
    }

    public void setDevise(Devise devise) {
        this.devise = devise;
    }

    public Collection<Employe> getEmployes() {
        return employes;
    }

    public void setEmployes(Collection<Employe> employes) {
        this.employes = employes;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }
}
