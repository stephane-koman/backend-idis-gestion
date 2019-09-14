package com.idis.gestion.dao;

import com.idis.gestion.entities.TypeFacture;
import com.idis.gestion.entities.TypeFacture;
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
public interface TypeFactureRepository extends PagingAndSortingRepository<TypeFacture,Long> {

    public TypeFacture getTypeFactureById(long id);

    @Modifying
    @Query("update TypeFacture tf set tf.enable = 0, tf.updateAt = ?2 where tf.id = ?1")
    public void disableTypeFacture(Long id, Date date);

    @Modifying
    @Query("update TypeFacture tf set tf.enable = 1, tf.updateAt = ?2 where tf.id = ?1")
    public void enableTypeFacture(Long id, Date date);

    @Modifying
    public void removeTypeFactureById(Long id);

    @Query("select tf from TypeFacture tf where (tf.enable = :enable or :enable = 2)")
    public List<TypeFacture> findAll(@Param("enable") int enable);

    @Query("select tf from TypeFacture tf where lower(tf.nomTypeFacture) like lower(concat('%',:nomTypeFacture,'%') ) and (tf.enable = :enable or :enable = 2) order by tf.id desc ")
    public Page<TypeFacture> listeTypesFacture(@Param("nomTypeFacture") String nomTypeFacture, @Param("enable") int enable, Pageable pageable);

}
