package com.idis.gestion.web;

import com.idis.gestion.entities.Pays;
import com.idis.gestion.entities.TypeReglement;
import com.idis.gestion.service.PaysService;
import com.idis.gestion.service.TypeReglementService;
import com.idis.gestion.service.pagination.PagePays;
import com.idis.gestion.service.pagination.PageTypeReglement;
import com.idis.gestion.web.controls.HeadersControls;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api")
public class TypeReglementRestController {

    @Autowired
    private TypeReglementService typeReglementService;

    private HeadersControls headersControls = new HeadersControls();

    @GetMapping("/user/search-types-reglement")
    public PageTypeReglement searchTypesReglement(
            @RequestHeader(value = "Authorization") String jwt,
            @RequestParam(name = "nomTypeReglement", defaultValue = "") String nomTypeReglement,
            @RequestParam(name = "enable", defaultValue = "1") int enable,
            Pageable pageable
    ){
        int actif = headersControls.getIsAdmin(jwt, enable);

        return typeReglementService.listTypesReglement(nomTypeReglement, actif, pageable);
    }

    @GetMapping("/user/all-types-reglement")
    public List<TypeReglement> allTypesReglement(@RequestHeader(value = "Authorization") String jwt){
        int enable = 1;
        return typeReglementService.findAllTypesReglement(enable);
    }

    @GetMapping("/user/take-type-reglement")
    public TypeReglement getTypeReglement(@RequestParam Long id){
        return typeReglementService.getTypeReglementById(id);
    }

    @PostMapping("/user/add-type-reglement")
    public TypeReglement addTypeReglement(@RequestBody TypeReglement typeReglement){
        if(typeReglement.getNomTypeReglement().equals("")) throw new RuntimeException("Le libelle est obligatoire");
        return typeReglementService.saveTypeReglement(typeReglement);
    }

    @PostMapping("/user/update-type-reglement")
    public TypeReglement updateTypeReglement(@RequestBody TypeReglement typeReglement){
        return typeReglementService.updateTypeReglement(typeReglement);
    }

    @PostMapping("/user/disable-type-reglement")
    public boolean disableTypeReglement(@RequestBody TypeReglement typeReglement){
        typeReglementService.disableTypeReglement(typeReglement.getId(), new Date());
        return true;
    }

    @PostMapping("/admin/enable-type-reglement")
    public boolean enableTypeReglement(@RequestBody TypeReglement typeReglement){
        typeReglementService.enableTypeReglement(typeReglement.getId(), new Date());
        return true;
    }

    @PostMapping("/admin/remove-type-reglement")
    public boolean removeTypeReglement(@RequestBody TypeReglement typeReglement){
        typeReglementService.removeTypeReglementById(typeReglement.getId());
        return true;
    }
}
