package com.idis.gestion.dao;

import com.idis.gestion.entities.Fonction;
import com.idis.gestion.entities.Fonction;
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
public interface FonctionRepository extends PagingAndSortingRepository<Fonction,Long> {

    public Fonction getFonctionById(long id);

    @Modifying
    @Query("update Fonction f set f.enable = 0, f.updateAt = ?2 where f.id = ?1")
    public void disableFonction(Long id, Date date);

    @Modifying
    @Query("update Fonction f set f.enable = 1, f.updateAt = ?2 where f.id = ?1")
    public void enableFonction(Long id, Date date);

    @Modifying
    public void removeFonctionById(Long id);

    @Query("select f from Fonction f where (f.enable = :enable or :enable = 2)")
    public List<Fonction> findAll(@Param("enable") int enable);

    @Query("select f from Fonction f where lower(f.nomFonction) like lower(concat('%',:nomFonction,'%') ) and (f.enable = :enable or :enable = 2) order by f.id desc ")
    public Page<Fonction> findAllByNomFonction(@Param("nomFonction") String nomFonction, @Param("enable") int enable, Pageable pageable);

}
