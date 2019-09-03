package com.idis.gestion.web;

import com.idis.gestion.entities.Employe;
import com.idis.gestion.entities.Reglement;
import com.idis.gestion.entities.Utilisateur;
import com.idis.gestion.service.PersonneService;
import com.idis.gestion.service.ReglementService;
import com.idis.gestion.service.UtilisateurService;
import com.idis.gestion.service.pagination.PageReglement;
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
public class ReglementRestController {

    @Autowired
    private ReglementService reglementService;

    @Autowired
    private UtilisateurService utilisateurService;

    @Autowired
    private PersonneService personneService;

    private HeadersControls headersControls = new HeadersControls();

    @GetMapping("/user/search-reglements")
    public PageReglement searchTypesReglement(
            @RequestHeader(value = "Authorization") String jwt,
            @RequestParam(name = "nomTypeReglement", defaultValue = "") String nomTypeReglement,
            @RequestParam(name = "numeroFacture", defaultValue = "") String numeroFacture,
            @RequestParam(name = "enable", defaultValue = "1") int enable,
            Pageable pageable
    ){
        int actif = headersControls.getIsAdmin(jwt, enable);

        return reglementService.listReglements(nomTypeReglement, numeroFacture, actif, pageable);
    }

    @GetMapping("/user/take-reglement")
    public Reglement getReglement(@RequestParam Long id){
        return reglementService.getReglementById(id);
    }

    @PostMapping("/user/add-reglement")
    public Reglement addReglement(
            @RequestHeader(value = "Authorization") String jwt,
            @RequestBody Reglement reglement
    ){
        String username = headersControls.getUsername(jwt);
        Utilisateur utilisateur = utilisateurService.findUserByUsername(username);
        if(utilisateur.getPersonne() == null) throw new RuntimeException("Vous n'êtes pas autorisé");
        Employe employe = personneService.getEmployeById(utilisateur.getPersonne().getId());
        reglement.setUtilisateur(utilisateur);
        reglement.setSite(employe.getSite());
        reglement.setDevise(employe.getSite().getDevise());
        return reglementService.saveReglement(reglement);
    }

    @PostMapping("/user/update-reglement")
    public Reglement updateReglement(@RequestBody Reglement reglement){
        return reglementService.updateReglement(reglement);
    }

    @GetMapping(value = "/user/reglement-pdf")
    public @ResponseBody void getReglementPDF(
            @RequestParam(name = "numeroReglement", defaultValue = "") String numeroReglement,
            HttpServletResponse response
    ){

        try {

            response.setContentType("application/pdf");
            response.setHeader("Content-Disposition", "attachment; filename=\"paiement.pdf\"");

            final OutputStream out = response.getOutputStream();
            JasperPrint print = reglementService.exportReglementPdf(numeroReglement);
            JasperExportManager.exportReportToPdfStream(print, out);

        } catch (SQLException | IOException | JRException e) {
            e.printStackTrace();
        }

    }

    @PostMapping("/user/disable-reglement")
    public boolean disableReglement(@RequestBody Reglement reglement){
        reglementService.disableReglement(reglement.getId(), new Date());
        return true;
    }

    @PostMapping("/admin/enable-reglement")
    public boolean enableReglement(@RequestBody Reglement reglement){
        reglementService.enableReglement(reglement.getId(), new Date());
        return true;
    }

    @PostMapping("/admin/remove-reglement")
    public boolean removeReglement(@RequestBody Reglement reglement){
        reglementService.removeReglementById(reglement.getId());
        return true;
    }
}
