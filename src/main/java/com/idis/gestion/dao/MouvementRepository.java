package com.idis.gestion.dao;

import com.idis.gestion.entities.Facture;
import com.idis.gestion.entities.Mouvement;
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
public interface MouvementRepository extends PagingAndSortingRepository<Facture,Long> {

    @Query("select DISTINCT f from Facture f " +
            "inner join f.colis c " +
            "inner join f.typeFacture tf " +
            "inner join f.site s " +
            "where LOWER(f.numeroFacture) like LOWER(CONCAT('%',:numeroFacture,'%') ) " +
            "and ((LOWER(c.reference) like LOWER(CONCAT('%',:referenceColis,'%'))) or (c.reference is null)) " +
            "and ((LOWER(tf.nomTypeFacture) like LOWER(CONCAT('%',:nomTypeFacture,'%'))) or (tf.nomTypeFacture is null)) " +
            "and ((LOWER(s.codeSite) like LOWER(CONCAT('%',:codeSite,'%'))) or (s.codeSite is null)) " +
            "and (f.enable = :enable or :enable = 2) " +
            "order by f.id desc")
    public Page<Facture> listFactures(
            @Param("numeroFacture")String numeroFacture,
            @Param("referenceColis")String referenceColis,
            @Param("nomTypeFacture")String nomTypeFacture,
            @Param("codeSite")String codeSite,
            @Param("enable")int enable,
            Pageable pageable
    );

    @Query("select DISTINCT f from Facture f " +
            "left join f.typeFacture tf " +
            "left join f.colis.siteExpediteur s " +
            "where (f.enable = :enable or :enable = 2) " +
            "and (LOWER(tf.nomTypeFacture) = 'facture') " +
            "and (LOWER(s.nomSite) = :nomSite)"
    )
    public List<Facture> findAllFactures(@Param("nomSite")String nomSite, @Param("enable")int enable);

    @Query("select f from Facture f " +
            "left join f.colis.siteExpediteur s " +
            "where (f.enable = :enable) " +
            "and (LOWER(f.numeroFacture) like LOWER(CONCAT('%',:numeroFacture,'%'))) " +
            "and (LOWER(s.nomSite) = :nomSite)"
    )
    public List<Facture> findFacturesByNumeroFacture(@Param("numeroFacture")String numeroFacture,@Param("nomSite")String nomSite, @Param("enable")int enable);

    @Query("select f from Facture f where f.numeroFacture = :numeroFacture")
    public Facture getFactureByNumeroFacture(@Param("numeroFacture")String numeroFacture);

    public Facture getFactureById(Long id);

    @Modifying
    @Query("update Mouvement m set m.enable = 0, m.updateAt = ?2 where m.id = ?1")
    public void disableFacture(Long id, Date date);

    @Modifying
    @Query("update Mouvement m set m.enable = 1, m.updateAt = ?2 where m.id = ?1")
    public void enableFacture(Long id, Date date);

    @Modifying
    public void removeFactureById(Long id);


}
