package com.idis.gestion.dao;

import com.idis.gestion.entities.DetailsColis;
import com.idis.gestion.entities.LigneFacture;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Transactional
public interface LigneFactureRepository extends JpaRepository<LigneFacture, Long> {

    @Query("select lf from LigneFacture lf where (lf.enable = :enable or :enable = 2)")
    List<LigneFacture> findAllLigneFactures(@Param("enable") int enable);

    public LigneFacture getLigneFactureById(long id);

    public LigneFacture getLigneFactureByDetailsColis(DetailsColis detailsColis);

    @Modifying
    @Query("update LigneFacture lf set lf.enable = 0, lf.updateAt = ?2 where lf.id = ?1")
    public void disableLigneFacture(Long id, Date date);

    @Modifying
    @Query("update LigneFacture lf set lf.enable = 1, lf.updateAt = ?2 where lf.id = ?1")
    public void enableLigneFacture(Long id, Date date);

    public void removeLigneFactureById(Long id);
}
