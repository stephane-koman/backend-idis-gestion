package com.idis.gestion.service.impl;

import com.idis.gestion.dao.SiteRepository;
import com.idis.gestion.entities.Site;
import com.idis.gestion.service.SiteService;
import com.idis.gestion.service.pagination.PageSite;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
@Transactional
public class SiteServiceImpl implements SiteService {

    @Autowired
    private SiteRepository siteRepository;

    @Override
    public PageSite listSites(String nomSite, String codeSite, String nomPays, int enable, Pageable pageable) {
        Page<Site> sites = siteRepository.findAllByNomSite(nomSite, codeSite, nomPays, enable, pageable);
        PageSite pSites = new PageSite();
        pSites.setSites(sites.getContent());
        pSites.setPage(sites.getNumber());
        pSites.setNombreSites(sites.getNumberOfElements());
        pSites.setTotalSites((int)sites.getTotalElements());
        pSites.setTotalPages(sites.getTotalPages());
        return pSites;
    }

    @Override
    public List<Site> findAllSite(String nomSite, int enable) {
        return siteRepository.findAll(nomSite, enable);
    }

    @Override
    public Site saveSite(Site s) {
        s.setCreateAt(new Date());
        s.setUpdateAt(new Date());
        return siteRepository.save(s);
    }

    @Override
    public Site getSiteById(Long id) {
        return siteRepository.getSiteById(id);
    }

    @Override
    public Site findSiteByNomSite(String nomSite) {
        return siteRepository.findSiteByNomSite(nomSite);
    }

    @Override
    public Site updateSite(Site s) {
        Site site = siteRepository.getSiteById(s.getId());
        if(site == null) throw new RuntimeException("Ce site n'existe pas");

        site.setNomSite(s.getNomSite());
        site.setContact(s.getContact());
        site.setEmail(s.getEmail());
        site.setSiret(s.getSiret());
        site.setAdresse(s.getAdresse());
        site.setDescription(s.getDescription());
        site.setTva(s.getTva());
        site.setDevise(s.getDevise());
        site.setPays(s.getPays());
        site.setEnable(s.getEnable());
        site.setUpdateAt(new Date());
        return siteRepository.save(site);
    }

    @Override
    public void enableSite(Long id, Date date) {
        Site site = siteRepository.getSiteById(id);
        if(site == null) throw new RuntimeException("Ce site n'existe pas");
        siteRepository.enableSite(id, date);
    }

    @Override
    public void disableSite(Long id, Date date) {
        Site site = siteRepository.getSiteById(id);
        if(site == null) throw new RuntimeException("Ce site n'existe pas");
        siteRepository.disableSite(id, date);
    }

    @Override
    public void removeSiteById(Long id) {
        Site site = siteRepository.getSiteById(id);
        if(site == null) throw new RuntimeException("Ce site n'existe pas");
        siteRepository.removeSiteById(id);
    }
}
