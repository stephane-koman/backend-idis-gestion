package com.idis.gestion.web;

import com.idis.gestion.entities.Devise;
import com.idis.gestion.service.DeviseService;
import com.idis.gestion.service.pagination.PageDevise;
import com.idis.gestion.web.controls.HeadersControls;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api")
public class DeviseRestController {

    @Autowired
    private DeviseService deviseService;

    private HeadersControls headersControls = new HeadersControls();

    @GetMapping("/user/search-devises")
    public PageDevise searchDevises(
            @RequestHeader(value = "Authorization") String jwt,
            @RequestParam(name = "nomDevise", defaultValue = "") String nomDevise,
            @RequestParam(name = "enable", defaultValue = "1") int enable,
            Pageable pageable
    ){
        int actif = headersControls.getIsAdmin(jwt, enable);

        return deviseService.listDevises(nomDevise, actif, pageable);
    }

    @GetMapping("/user/all-devises")
    public List<Devise> allDevises(@RequestHeader(value = "Authorization") String jwt){
        int enable = 1;
        return deviseService.findAllDevises(enable);
    }

    @GetMapping("/user/take-devise")
    public Devise getDevise(@RequestParam Long id){
        return deviseService.getDeviseById(id);
    }

    @PostMapping("/user/add-devise")
    public Devise addDevise(@RequestBody Devise devise){
        if(devise.getNomDevise().equals("")) throw new RuntimeException("Le nom de la devise est obligatoire");
        return deviseService.saveDevise(devise);
    }

    @PostMapping("/user/update-devise")
    public Devise updateDevise(@RequestBody Devise devise){
        return deviseService.updateDevise(devise);
    }

    @PostMapping("/user/disable-devise")
    public boolean disableDevise(@RequestBody Devise devise){
        deviseService.disableDevise(devise.getId(), new Date());
        return true;
    }

    @PostMapping("/admin/enable-devise")
    public boolean enableDevise(@RequestBody Devise devise){
        deviseService.enableDevise(devise.getId(), new Date());
        return true;
    }

    @PostMapping("/admin/remove-devise")
    public boolean removeDevise(@RequestBody Devise devise){
        deviseService.removeDeviseById(devise.getId());
        return true;
    }
}
