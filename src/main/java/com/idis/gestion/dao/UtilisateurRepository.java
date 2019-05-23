package com.idis.gestion.dao;

import com.idis.gestion.entities.Utilisateur;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Transactional
public interface UtilisateurRepository extends PagingAndSortingRepository<Utilisateur,Long> {


    @Query("select DISTINCT u from Utilisateur u " +
            "inner join u.roles r " +
            "where LOWER(u.username) like LOWER(CONCAT('%',:username,'%')) " +
            "and (LOWER(r.roleName) like LOWER(CONCAT('%',:role,'%'))) " +
            "and (u.enable = :enable or :enable = 2) order by u.id desc ")
    public Page<Utilisateur> listUsers(@Param("username") String username, @Param("role") String role, @Param("enable") int enable, Pageable pageable);

    @Query("select u from Utilisateur u where u.username = ?1 and u.enable = 1")
    public Utilisateur findByUsername(String username);

    @Query("select u from Utilisateur u where u.personne.id = ?1")
    public Utilisateur findUtilisateurByPersonne(Long id);

    public Utilisateur getUtilisateurById(Long id);

    @Modifying
    @Query("update Utilisateur u set u.enable = 0, u.updateAt = ?2 where u.id = ?1")
    public void disableUser(Long id, Date date);

    @Modifying
    @Query("update Utilisateur u set u.enable = 1, u.updateAt = ?2 where u.id = ?1")
    public void enableUser(Long id, Date date);

    public void removeUtilisateurById(Long id);
}
