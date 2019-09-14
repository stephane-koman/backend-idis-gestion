package com.idis.gestion.dao;

import com.idis.gestion.entities.Site;
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
public interface SiteRepository extends PagingAndSortingRepository<Site,Long> {
    public Site getSiteById(long id);

    @Query("select DISTINCT s from Site s inner join s.pays p " +
            "where (LOWER(s.nomSite) LIKE LOWER(CONCAT('%',:nomSite,'%'))) " +
            "and (LOWER(s.codeSite) LIKE LOWER(CONCAT('%',:codeSite,'%'))) " +
            "and (LOWER(p.nomPays) LIKE LOWER(CONCAT('%',:nomPays,'%'))) " +
            "and (s.enable = :enable or :enable = 2) order by s.id desc")
    public Page<Site> findAllByNomSite(@Param("nomSite")String nomSite, @Param("codeSite")String codeSite, @Param("nomPays")String nomPays, @Param("enable")int enable, Pageable pageable);

    public Site findSiteByNomSite(String nomSite);

    @Modifying
    @Query("update Site s set s.enable = 0, s.updateAt = ?2 where s.id = ?1")
    public void disableSite(Long id, Date date);

    @Modifying
    @Query("update Site s set s.enable = 1, s.updateAt = ?2 where s.id = ?1")
    public void enableSite(Long id, Date date);

    @Modifying
    public void removeSiteById(Long id);

    @Query("select s from Site s where (s.nomSite <> :nomSite ) and (s.enable = :enable or :enable = 2)")
    public List<Site> findAll(@Param("nomSite") String nomSite, @Param("enable") int enable);

}
