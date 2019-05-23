package com.idis.gestion.service;

import com.idis.gestion.entities.Site;
import com.idis.gestion.service.pagination.PageSite;
import org.springframework.data.domain.Pageable;

import java.util.Date;
import java.util.List;

public interface SiteService {
    public PageSite listSites( String nomSite, String codeSite, String nomPays, int enable, Pageable pageable);
    public List<Site> findAllSite(String nomSite, int enable);
    public Site saveSite(Site s);
    public Site getSiteById(Long id);
    public Site findSiteByNomSite(String nomSite);
    public Site updateSite(Site s);
    public void enableSite(Long id, Date date);
    public void disableSite(Long id, Date date);
    public void removeSiteById(Long id);
}
