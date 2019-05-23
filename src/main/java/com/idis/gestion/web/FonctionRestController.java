package com.idis.gestion.web;

import com.idis.gestion.entities.Fonction;
import com.idis.gestion.service.FonctionService;
import com.idis.gestion.service.pagination.PageFonction;
import com.idis.gestion.web.controls.HeadersControls;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api")
public class FonctionRestController {

    @Autowired
    private FonctionService fonctionService;

    private HeadersControls headersControls = new HeadersControls();

    @GetMapping("/user/search-fonctions")
    public PageFonction searchFonction(
            @RequestHeader(value = "Authorization") String jwt,
            @RequestParam(name = "nomFonction", defaultValue = "") String nomFonction,
            @RequestParam(name = "enable", defaultValue = "1") int enable,
            Pageable pageable
    ){
        int actif = headersControls.getIsAdmin(jwt, enable);

        return fonctionService.listFonctions(nomFonction, actif, pageable);
    }

    @GetMapping("/user/all-fonctions")
    public List<Fonction> allFonction(@RequestHeader(value = "Authorization") String jwt){
        int enable = 1;
        return fonctionService.findAllFonctions(enable);
    }

    @GetMapping("/user/take-fonction")
    public Fonction getFonction(@RequestParam Long id){
        return fonctionService.getFonctionById(id);
    }

    @PostMapping("/user/add-fonction")
    public Fonction addFonction(@RequestBody Fonction fonction){
        if(fonction.getNomFonction().equals("")) throw new RuntimeException("Le nom de la fonction est obligatoire");
        return fonctionService.saveFonction(fonction);
    }

    @PostMapping("/user/update-fonction")
    public Fonction updateFonction(@RequestBody Fonction fonction){
        return fonctionService.updateFonction(fonction);
    }

    @PostMapping("/user/disable-fonction")
    public boolean disableFonction(@RequestBody Fonction fonction){
        fonctionService.disableFonction(fonction.getId(), new Date());
        return true;
    }

    @PostMapping("/admin/enable-fonction")
    public boolean enableFonction(@RequestBody Fonction fonction){
        fonctionService.enableFonction(fonction.getId(), new Date());
        return true;
    }

    @PostMapping("/admin/remove-fonction")
    public boolean removeFonction(@RequestBody Fonction fonction){
        fonctionService.removeFonctionById(fonction.getId());
        return true;
    }
}
