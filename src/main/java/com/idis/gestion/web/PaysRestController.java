package com.idis.gestion.web;

import com.idis.gestion.entities.Pays;
import com.idis.gestion.entities.Utilisateur;
import com.idis.gestion.service.PaysService;
import com.idis.gestion.service.UtilisateurService;
import com.idis.gestion.service.pagination.PagePays;
import com.idis.gestion.web.controls.HeadersControls;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api")
public class PaysRestController {

    @Autowired
    private PaysService paysService;

    private HeadersControls headersControls = new HeadersControls();

    @GetMapping("/user/search-pays")
    public PagePays searchPays(
            @RequestHeader(value = "Authorization") String jwt,
            @RequestParam(name = "nomPays", defaultValue = "") String nomPays,
            @RequestParam(name = "enable", defaultValue = "1") int enable,
            Pageable pageable
    ){
        int actif = headersControls.getIsAdmin(jwt, enable);

        return paysService.listPays(nomPays, actif, pageable);
    }

    @GetMapping("/user/all-pays")
    public List<Pays> allPays(@RequestHeader(value = "Authorization") String jwt){
        int enable = 1;
        return paysService.findAllPays(enable);
    }

    @GetMapping("/user/take-pays")
    public Pays getPays(@RequestParam Long id){
        return paysService.getPaysById(id);
    }

    @PostMapping("/user/add-pays")
    public Pays addPays(@RequestBody Pays pays){
        if(pays.getNomPays().equals("")) throw new RuntimeException("Le nom du pays est obligatoire");
        return paysService.savePays(pays);
    }

    @PostMapping("/user/update-pays")
    public Pays updatePays(@RequestBody Pays pays){
        return paysService.updatePays(pays);
    }

    @PostMapping("/user/disable-pays")
    public boolean disablePays(@RequestBody Pays pays){
        paysService.disablePays(pays.getId(), new Date());
        return true;
    }

    @PostMapping("/admin/enable-pays")
    public boolean enablePays(@RequestBody Pays pays){
        paysService.enablePays(pays.getId(), new Date());
        return true;
    }

    @PostMapping("/admin/remove-pays")
    public boolean removePays(@RequestBody Pays pays){
        paysService.removePaysById(pays.getId());
        return true;
    }
}
