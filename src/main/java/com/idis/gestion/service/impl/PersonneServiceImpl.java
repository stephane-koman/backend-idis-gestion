package com.idis.gestion.service.impl;

import com.idis.gestion.dao.PersonneRepository;
import com.idis.gestion.dao.UtilisateurRepository;
import com.idis.gestion.entities.Client;
import com.idis.gestion.entities.Employe;
import com.idis.gestion.entities.Personne;
import com.idis.gestion.entities.Utilisateur;
import com.idis.gestion.entities.generator.CodeClientGenerator;
import com.idis.gestion.entities.generator.EmpMatrGenerator;
import com.idis.gestion.service.PersonneService;
import com.idis.gestion.service.pagination.PageClient;
import com.idis.gestion.service.pagination.PageEmploye;
import com.idis.gestion.web.controls.Count;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
@Transactional
public class PersonneServiceImpl implements PersonneService {

    @Autowired
    private PersonneRepository personneRepository;

    @Autowired
    private UtilisateurRepository utilisateurRepository;

    @Override
    public Employe saveEmploye(Employe e) {

        e.setCreateAt(new Date());
        e.setUpdateAt(new Date());
        e.setEnable(1);

        Employe employe = personneRepository.save(e);

        EmpMatrGenerator empMatrGenerator = new EmpMatrGenerator(personneRepository);
        String matricule = empMatrGenerator.generate();
        employe.setMatricule(matricule);

        return employe;
    }

    @Override
    public Client saveClient(Client c) {
        c.setCreateAt(new Date());
        c.setUpdateAt(new Date());
        c.setEnable(1);

        Client client = personneRepository.save(c);

        CodeClientGenerator codeClientGenerator = new CodeClientGenerator(personneRepository);
        String cpteClt = codeClientGenerator.generate();
        client.setCodeClient(cpteClt);
        return client;
    }

    @Override
    public Employe updateEmploye(Employe e) {
        Employe employe = personneRepository.getEmployeByMatricule(e.getMatricule());
        if(employe == null) throw new RuntimeException("Ce employé n'existe pas");
        employe.setAdresse(e.getAdresse());
        employe.setContact(e.getContact());
        employe.setEmail(e.getEmail());
        employe.setEnable(e.getEnable());
        employe.setImage(e.getImage());
        employe.setRaisonSociale(e.getRaisonSociale());
        employe.setSite(e.getSite());
        employe.setFonction(e.getFonction());
        employe.setUpdateAt(new Date());
        return personneRepository.save(employe);
    }

    @Override
    public Client updateClient(Client c) {
        Client client = personneRepository.getClientByCodeClient(c.getCodeClient());
        if(client == null) throw new RuntimeException("Ce client n'existe pas");
        client.setAdresse(c.getAdresse());
        client.setContact(c.getContact());
        client.setEmail(c.getEmail());
        client.setEnable(c.getEnable());
        client.setImage(c.getImage());
        client.setRaisonSociale(c.getRaisonSociale());
        client.setResponsable(c.getResponsable());
        client.setUpdateAt(new Date());
        client.setDomaineActivite(c.getDomaineActivite());
        return personneRepository.save(client);
    }

    @Override
    public List<Employe> findAllEmployes(int enable) {
        return personneRepository.findAllEmployes(enable);
    }

    @Override
    public List<Client> findAllClients(int enable) {
        return personneRepository.findAllClients(enable);
    }

    @Override
    public List<Client> findClientsByRaisonSociale(String raisonSociale, int enable) {
        return personneRepository.findClientsByRaisonSociale(raisonSociale, enable);
    }

    @Override
    public Employe getEmployeByMatricule(String matricule) {
        return personneRepository.getEmployeByMatricule(matricule);
    }

    @Override
    public Client getClientById(Long id) {
        return personneRepository.getClientById(id);
    }

    @Override
    public Employe getEmployeById(Long id) {
        return personneRepository.getEmployeById(id);
    }

    @Override
    public Client getClientByCodeClient(String codeClient) {
        return personneRepository.getClientByCodeClient(codeClient);
    }

    @Override
    public PageEmploye listEmployes(String matricule, String nomSite, String raisonSociale, int enable, Pageable pageable) {
        Page<Employe> employes = personneRepository.listEmployes(matricule, nomSite, raisonSociale, enable, pageable);
        PageEmploye pEmp = new PageEmploye();
        pEmp.setEmployes(employes.getContent());
        pEmp.setPage(employes.getNumber());
        pEmp.setNombreEmployes(employes.getNumberOfElements());
        pEmp.setTotalEmployes((int)employes.getTotalElements());
        pEmp.setTotalPages(employes.getTotalPages());
        return pEmp;
    }

    @Override
    public PageClient listClients(String codeClient, String raisonSociale, int enable, Pageable pageable) {
        Page<Client> clients = personneRepository.listClients(codeClient, raisonSociale, enable, pageable);
        PageClient pClient = new PageClient();
        pClient.setClients(clients.getContent());
        pClient.setPage(clients.getNumber());
        pClient.setNombreClients(clients.getNumberOfElements());
        pClient.setTotalClients((int)clients.getTotalElements());
        pClient.setTotalPages(clients.getTotalPages());
        return pClient;
    }

    @Override
    public List<Client> listClientsByRaisonSociale(String raisonSociale) {
        List<Client> clients = personneRepository.listClientsByRaisonSociale(raisonSociale);
        return null;
    }

    @Override
    public void disablePersonne(Long id, Date date) {
        Personne personne = personneRepository.getPersonneById(id);
        if(personne == null) throw new RuntimeException("Cette personne n'existe pas");
        Utilisateur utilisateur = utilisateurRepository.findUtilisateurByPersonne(personne.getId());
        if(utilisateur != null) utilisateurRepository.disableUser(utilisateur.getId(), new Date());
        personneRepository.disablePersonne(id, date);
    }

    @Override
    public void enablePersonne(Long id, Date date) {
        Personne personne = personneRepository.getPersonneById(id);
        if(personne == null) throw new RuntimeException("Cette personne n'existe pas");
        Utilisateur utilisateur = utilisateurRepository.findUtilisateurByPersonne(personne.getId());
        if(utilisateur != null) utilisateurRepository.enableUser(utilisateur.getId(), new Date());
        personneRepository.enablePersonne(id, date);
    }

    @Override
    public void removePersonneById(Long id) {
        Personne personne = personneRepository.getPersonneById(id);
        if(personne == null) throw new RuntimeException("Cette personne n'existe pas");
        Utilisateur utilisateur = utilisateurRepository.findUtilisateurByPersonne(personne.getId());
        if(utilisateur != null) utilisateurRepository.removeUtilisateurById(utilisateur.getId());
        personneRepository.removePersonneById(id);
    }

    @Override
    public String countClients(int enable) {
        Long value = personneRepository.countClients(enable);
        return value.toString();
    }

    @Override
    public String countEmployes(int enable) {
        Long value = personneRepository.countEmployes(enable);
        return value.toString();
    }


}
