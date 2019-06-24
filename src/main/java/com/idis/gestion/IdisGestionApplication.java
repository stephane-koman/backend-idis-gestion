package com.idis.gestion;

import com.idis.gestion.dao.ColisRepository;
import com.idis.gestion.dao.PersonneRepository;
import com.idis.gestion.dao.RoleRepository;
import com.idis.gestion.dao.UtilisateurRepository;
import com.idis.gestion.entities.*;
import com.idis.gestion.entities.generator.EmpMatrGenerator;
import com.idis.gestion.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Date;

@SpringBootApplication
public class IdisGestionApplication extends SpringBootServletInitializer implements CommandLineRunner {

    @Autowired
    private PersonneRepository personneRepository;

    @Autowired
    private PersonneService personneService;

    @Autowired
    private ColisRepository colisRepository;

    @Autowired
    private UtilisateurRepository utilisateurRepository;

    @Autowired
    private UtilisateurService utilisateurService;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PaysService paysService;

    @Autowired
    private TvaService tvaService;

    @Autowired
    private DeviseService deviseService;

    @Autowired
    private SiteService siteService;

    public static void main(String[] args) {
        SpringApplication.run(IdisGestionApplication.class, args);
    }

    @Bean
    public BCryptPasswordEncoder getBCPE(){
        return new BCryptPasswordEncoder();
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(IdisGestionApplication.class);
    }

    @Override
    public void run(String... args) throws Exception {
        /*Pays pays1 = paysService.savePays(new Pays("Côte d'ivoire", "CI", new Date(), new Date(), 1));
        Pays pays2 = paysService.savePays(new Pays("France", "FR", new Date(), new Date(), 1));

        Tva tva1 = tvaService.saveTva(new Tva(0.18,"TVA Côte d'Ivoire", new Date(), new Date(), 1));
        Tva tva2 = tvaService.saveTva(new Tva(0.20,"TVA France", new Date(), new Date(), 1));

        Devise devise1 = deviseService.saveDevise(new Devise("F CFA", 1, "Franc CFA Devise Afrique de l'ouest Francophone", new Date(), new Date(), 1));
        Devise devise2 = deviseService.saveDevise(new Devise("€", 655, "Euro Devise Européenne", new Date(), new Date(), 1));

        Site site1 = siteService.saveSite(new Site("IDIS Abidjan", "ABJ", "22456789","Plateau", "Site d'Abidjan", new Date(), new Date(), 1, tva1, devise1, pays1, null));
        Site site2 = siteService.saveSite(new Site("IDIS Paris", "PRS", "33556677889900","Louis 12", "Site Paris", new Date(), new Date(), 1, tva2, devise2, pays2, null));


        Employe emp = new Employe();

        emp.setSite(site1);
        emp.setRaisonSociale("KOMAN STEPHANE");

        Employe employe = personneRepository.save(emp);

        EmpMatrGenerator empMatrGenerator = new EmpMatrGenerator();

        String matricule = empMatrGenerator.generate(employe.getId());

        personneRepository.updateMatricule(employe.getId(), matricule);

        roleRepository.save(new Role("ADMIN", new Date(), new Date(), 1));
        roleRepository.save(new Role("CLIENT", new Date(), new Date(), 1));
        roleRepository.save(new Role("EMPLOYE", new Date(), new Date(), 1));

        utilisateurService.saveUser(new Utilisateur("admin", "1234", new Date(), new Date(), 1, employe));

        utilisateurService.addRoleToUser("admin", "ADMIN");

        personneService.saveClient(new Client("KOFFI ANDRE", "47897262", "stefchris2@gmail.com", "12 RUE PARIS", new Date(), new Date(), 1, null));
    */}

}
