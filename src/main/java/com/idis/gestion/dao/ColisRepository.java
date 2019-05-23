package com.idis.gestion.dao;

import com.idis.gestion.entities.Colis;
import com.idis.gestion.entities.Personne;
import com.idis.gestion.entities.Utilisateur;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Transactional
public interface ColisRepository extends PagingAndSortingRepository<Colis,String> {

    /**
     *  La liste des colis
     * @return
     */
    @Query("select c " +
            "from Colis c " +
            "left join c.client clt " +
            "where ((lower(c.reference) like lower(concat('%',:reference,'%') )) or (c.reference is null )) " +
            "and ((lower(clt.raisonSociale) like lower(concat('%',:nomClient,'%') )) or (clt.raisonSociale is null)) " +
            "and ((lower(c.nomDestinataire) like lower(concat('%',:nomDestinataire,'%') )) or (c.nomDestinataire is null)) " +
            "and (c.enable = :enable or :enable=2) " +
            "order by c.reference desc ")
    public Page<Colis> listColis(
            @Param("reference")String reference,
            @Param("nomClient")String nomClient,
            @Param("nomDestinataire")String nomDestinataire,
            @Param("enable")int enable,
            Pageable pageable
    );

    @Query("select c " +
            "from Colis c " +
            "left join c.client clt " +
            "left join c.siteExpediteur site " +
            "where ((lower(c.reference) like lower(concat('%',:reference,'%') )) or (c.reference is null )) " +
            "and ((lower(clt.raisonSociale) like lower(concat('%',:nomClient,'%') )) or (clt.raisonSociale is null)) " +
            "and ((lower(c.nomDestinataire) like lower(concat('%',:nomDestinataire,'%') )) or (c.nomDestinataire is null)) " +
            "and lower(site.nomSite) = lower(:nomSite) " +
            "and (c.enable = :enable or :enable=2) " +
            "order by c.reference desc ")
    public Page<Colis> sendListColis(
            @Param("reference")String reference,
            @Param("nomClient")String nomClient,
            @Param("nomDestinataire")String nomDestinataire,
            @Param("nomSite")String nomSite,
            @Param("enable")int enable,
            Pageable pageable
    );

    @Query("select c " +
            "from Colis c " +
            "left join c.client clt " +
            "left join c.siteDestinataire site " +
            "where ((lower(c.reference) like lower(concat('%',:reference,'%') )) or (c.reference is null )) " +
            "and ((lower(clt.raisonSociale) like lower(concat('%',:nomClient,'%') )) or (clt.raisonSociale is null)) " +
            "and ((lower(c.nomDestinataire) like lower(concat('%',:nomDestinataire,'%') )) or (c.nomDestinataire is null)) " +
            "and lower(site.nomSite) = lower(:nomSite) " +
            "and c.expeditionColis is not null " +
            "and (c.enable = :enable or :enable=2) " +
            "order by c.reference desc ")
    public Page<Colis> receiveListColis(
            @Param("reference")String reference,
            @Param("nomClient")String nomClient,
            @Param("nomDestinataire")String nomDestinataire,
            @Param("nomSite")String nomSite,
            @Param("enable")int enable,
            Pageable pageable
    );

    @Query("select c " +
            "from Colis c " +
            "left join c.client clt " +
            "where ((lower(c.reference) like lower(concat('%',:reference,'%') )) or (c.reference is null )) " +
            "and (clt.id = :idClient) " +
            "and ((lower(c.nomDestinataire) like lower(concat('%',:nomDestinataire,'%') )) or (c.nomDestinataire is null)) " +
            "and (c.enable = :enable or :enable=2) " +
            "order by c.reference desc ")
    public Page<Colis> clientListColis(
            @Param("reference")String reference,
            @Param("idClient")Long idClient,
            @Param("nomDestinataire")String nomDestinataire,
            @Param("enable")int enable,
            Pageable pageable
    );

    @Query("select c from Colis c " +
            "left join c.siteExpediteur site " +
            "where lower(site.nomSite) = lower(:nomSite) " +
            "and (c.enable = :enable or :enable = 2)")
    public List<Colis> findAllSendColis(@Param("nomSite")String nomSite, @Param("enable") int enable);

    public Colis findColisByReference(String ref);

    @Modifying
    @Query("update Colis c set c.enable = 0, c.updateAt = ?2 where lower(c.reference) = lower(?1)")
    public void disableColis(String ref, Date date);

    @Modifying
    @Query("update Colis c set c.enable = 1, c.updateAt = ?2 where lower(c.reference) = lower(?1)")
    public void enableColis(String ref, Date date);

    @Modifying
    public void removeColisByReference(String ref);

    @Query("select count(c) from Colis c " +
            "left join c.siteExpediteur site " +
            "where site.id = :idSite " +
            "and (c.enable = :enable or :enable=2)")
    public int countSendColis(@Param("idSite") Long idSite, @Param("enable")int enable);

    @Query("select count(c) from Colis c " +
            "left join c.siteDestinataire site " +
            "where site.id = :idSite " +
            "and c.expeditionColis is not null " +
            "and (c.enable = :enable or :enable=2)")
    public int countReceiveColis(@Param("idSite") Long idSite, @Param("enable")int enable);

    @Query("select count(c) from Colis c " +
            "left join c.client clt " +
            "where (clt.id = :idClient) " +
            "and (c.enable = :enable or :enable=2)")
    public int countClientColis(@Param("idClient")Long idClient, @Param("enable")int enable);

}
