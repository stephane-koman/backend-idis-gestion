package com.idis.gestion.web;

import com.idis.gestion.entities.Client;
import com.idis.gestion.entities.Employe;
import com.idis.gestion.entities.Personne;
import com.idis.gestion.service.PersonneService;
import com.idis.gestion.service.pagination.PageClient;
import com.idis.gestion.service.pagination.PageEmploye;
import com.idis.gestion.web.controls.HeadersControls;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api")
public class PersonneRestController {

    @Autowired
    private PersonneService personneService;

    private HeadersControls headersControls = new HeadersControls();

    //----------------------------- EMPLOYE --------------------------------
    @GetMapping(value = "/user/search-employes")
    public PageEmploye findAllEmployes(
            @RequestHeader(value = "Authorization") String jwt,
            @RequestParam(name = "matricule", defaultValue = "") String matricule,
            @RequestParam(name = "nomSite", defaultValue = "") String nomSite,
            @RequestParam(name = "raisonSociale", defaultValue = "") String raisonSociale,
            @RequestParam(name = "enable", defaultValue = "1") int enable,
            Pageable pageable
    ){
        int actif = headersControls.getIsAdmin(jwt, enable);
        return personneService.listEmployes(matricule, nomSite, raisonSociale, actif, pageable);
    }

    @GetMapping("/user/all-employes")
    public List<Employe> allEmployes(@RequestHeader(value = "Authorization") String jwt){
        int enable = 1;
        return personneService.findAllEmployes(enable);
    }

    @PostMapping("/user/add-employe")
    public Employe addEmploye(
            @RequestBody Employe employe
    ){
        return personneService.saveEmploye(employe);
    }

    @PostMapping("/user/update-employe")
    public Employe updateEmploye(
            @RequestBody Employe employe
    ){
        return personneService.updateEmploye(employe);
    }

    @GetMapping("/user/take-employe")
    public Employe getEmploye(@RequestParam String matricule){
        return personneService.getEmployeByMatricule(matricule);
    }


    @PostMapping(value = "/user/disable-employe")
    public boolean disableEmploye(
            @RequestBody Employe employe
            ){
        personneService.disablePersonne(employe.getId(), new Date());
        return true;
    }

    @PostMapping(value = "/admin/enable-employe")
    public boolean enableEmploye(
            @RequestBody Employe employe
    ){
        personneService.enablePersonne(employe.getId(), new Date());
        return true;
    }

    @PostMapping(value = "/admin/remove-employe")
    public boolean removeEmploye(
            @RequestBody Employe employe
    ){
        personneService.removePersonneById(employe.getId());
        return true;
    }

    //----------------------------- CLIENT --------------------------------

    @GetMapping(value = "/user/search-clients")
    public PageClient findAllClients(
            @RequestHeader(value = "Authorization") String jwt,
            @RequestParam(name = "codeClient", defaultValue = "") String codeClient,
            @RequestParam(name = "raisonSociale", defaultValue = "") String raisonSociale,
            @RequestParam(name = "enable", defaultValue = "1") int enable,
            Pageable pageable
    ){
        int actif = headersControls.getIsAdmin(jwt, enable);
        return personneService.listClients(codeClient, raisonSociale, actif, pageable);
    }

    @GetMapping(value = "/user/get-clients")
    public List<Client> getClientsByName(
            @RequestParam(name = "raisonSociale", defaultValue = "") String raisonSociale
    ){
        return personneService.listClientsByRaisonSociale(raisonSociale);
    }


    @GetMapping("/user/all-clients")
    public List<Client> allClients(@RequestHeader(value = "Authorization") String jwt){
        int actif = headersControls.getIsAdmin(jwt, 1);
        return personneService.findAllClients(actif);
    }

    @GetMapping("/user/clients-by-raisonsociale")
    public List<Client> clientsByRaisonSociale(
            @RequestHeader(value = "Authorization") String jwt,
            @RequestParam String raisonSociale
    ){
        int actif = headersControls.getIsAdmin(jwt, 1);
        return personneService.findClientsByRaisonSociale(raisonSociale, actif);
    }

    @PostMapping("/user/add-client")
    public Client addClient(
            @RequestBody Client client
    ){
        return personneService.saveClient(client);
    }

    @PostMapping("/user/update-client")
    public Client updateClient(
            @RequestBody Client client
    ){
        return personneService.updateClient(client);
    }

    @GetMapping("/user/take-client")
    public Client getClient(@RequestParam String codeClient){
        return personneService.getClientByCodeClient(codeClient);
    }

    @PostMapping(value = "/user/disable-client")
    public boolean disablePersonne(
            @RequestBody Client client
    ){
        personneService.disablePersonne(client.getId(), new Date());
        return true;
    }

    @PostMapping(value = "/admin/enable-client")
    public boolean enablePersonne(
            @RequestBody Client client
    ){
        personneService.enablePersonne(client.getId(), new Date());
        return true;
    }

    @PostMapping(value = "/admin/remove-client")
    public boolean removePersonne(
            @RequestBody Client client
    ){
        personneService.removePersonneById(client.getId());
        return true;
    }

    @GetMapping(value = "/user/count-employes")
    public String countEmployes(
            @RequestHeader(value = "Authorization") String jwt
    ){
        int actif = headersControls.getIsAdmin(jwt, 1);
        return personneService.countEmployes(actif);
    }

    @GetMapping(value = "/user/count-clients")
    public String countClients(
            @RequestHeader(value = "Authorization") String jwt
    ){
        int actif = headersControls.getIsAdmin(jwt, 1);
        return personneService.countClients(actif);
    }

}
