package com.idis.gestion.dao;

import com.idis.gestion.entities.Pays;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Transactional
public interface PaysRepository extends PagingAndSortingRepository<Pays,Long> {

    public Pays getPaysById(long id);

    @Modifying
    @Query("update Pays p set p.enable = 0, p.updateAt = ?2 where p.id = ?1")
    public void disablePays(Long id, Date date);

    @Modifying
    @Query("update Pays p set p.enable = 1, p.updateAt = ?2 where p.id = ?1")
    public void enablePays(Long id, Date date);

    @Modifying
    public void removePaysById(Long id);

    @Query("select p from Pays p where (p.enable = :enable or :enable = 2)")
    public List<Pays> findAll(@Param("enable") int enable);

    @Query("select p from Pays p where lower(p.nomPays) like lower(concat('%',:nomPays,'%') ) and (p.enable = :enable or :enable = 2) order by p.id desc ")
    public Page<Pays> findAllByNomPays(@Param("nomPays")String nomPays, @Param("enable")int enable, Pageable pageable);

}
