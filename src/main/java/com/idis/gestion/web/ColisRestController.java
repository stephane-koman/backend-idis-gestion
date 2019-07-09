package com.idis.gestion.web;

import com.idis.gestion.entities.*;
import com.idis.gestion.service.ColisService;
import com.idis.gestion.service.PersonneService;
import com.idis.gestion.service.UtilisateurService;
import com.idis.gestion.service.pagination.PageColis;
import com.idis.gestion.web.controls.HeadersControls;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperPrint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api")
public class ColisRestController {

    @Autowired
    private ColisService colisService;

    @Autowired
    private UtilisateurService utilisateurService;

    @Autowired
    private PersonneService personneService;

    private HeadersControls headersControls = new HeadersControls();

    @PostMapping(value = "/user/add-colis")
    public Colis addColis(
            @RequestHeader(value = "Authorization") String jwt,
            @RequestBody Colis colis
    ){
        String username = headersControls.getUsername(jwt);
        Utilisateur utilisateur = utilisateurService.findUserByUsername(username);

        colis.setUtilisateur(utilisateur);

        if(utilisateur.getPersonne() == null) throw new RuntimeException("Vous n'êtes pas autorisé");
        Employe employe = personneService.getEmployeById(utilisateur.getPersonne().getId());
        colis.setSiteExpediteur(employe.getSite());
        colis.setDevise(employe.getSite().getDevise());
        return colisService.saveColis(colis, employe.getSite().getCodeSite());
    }

    @GetMapping(value = "/admin/search-colis")
    public PageColis searchColis(
            @RequestHeader(value = "Authorization") String jwt,
            @RequestParam(name = "reference", defaultValue = "") String reference,
            @RequestParam(name = "nomClient", defaultValue = "") String nomClient,
            @RequestParam(name = "nomDestinataire", defaultValue = "") String nomDestinataire,
            @RequestParam(name = "enable", defaultValue = "1") int enable,
            Pageable pageable
    ){
        int actif = headersControls.getIsAdmin(jwt, enable);
        return colisService.listColis(reference, nomClient, nomDestinataire, actif, pageable);
    }

    @GetMapping(value = "/user/send/search-colis")
    public PageColis sendSearchColis(
            @RequestHeader(value = "Authorization") String jwt,
            @RequestParam(name = "reference", defaultValue = "") String reference,
            @RequestParam(name = "nomClient", defaultValue = "") String nomClient,
            @RequestParam(name = "nomDestinataire", defaultValue = "") String nomDestinataire,
            @RequestParam(name = "enable", defaultValue = "1") int enable,
            Pageable pageable
    ){
        int actif = headersControls.getIsAdmin(jwt, enable);
        String username = headersControls.getUsername(jwt);
        Utilisateur utilisateur = utilisateurService.findUserByUsername(username);
        Employe employe = personneService.getEmployeById(utilisateur.getPersonne().getId());
        return colisService.sendListColis(reference, nomClient, nomDestinataire, employe.getSite().getNomSite(), actif, pageable);
    }

    @GetMapping(value = "/user/send/all-colis")
    public List<Colis> allSendColis(
            @RequestHeader(value = "Authorization") String jwt
    ){
        String username = headersControls.getUsername(jwt);
        Utilisateur utilisateur = utilisateurService.findUserByUsername(username);
        Employe employe = personneService.getEmployeById(utilisateur.getPersonne().getId());
        return colisService.findAllSendColis(employe.getSite().getNomSite(), 1);
    }

    @GetMapping(value = "/user/receive/search-colis")
    public PageColis receiveSearchColis(
            @RequestHeader(value = "Authorization") String jwt,
            @RequestParam(name = "reference", defaultValue = "") String reference,
            @RequestParam(name = "nomClient", defaultValue = "") String nomClient,
            @RequestParam(name = "nomDestinataire", defaultValue = "") String nomDestinataire,
            @RequestParam(name = "enable", defaultValue = "1") int enable,
            Pageable pageable
    ){
        int actif = headersControls.getIsAdmin(jwt, enable);
        String username = headersControls.getUsername(jwt);
        Utilisateur utilisateur = utilisateurService.findUserByUsername(username);
        Employe employe = personneService.getEmployeById(utilisateur.getPersonne().getId());
        return colisService.receiveListColis(reference, nomClient, nomDestinataire, employe.getSite().getNomSite(), actif, pageable);
    }

    @GetMapping(value = "/client/search-colis")
    public PageColis clientSearchColis(
            @RequestHeader(value = "Authorization") String jwt,
            @RequestParam(name = "reference", defaultValue = "") String reference,
            @RequestParam(name = "nomDestinataire", defaultValue = "") String nomDestinataire,
            Pageable pageable
    ){
        String username = headersControls.getUsername(jwt);
        Utilisateur utilisateur = utilisateurService.findUserByUsername(username);
        Client client = personneService.getClientById(utilisateur.getPersonne().getId());
        return colisService.clientListColis(reference, client.getId(), nomDestinataire, 1, pageable);
    }

    @GetMapping(value = "/user/take-colis")
    public Colis getColis(
            @RequestParam(name = "reference", defaultValue = "") String reference
    ){
        return colisService.findColisByReference(reference);
    }

    @GetMapping(value = "/user/qrcode-pdf")
    public @ResponseBody void getQrCodePDF(
            @RequestParam(name = "referenceColis", defaultValue = "") String referenceColis,
            HttpServletResponse response
    ){

        try {

            response.setContentType("application/pdf");
            response.setHeader("Content-Disposition", "attachment; filename=\"qrcode.pdf\"");

            final OutputStream out = response.getOutputStream();
            JasperPrint print = colisService.exportQrCodePdf(referenceColis);
            JasperExportManager.exportReportToPdfStream(print, out);

        } catch (SQLException | IOException | JRException e) {
            e.printStackTrace();
        }

    }

    @PostMapping(value = "/user/update-colis")
    public Colis updateColis(
            @RequestBody Colis colis
    ){
        return colisService.updateColis(colis);
    }

    @PostMapping(value = "/user/disable-colis")
    public boolean disableColis(
            @RequestBody Colis colis
    ){
        colisService.disableColis(colis.getReference(), new Date());
        return true;
    }

    @PostMapping(value = "/admin/enable-colis")
    public boolean enableColis(
            @RequestBody Colis colis
    ){
        colisService.enableColis(colis.getReference(), new Date());
        return true;
    }

    @PostMapping(value = "/admin/remove-colis")
    public boolean removeColis(
            @RequestBody Colis colis
    ){
        colisService.removeColisByReference(colis.getReference());
        return true;
    }

    //-------------------------- OTHER -------------------------------
    @PostMapping(value = "/user/add-enregistrement-colis")
    public Colis addEnregistrementColis(
            @RequestHeader(value = "Authorization") String jwt,
            @RequestBody Colis colis
    ){
        String username = headersControls.getUsername(jwt);
        Utilisateur utilisateur = utilisateurService.findUserByUsername(username);
        return colisService.addEnregistrementColis(colis);
    }

    @PostMapping(value = "/user/update-enregistrement-colis")
    public Colis updateEnregistrementColis(
            @RequestHeader(value = "Authorization") String jwt,
            @RequestBody Colis colis
    ){
        return colisService.updateEnregistrementColis(colis);
    }

    @PostMapping(value = "/user/add-expedition-colis")
    public Colis addExpeditionColis(
            @RequestHeader(value = "Authorization") String jwt,
            @RequestBody Colis colis
    ){
        String username = headersControls.getUsername(jwt);
        Utilisateur utilisateur = utilisateurService.findUserByUsername(username);
        return colisService.addExpeditionColis(colis, utilisateur);
    }

    @PostMapping(value = "/user/update-expedition-colis")
    public Colis updateExpeditionColis(
            @RequestHeader(value = "Authorization") String jwt,
            @RequestBody Colis colis
    ){
        return colisService.updateExpeditionColis(colis);
    }

    @PostMapping(value = "/user/add-arrivee-colis")
    public Colis addArriveeColis(
            @RequestHeader(value = "Authorization") String jwt,
            @RequestBody Colis colis
    ){
        String username = headersControls.getUsername(jwt);
        Utilisateur utilisateur = utilisateurService.findUserByUsername(username);
        return colisService.addArriveeColis(colis, utilisateur);
    }

    @PostMapping(value = "/user/update-arriveee-colis")
    public Colis updateArriveeColis(
            @RequestHeader(value = "Authorization") String jwt,
            @RequestBody Colis colis
    ){
        return colisService.updateArriveeColis(colis);
    }

    @PostMapping(value = "/user/add-reception-colis")
    public Colis addReceptionColis(
            @RequestHeader(value = "Authorization") String jwt,
            @RequestBody Colis colis
    ){
        String username = headersControls.getUsername(jwt);
        Utilisateur utilisateur = utilisateurService.findUserByUsername(username);
        return colisService.addReceptionColis(colis, utilisateur);
    }

    @PostMapping(value = "/user/update-reception-colis")
    public Colis updateReceptionColis(
            @RequestHeader(value = "Authorization") String jwt,
            @RequestBody Colis colis
    ){
        return colisService.updateReceptionColis(colis);
    }

    @PostMapping(value = "/user/add-livraison-colis")
    public Colis addLivraisonColis(
            @RequestHeader(value = "Authorization") String jwt,
            @RequestBody Colis colis
    ){
        String username = headersControls.getUsername(jwt);
        Utilisateur utilisateur = utilisateurService.findUserByUsername(username);
        return colisService.addLivraisonColis(colis, utilisateur);
    }

    @PostMapping(value = "/user/update-livraison-colis")
    public Colis updateLivraisonColis(
            @RequestBody Colis colis
    ){
        return colisService.updateLivraisonColis(colis);
    }

    // ----------------------- DASHBOARD -------------------------------
    @GetMapping(value = "/user/count-send-colis")
    public String countSendColis(
            @RequestHeader(value = "Authorization") String jwt
    ){
        int actif = headersControls.getIsAdmin(jwt, 1);
        String username = headersControls.getUsername(jwt);
        Utilisateur utilisateur = utilisateurService.findUserByUsername(username);
        Employe employe = personneService.getEmployeById(utilisateur.getPersonne().getId());
        return colisService.countSendColis(employe.getSite().getId(), actif);
    }

    @GetMapping(value = "/user/count-receive-colis")
    public String countReceiveColis(
            @RequestHeader(value = "Authorization") String jwt
    ){
        int actif = headersControls.getIsAdmin(jwt, 1);
        String username = headersControls.getUsername(jwt);
        Utilisateur utilisateur = utilisateurService.findUserByUsername(username);
        Employe employe = personneService.getEmployeById(utilisateur.getPersonne().getId());
        return colisService.countReceiveColis(employe.getSite().getId(), actif);
    }

    @GetMapping(value = "/user/count-client-colis")
    public String countClientColis(
            @RequestHeader(value = "Authorization") String jwt
    ){
        int actif = headersControls.getIsAdmin(jwt, 1);
        String username = headersControls.getUsername(jwt);
        Utilisateur utilisateur = utilisateurService.findUserByUsername(username);
        Client client = personneService.getClientById(utilisateur.getPersonne().getId());
        return colisService.countClientColis(client.getId(), actif);
    }
}
