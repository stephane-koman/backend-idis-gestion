package com.idis.gestion.dao;

import com.idis.gestion.entities.DetailsColis;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Transactional
public interface DetailsColisRepository extends JpaRepository<DetailsColis, Long> {

    @Query("select d from DetailsColis d where (d.enable = :enable or :enable = 2)")
    List<DetailsColis> findAllDetailsColis(@Param("enable") int enable);

    public DetailsColis getDetailsColisById(long id);

    @Modifying
    @Query("update DetailsColis d set d.enable = 0, d.updateAt = ?2 where d.id = ?1")
    public void disableDetailsColis(Long id, Date date);

    @Modifying
    @Query("update DetailsColis d set d.enable = 1, d.updateAt = ?2 where d.id = ?1")
    public void enableDetailsColis(Long id, Date date);

    public void removeDetailsColisById(Long id);

}
