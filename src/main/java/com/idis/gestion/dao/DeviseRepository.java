package com.idis.gestion.dao;

import com.idis.gestion.entities.Devise;
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
public interface DeviseRepository extends PagingAndSortingRepository<Devise,Long> {

    public Devise getDeviseById(long id);

    @Modifying
    @Query("update Devise d set d.enable = 0, d.updateAt = ?2 where d.id = ?1")
    public void disableDevise(Long id, Date date);

    @Modifying
    @Query("update Devise d set d.enable = 1, d.updateAt = ?2 where d.id = ?1")
    public void enableDevise(Long id, Date date);

    @Modifying
    public void removeDeviseById(Long id);

    @Query("select d from Devise d where (d.enable = :enable or :enable = 2)")
    public List<Devise> findAll(@Param("enable") int enable);

    @Query("select d from Devise d where lower(d.nomDevise) like lower(concat('%',:nomDevise,'%') ) and (d.enable = :enable or :enable = 2) order by d.id desc ")
    public Page<Devise> findAllByNomDevise(@Param("nomDevise") String nomDevise, @Param("enable") int enable, Pageable pageable);

}
