package com.idis.gestion.web;

import com.idis.gestion.entities.DomaineActivite;
import com.idis.gestion.entities.Utilisateur;
import com.idis.gestion.service.DomaineActiviteService;
import com.idis.gestion.service.pagination.PageDomaineActivite;
import com.idis.gestion.web.controls.HeadersControls;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api")
public class DomaineActiviteRestController {

    @Autowired
    private DomaineActiviteService domaineActiviteService;

    private HeadersControls headersControls = new HeadersControls();

    @GetMapping("/user/search-domaine-activite")
    public PageDomaineActivite searchDomaineActivite(
            @RequestHeader(value = "Authorization") String jwt,
            @RequestParam(name = "nomDomaineActivite", defaultValue = "") String nomDomaineActivite,
            @RequestParam(name = "enable", defaultValue = "1") int enable,
            Pageable pageable
    ){
        int actif = headersControls.getIsAdmin(jwt, enable);

        return domaineActiviteService.listDomaineActivite(nomDomaineActivite, actif, pageable);
    }

    @GetMapping(value = "/user/domaine-activite-by-code")
    public List<DomaineActivite> findDomaineActiviteByCode(
            @RequestHeader(value = "Authorization") String jwt,
            @RequestParam(name = "code", defaultValue = "") String code,
            @RequestParam(name = "enable", defaultValue = "1") int enable
    ){
        int actif = headersControls.getIsAdmin(jwt, enable);
        return domaineActiviteService.findDomainesActiviteByCode(code, 1);
    }

    @GetMapping("/user/all-domaine-activite")
    public List<DomaineActivite> allDomaineActivite(@RequestHeader(value = "Authorization") String jwt){
        int enable = 1;
        return domaineActiviteService.findAllDomaineActivite(enable);
    }

    @GetMapping("/user/take-domaine-activite")
    public DomaineActivite getDomaineActivite(@RequestParam Long id){
        return domaineActiviteService.getDomaineActiviteById(id);
    }

    @PostMapping("/user/add-domaine-activite")
    public DomaineActivite addDomaineActivite(@RequestBody DomaineActivite domaineActivite){
        if(domaineActivite.getCode().equals("")) throw new RuntimeException("Le code est obligatoire");
        return domaineActiviteService.saveDomaineActivite(domaineActivite);
    }

    @PostMapping("/user/update-domaine-activite")
    public DomaineActivite updateDomaineActivite(@RequestBody DomaineActivite domaineActivite){
        return domaineActiviteService.updateDomaineActivite(domaineActivite);
    }

    @PostMapping("/user/disable-domaine-activite")
    public boolean disableDomaineActivite(@RequestBody DomaineActivite domaineActivite){
        domaineActiviteService.disableDomaineActivite(domaineActivite.getId(), new Date());
        return true;
    }

    @PostMapping("/admin/enable-domaine-activite")
    public boolean enableDomaineActivite(@RequestBody DomaineActivite domaineActivite){
        domaineActiviteService.enableDomaineActivite(domaineActivite.getId(), new Date());
        return true;
    }

    @PostMapping("/admin/remove-domaine-activite")
    public boolean removeDomaineActivite(@RequestBody DomaineActivite domaineActivite){
        domaineActiviteService.removeDomaineActiviteById(domaineActivite.getId());
        return true;
    }
}
