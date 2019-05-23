package com.idis.gestion.service;

import com.idis.gestion.entities.Client;
import com.idis.gestion.entities.Employe;
import com.idis.gestion.entities.Personne;
import com.idis.gestion.entities.Utilisateur;
import com.idis.gestion.service.pagination.PageClient;
import com.idis.gestion.service.pagination.PageEmploye;
import org.springframework.data.domain.Pageable;

import java.util.Date;
import java.util.List;

public interface PersonneService {
    public List<Employe> findAllEmployes(int enable);
    public List<Client> findAllClients(int enable);
    public Employe saveEmploye(Employe e);
    public Client saveClient(Client c);
    public Employe updateEmploye(Employe e);
    public Client updateClient(Client c);
    public Employe getEmployeByMatricule(String matricule);
    public Employe getEmployeById(Long id);
    public Client getClientById(Long id);
    public Client getClientByCodeClient(String codeClient);
    public PageEmploye listEmployes(String matricule, String nomSite, String raisonSociale, int enable, Pageable pageable);
    public PageClient listClients(String codeClient, String raisonSociale, int enable, Pageable pageable);
    public void disablePersonne(Long id, Date date);
    public void enablePersonne(Long id, Date date);
    public void removePersonneById(Long id);

    // --------------------------- DASHBOARD ----------------------
    public String countClients(int enable);
    public String countEmployes(int enable);
}
