package com.idis.gestion.web;

import com.idis.gestion.entities.Employe;
import com.idis.gestion.entities.Site;
import com.idis.gestion.entities.Utilisateur;
import com.idis.gestion.service.PersonneService;
import com.idis.gestion.service.SiteService;
import com.idis.gestion.service.UtilisateurService;
import com.idis.gestion.service.pagination.PageSite;
import com.idis.gestion.web.controls.HeadersControls;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api")
public class SiteRestController {
    @Autowired
    private SiteService siteService;

    @Autowired
    private UtilisateurService utilisateurService;

    @Autowired
    private PersonneService personneService;

    private HeadersControls headersControls = new HeadersControls();

    @GetMapping(value = "/admin/search-sites")
    public PageSite searchSites(
            @RequestHeader(value = "Authorization") String jwt,
            @RequestParam(name = "nomSite", defaultValue = "") String nomSite,
            @RequestParam(name = "codeSite", defaultValue = "") String codeSite,
            @RequestParam(name = "nomPays", defaultValue = "") String nomPays,
            @RequestParam(name = "enable", defaultValue = "1") int enable,
            Pageable pageable
    ){
        int actif = headersControls.getIsAdmin(jwt, enable);
        return siteService.listSites(nomSite, codeSite, nomPays, actif, pageable);
    }

    @GetMapping("/user/all-sites")
    public List<Site> allSites(){
        return siteService.findAllSite("", 1);
    }

    @GetMapping("/user/all-sites-colis")
    public List<Site> allSitesColis(
            @RequestHeader(value = "Authorization") String jwt
    ){
        String username = headersControls.getUsername(jwt);
        Utilisateur utilisateur = utilisateurService.findUserByUsername(username);
        Employe employe = personneService.getEmployeById(utilisateur.getPersonne().getId());
        return siteService.findAllSite(employe.getSite().getNomSite(), 1);
    }

    @PostMapping("/admin/add-site")
    public Site addSite(@RequestBody Site site){
        Site s = siteService.findSiteByNomSite(site.getNomSite());
        if(s != null) throw new RuntimeException("Ce site existe déjà");
        return siteService.saveSite(site);
    }

    @GetMapping(value = "/admin/take-site")
    public Site getSite(
            @RequestParam(name = "id", defaultValue = "") Long id
    ){
        return siteService.getSiteById(id);
    }

    @PostMapping(value = "/admin/update-site")
    public Site updateSite(
            @RequestBody Site site
    ){
        return siteService.updateSite(site);
    }

    @PostMapping(value = "/admin/disable-site")
    public boolean disableSite(
            @RequestBody Site site
    ){
        siteService.disableSite(site.getId(), new Date());
        return true;
    }

    @PostMapping(value = "/admin/enable-site")
    public boolean enableSite(
            @RequestBody Site site
    ){
        siteService.enableSite(site.getId(), new Date());
        return true;
    }

    @PostMapping(value = "/admin/remove-site")
    public boolean removeUtilisateur(
            @RequestBody Site site
    ){
        siteService.removeSiteById(site.getId());
        return true;
    }
}
