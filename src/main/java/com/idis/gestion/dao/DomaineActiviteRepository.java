package com.idis.gestion.dao;

import com.idis.gestion.entities.DomaineActivite;
import com.idis.gestion.entities.DomaineActivite;
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
public interface DomaineActiviteRepository extends PagingAndSortingRepository<DomaineActivite,Long> {

    public DomaineActivite getDomaineActiviteById(long id);

    @Modifying
    @Query("update DomaineActivite d set d.enable = 0, d.updateAt = ?2 where d.id = ?1")
    public void disableDomaineActivite(Long id, Date date);

    @Modifying
    @Query("update DomaineActivite d set d.enable = 1, d.updateAt = ?2 where d.id = ?1")
    public void enableDomaineActivite(Long id, Date date);

    @Modifying
    public void removeDomaineActiviteById(Long id);

    @Query("select d from DomaineActivite d where (d.enable = :enable or :enable = 2)")
    public List<DomaineActivite> findAll(@Param("enable") int enable);

    @Query("select d from DomaineActivite d where lower(d.code) like lower(concat('%',:code,'%') ) and (d.enable = :enable or :enable = 2) order by d.id desc ")
    public Page<DomaineActivite> findAllByCode(@Param("code") String code, @Param("enable") int enable, Pageable pageable);

    @Query("select d from DomaineActivite d " +
            "where (d.enable = :enable or :enable = 2) " +
            "and (lower(d.code) like lower(concat('%',:code,'%')))")
    public List<DomaineActivite> findDomaineActiviteByCode(@Param("code")String code, @Param("enable") int enable);

}
