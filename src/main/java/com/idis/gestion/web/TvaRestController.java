package com.idis.gestion.web;

import com.idis.gestion.entities.Tva;
import com.idis.gestion.service.TvaService;
import com.idis.gestion.service.pagination.PageTva;
import com.idis.gestion.web.controls.HeadersControls;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api")
public class TvaRestController {

    @Autowired
    private TvaService tvaService;

    private HeadersControls headersControls = new HeadersControls();

    @GetMapping("/user/search-tvas")
    public PageTva searchTvas(
            @RequestHeader(value = "Authorization") String jwt,
            @RequestParam(name = "valeurTva", defaultValue = "0") String valeurTva,
            @RequestParam(name = "enable", defaultValue = "1") int enable,
            Pageable pageable
    ){
        int actif = headersControls.getIsAdmin(jwt, enable);
        double valeur = 0;

        if(!valeurTva.equals("null")){
            valeur = Double.parseDouble(valeurTva);
        }

        return tvaService.listTvas(valeur, actif, pageable);
    }

    @GetMapping("/user/all-tvas")
    public List<Tva> allTvas(@RequestHeader(value = "Authorization") String jwt){
        int enable = 1;
        return tvaService.findAllTvas(enable);
    }

    @GetMapping("/user/take-tva")
    public Tva getTva(@RequestParam Long id){
        return tvaService.getTvaById(id);
    }

    @PostMapping("/user/add-tva")
    public Tva addTva(@RequestBody Tva tva){
        if(tva.getValeurTva() < 0) throw new RuntimeException("Le valeur de la tva ne peut être négative");
        return tvaService.saveTva(tva);
    }

    @PostMapping("/user/update-tva")
    public Tva updateTva(@RequestBody Tva tva){
        return tvaService.updateTva(tva);
    }

    @PostMapping("/user/disable-tva")
    public boolean disableTva(@RequestBody Tva tva){
        tvaService.disableTva(tva.getId(), new Date());
        return true;
    }

    @PostMapping("/admin/enable-tva")
    public boolean enableTva(@RequestBody Tva tva){
        tvaService.enableTva(tva.getId(), new Date());
        return true;
    }

    @PostMapping("/admin/remove-tva")
    public boolean removeTva(@RequestBody Tva tva){
        tvaService.removeTvaById(tva.getId());
        return true;
    }
}
