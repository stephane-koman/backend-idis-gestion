package com.idis.gestion.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

@Entity
public class Colis {

    @Id
    @GeneratedValue
    private Long id;
    @Column(unique = true)
    private String reference;
    @Column(unique = true)
    private String qrCode;
    @Column(unique = true)
    private String codeLivraison;
    @Column(columnDefinition="double precision default 0")
    private double valeurColis;
    @Column(columnDefinition = "text")
    private String description;

    private String nomDestinataire;
    @Column(length = 30)
    private String contactDestinataire;
    @Column(columnDefinition = "text")
    private String adresseDestinataire;

    private Date createAt;
    private Date updateAt;
    @Column(name = "enable", columnDefinition = "TINYINT default 1", length = 1)
    private int enable = 1;

    @ManyToOne
    @JoinColumn(name = "code_devise")
    private Devise devise;

    @OneToOne
    @JoinColumn(name = "date_enregistrement")
    private EnregistrementColis enregistrementColis;

    @OneToOne
    @JoinColumn(name = "date_expedition")
    private ExpeditionColis expeditionColis;

    @OneToOne
    @JoinColumn(name = "date_arrivee")
    private ArriveeColis arriveeColis;

    @OneToOne
    @JoinColumn(name = "date_reception")
    private ReceptionColis receptionColis;

    @OneToOne
    @JoinColumn(name = "date_livraison")
    private LivraisonColis livraisonColis;

    @OneToOne
    @JoinColumn(name = "site_expediteur", nullable = false)
    private Site siteExpediteur;

    @OneToOne
    @JoinColumn(name = "site_destinataire", nullable = false)
    private Site siteDestinataire;

    @OneToMany(mappedBy = "colis", fetch = FetchType.LAZY)
    private Collection<DetailsColis> detailsColis = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "code_utilisateur", nullable = false)
    private Utilisateur utilisateur;

    @ManyToOne
    @JoinColumn(name = "code_client", nullable = false)
    private Client client;

    @OneToMany(mappedBy = "colis", fetch = FetchType.LAZY, cascade=CascadeType.ALL)
    private Collection<Image> images = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "colis", fetch = FetchType.LAZY, cascade=CascadeType.ALL, orphanRemoval = true)
    private Collection<Facture> factures = new ArrayList<>();

    public Colis() {
    }

    public Colis(String reference, String qrCode, String codeLivraison, double valeurColis, String description, String nomDestinataire, String contactDestinataire, String adresseDestinataire, Date createAt, Date updateAt, int enable, EnregistrementColis enregistrementColis, ExpeditionColis expeditionColis, ArriveeColis arriveeColis, ReceptionColis receptionColis, LivraisonColis livraisonColis, Site siteExpediteur, Site siteDestinataire, Utilisateur utilisateur, Client client, Devise devise) {
        this.reference = reference;
        this.qrCode = qrCode;
        this.valeurColis = valeurColis;
        this.description = description;
        this.nomDestinataire = nomDestinataire;
        this.contactDestinataire = contactDestinataire;
        this.adresseDestinataire = adresseDestinataire;
        this.createAt = createAt;
        this.updateAt = updateAt;
        this.enable = enable;
        this.enregistrementColis = enregistrementColis;
        this.expeditionColis = expeditionColis;
        this.arriveeColis = arriveeColis;
        this.receptionColis = receptionColis;
        this.livraisonColis = livraisonColis;
        this.siteExpediteur = siteExpediteur;
        this.siteDestinataire = siteDestinataire;
        this.utilisateur = utilisateur;
        this.client = client;
        this.devise = devise;
        this.codeLivraison = codeLivraison;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public String getQrCode() {
        return qrCode;
    }

    public void setQrCode(String qrCode) {
        this.qrCode = qrCode;
    }

    public String getCodeLivraison() {
        return codeLivraison;
    }

    public void setCodeLivraison(String codeLivraison) {
        this.codeLivraison = codeLivraison;
    }

    public double getValeurColis() {
        return valeurColis;
    }

    public void setValeurColis(double valeurColis) {
        this.valeurColis = valeurColis;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getNomDestinataire() {
        return nomDestinataire;
    }

    public void setNomDestinataire(String nomDestinataire) {
        this.nomDestinataire = nomDestinataire;
    }

    public String getContactDestinataire() {
        return contactDestinataire;
    }

    public void setContactDestinataire(String contactDestinataire) {
        this.contactDestinataire = contactDestinataire;
    }

    public String getAdresseDestinataire() {
        return adresseDestinataire;
    }

    public void setAdresseDestinataire(String adresseDestinataire) {
        this.adresseDestinataire = adresseDestinataire;
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

    public Utilisateur getUtilisateur() {
        return utilisateur;
    }

    public void setUtilisateur(Utilisateur utilisateur) {
        this.utilisateur = utilisateur;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public EnregistrementColis getEnregistrementColis() {
        return enregistrementColis;
    }

    public void setEnregistrementColis(EnregistrementColis enregistrementColis) {
        this.enregistrementColis = enregistrementColis;
    }

    public ExpeditionColis getExpeditionColis() {
        return expeditionColis;
    }

    public void setExpeditionColis(ExpeditionColis expeditionColis) {
        this.expeditionColis = expeditionColis;
    }

    public ArriveeColis getArriveeColis() {
        return arriveeColis;
    }

    public void setArriveeColis(ArriveeColis arriveeColis) {
        this.arriveeColis = arriveeColis;
    }

    public ReceptionColis getReceptionColis() {
        return receptionColis;
    }

    public void setReceptionColis(ReceptionColis receptionColis) {
        this.receptionColis = receptionColis;
    }

    public LivraisonColis getLivraisonColis() {
        return livraisonColis;
    }

    public void setLivraisonColis(LivraisonColis livraisonColis) {
        this.livraisonColis = livraisonColis;
    }

    public Collection<DetailsColis> getDetailsColis() {
        return detailsColis;
    }

    public void setDetailsColis(Collection<DetailsColis> detailsColis) {
        this.detailsColis = detailsColis;
    }

    public Site getSiteExpediteur() {
        return siteExpediteur;
    }

    public void setSiteExpediteur(Site siteExpediteur) {
        this.siteExpediteur = siteExpediteur;
    }

    public Site getSiteDestinataire() {
        return siteDestinataire;
    }

    public void setSiteDestinataire(Site siteDestinataire) {
        this.siteDestinataire = siteDestinataire;
    }

    public Collection<Facture> getFactures() {
        return factures;
    }

    public void setFactures(Collection<Facture> factures) {
        this.factures = factures;
    }

    public Devise getDevise() {
        return devise;
    }

    public void setDevise(Devise devise) {
        this.devise = devise;
    }

    public Collection<Image> getImages() {
        return images;
    }

    public void setImages(Collection<Image> images) {
        this.images = images;
    }
}
