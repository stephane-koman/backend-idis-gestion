package com.idis.gestion.dao;

import com.idis.gestion.entities.Mouvement;
import com.idis.gestion.entities.Reglement;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Transactional
public interface ReglementRepository extends PagingAndSortingRepository<Reglement,Long> {

    public Reglement getReglementById(long id);

    @Modifying
    @Query("update Reglement r set r.enable = 0, r.updateAt = ?2 where r.id = ?1")
    public void disableReglement(Long id, Date date);

    @Modifying
    @Query("update Reglement r set r.enable = 1, r.updateAt = ?2 where r.id = ?1")
    public void enableReglement(Long id, Date date);

    @Modifying
    public void removeReglementById(Long id);

    @Query("select r from Reglement r " +
            "where lower(r.typeReglement.nomTypeReglement) like lower(concat('%',:nomTypeReglement,'%') ) " +
            "and lower(r.facture.numeroFacture) like lower(concat('%',:numeroFacture,'%') ) " +
            "and (r.enable = :enable or :enable = 2) " +
            "order by r.id desc ")
    public Page<Reglement> findAllReglements(@Param("nomTypeReglement") String nomTypeReglement, @Param("numeroFacture") String numeroFacture, @Param("enable") int enable, Pageable pageable);

}
