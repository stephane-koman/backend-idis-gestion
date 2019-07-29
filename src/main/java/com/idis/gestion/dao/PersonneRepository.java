package com.idis.gestion.dao;

import com.idis.gestion.entities.Client;
import com.idis.gestion.entities.Employe;
import com.idis.gestion.entities.Personne;
import com.idis.gestion.entities.Utilisateur;
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
public interface PersonneRepository extends PagingAndSortingRepository<Personne,Long> {

    @Query("select e from Employe e " +
            "inner join e.site s " +
            "where (lower(e.matricule) like lower(concat('%',:matricule,'%'))) " +
            "and ((lower(s.nomSite) like lower(concat('%',:nomSite,'%') )) or (s.nomSite is null)) " +
            "and ((lower(e.raisonSociale) like lower(concat('%',:raisonSociale,'%') )) or (e.raisonSociale is null)) " +
            "and (e.enable = :enable or :enable = 2) " +
            "order by e.id desc ")
    public Page<Employe> listEmployes(@Param("matricule")String matricule, @Param("nomSite")String nomSite, @Param("raisonSociale")String raisonSociale,@Param("enable")int enable, Pageable pageable);

    @Query("select c from Client c " +
            "where (lower(c.codeClient) like lower(concat('%',:codeClient,'%'))) " +
            "and ((lower(c.raisonSociale) like lower(concat('%',:raisonSociale,'%') )) or (c.raisonSociale is null)) " +
            "and (c.enable = :enable or :enable = 2) order by c.id desc ")
    public Page<Client> listClients(@Param("codeClient")String codeClient, @Param("raisonSociale")String raisonSociale,@Param("enable")int enable, Pageable pageable);

    @Query("select c from Client c " +
            "where (lower(c.codeClient) like lower(concat('%',:codeClient,'%'))) " +
            "and ((lower(c.raisonSociale) like lower(concat('%',:raisonSociale,'%') )) or (c.raisonSociale is null)) order by c.id desc ")
    public List<Client> listClientsByRaisonSociale(@Param("raisonSociale")String raisonSociale);

    @Query("select e from Employe e where (e.enable = :enable or :enable = 2)")
    public List<Employe> findAllEmployes(@Param("enable")int enable);

    @Query("select c from Client c where (c.enable = :enable or :enable = 2)")
    public List<Client> findAllClients(@Param("enable")int enable);

    @Query("select c from Client c " +
            "where (c.enable = :enable or :enable = 2) " +
            "and ((lower(c.raisonSociale) like lower(concat('%',:raisonSociale,'%'))))")
    public List<Client> findClientsByRaisonSociale(@Param("raisonSociale")String raisonSociale, @Param("enable")int enable);

    @Query("select e from Employe e where e.matricule = :matricule")
    public Employe getEmployeByMatricule(@Param("matricule")String matricule);

    @Query("select e from Employe e where e.id = :id")
    public Employe getEmployeById(@Param("id")Long id);

    @Query("select c from Client c where c.id = :id")
    public Client getClientById(@Param("id")Long id);

    @Query("select c from Client c where c.codeClient = :codeClient")
    public Client getClientByCodeClient(@Param("codeClient")String codeClient);

    public Personne getPersonneById(Long id);

    @Modifying
    @Query("update Employe e set e.matricule = ?2 where e.id = ?1")
    public void updateMatricule(Long id, String matricule);

    @Modifying
    @Query("update Personne p set p.enable = 0, p.updateAt = ?2 where p.id = ?1")
    public void disablePersonne(Long id, Date date);


    @Modifying
    @Query("update Personne p set p.enable = 1, p.updateAt = ?2 where p.id = ?1")
    public void enablePersonne(Long id, Date date);

    public void removePersonneById(Long id);

    @Query("select count(e) from Employe e " +
            "where (e.enable = :enable or :enable = 2)")
    public int countEmployes(@Param("enable")int enable);

    @Query("select count(c) from Client c " +
            "where (c.enable = :enable or :enable = 2)")
    public int countClients(@Param("enable")int enable);
}
