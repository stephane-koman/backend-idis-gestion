package com.idis.gestion.dao;

import com.idis.gestion.entities.Tva;
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
public interface TvaRepository extends PagingAndSortingRepository<Tva,Long> {

    public Tva getTvaById(long id);

    @Modifying
    @Query("update Tva t set t.enable = 0, t.updateAt = ?2 where t.id = ?1")
    public void disableTva(Long id, Date date);

    @Modifying
    @Query("update Tva t set t.enable = 1, t.updateAt = ?2 where t.id = ?1")
    public void enableTva(Long id, Date date);

    @Modifying
    public void removeTvaById(Long id);

    @Query("select t from Tva t where (t.enable = :enable or :enable = 2)")
    public List<Tva> findAll(@Param("enable") int enable);

    @Query("select t from Tva t " +
            "where ((t.valeurTva like :valeurTva) or (:valeurTva = 0 )) " +
            "and (t.enable = :enable or :enable = 2) order by t.id desc ")
    public Page<Tva> findAllByValeurTva(@Param("valeurTva") double valeurTva, @Param("enable") int enable, Pageable pageable);

}
