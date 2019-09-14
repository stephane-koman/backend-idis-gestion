package com.idis.gestion.dao;

import com.idis.gestion.entities.TypeReglement;
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
public interface TypeReglementRepository extends PagingAndSortingRepository<TypeReglement,Long> {

    public TypeReglement getTypeReglementById(long id);

    @Modifying
    @Query("update TypeReglement tr set tr.enable = 0, tr.updateAt = ?2 where tr.id = ?1")
    public void disableTypeReglement(Long id, Date date);

    @Modifying
    @Query("update TypeReglement tr set tr.enable = 1, tr.updateAt = ?2 where tr.id = ?1")
    public void enableTypeReglement(Long id, Date date);

    @Modifying
    public void removeTypeReglementById(Long id);

    @Query("select tr from TypeReglement tr where (tr.enable = :enable or :enable = 2)")
    public List<TypeReglement> findAll(@Param("enable") int enable);

    @Query("select tr from TypeReglement tr " +
            "where lower(tr.nomTypeReglement) like lower(concat('%',:nomTypeReglement,'%') ) " +
            "and (tr.enable = :enable or :enable = 2) " +
            "order by tr.id desc ")
    public Page<TypeReglement> findAllByLibelle(@Param("nomTypeReglement") String nomTypeReglement, @Param("enable") int enable, Pageable pageable);

}
