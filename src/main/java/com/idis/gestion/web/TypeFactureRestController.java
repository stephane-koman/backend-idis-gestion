package com.idis.gestion.web;

import com.idis.gestion.entities.TypeFacture;
import com.idis.gestion.service.TypeFactureService;
import com.idis.gestion.service.pagination.PageTypeFacture;
import com.idis.gestion.web.controls.HeadersControls;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api")
public class TypeFactureRestController {

    @Autowired
    private TypeFactureService typeFactureService;

    private HeadersControls headersControls = new HeadersControls();

    @GetMapping("/user/search-types-facture")
    public PageTypeFacture searchTypesFacture(
            @RequestHeader(value = "Authorization") String jwt,
            @RequestParam(name = "nomTypeFacture", defaultValue = "") String nomTypeFacture,
            @RequestParam(name = "enable", defaultValue = "1") int enable,
            Pageable pageable
    ){
        int actif = headersControls.getIsAdmin(jwt, enable);

        return typeFactureService.listTypesFacture(nomTypeFacture, actif, pageable);
    }

    @GetMapping("/user/all-types-facture")
    public List<TypeFacture> allTypesFacture(@RequestHeader(value = "Authorization") String jwt){
        int enable = 1;
        return typeFactureService.findAllTypesFacture(enable);
    }

    @GetMapping("/user/take-type-facture")
    public TypeFacture getTypeFacture(@RequestParam Long id){
        return typeFactureService.getTypeFactureById(id);
    }

    @PostMapping("/user/add-type-facture")
    public TypeFacture addTypeFacture(@RequestBody TypeFacture typeFacture){
        if(typeFacture.getNomTypeFacture().equals("")) throw new RuntimeException("Le nom est obligatoire");
        return typeFactureService.saveTypeFacture(typeFacture);
    }

    @PostMapping("/user/update-type-facture")
    public TypeFacture updateTypeFacture(@RequestBody TypeFacture typeFacture){
        return typeFactureService.updateTypeFacture(typeFacture);
    }

    @PostMapping("/user/disable-type-facture")
    public boolean disableTypeFacture(@RequestBody TypeFacture typeFacture){
        typeFactureService.disableTypeFacture(typeFacture.getId(), new Date());
        return true;
    }

    @PostMapping("/admin/enable-type-facture")
    public boolean enableTypeFacture(@RequestBody TypeFacture typeFacture){
        typeFactureService.enableTypeFacture(typeFacture.getId(), new Date());
        return true;
    }

    @PostMapping("/admin/remove-type-facture")
    public boolean removeTypeFacture(@RequestBody TypeFacture typeFacture){
        typeFactureService.removeTypeFactureById(typeFacture.getId());
        return true;
    }
}
